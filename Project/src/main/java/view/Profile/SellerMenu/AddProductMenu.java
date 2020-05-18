package view.Profile.SellerMenu;

import control.Controller;
import control.Exceptions.InvalidProductIdException;
import control.Exceptions.NotAllowedActivityException;
import control.SellerController;
import control.TokenGenerator;
import model.Attributes;
import model.Category;
import model.People.Seller;
import model.Product;
import model.Requests.AddSellerForItemRequest;
import view.Enums.AllCommands;
import view.Menu;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AddProductMenu extends Menu {

    private SellerController sellerController;

    public AddProductMenu(Menu parentMenu, SellerController sellerController) {
        super("Add Product Menu", parentMenu);
        this.sellerController = sellerController;
    }

    @Override
    public void executeMenu() {
        command = inputInFormat("Select a option : ", "(?i)(existed\\s+product|new\\s+product" +
                "|back|logout)");
        if (command.matches("existed\\s+product")) {
            addExistedProduct();
        } else if (command.matches("new\\s+product")) {
            addNewProduct();
        } else if (command.matches(AllCommands.BACK.getRegex())) {
            back();
        } else if (command.matches(AllCommands.LOGOUT.getRegex())) {
            logout();
        }

        this.executeMenu();
    }

    private void addExistedProduct() {
        Product product;
        String id = inputInFormat("Please enter the product Id : ", "\\w+");
        try {
            product = Product.getProductById(id);
        } catch (InvalidProductIdException e) {
            System.out.println("invalid product id");
            return;
        }
        System.out.println("Enter Quantity : ");
        int quantity = getOptionWithRange(0, Integer.MAX_VALUE);
        System.out.println("Enter Price : ");
        double price = getOptionWithRangeDouble(0.00, Double.MAX_VALUE);

        new AddSellerForItemRequest(TokenGenerator.generateRequestId(), product,
                (Seller) Controller.getCurrentAccount(), quantity, price);
    }

    private void showCategories(List<Category> categories) {
        for (int i = 0; i < categories.size(); i++) {
            var category = categories.get(i);
            System.out.println((i + 1) + ". " + Category.getPathOfCategory(category));
        }
    }

    private void addNewProduct() {
        var categories = sellerController.listCategories();
        String name = inputInFormat("Enter the name of product : ", "\\w+");
        String brand = inputInFormat("Enter the name of product : ", "\\w+");
        showCategories(categories);
        System.out.println("Enter product parent category number : ");
        int categoryOption = getOptionWithRange(1, categories.size());
        var parentCategory = categories.get(categoryOption - 1);
        Map<Attributes, String> attribute = getAttributesToAddProduct(parentCategory);
        System.out.println("Enter product quantity : ");
        int quantity = getOptionWithRange(1, Integer.MAX_VALUE);
        System.out.println("Enter product price : ");
        double price = getOptionWithRangeDouble(0.00, Double.MAX_VALUE);
        String description = inputInFormat("Enter a description for product : ", "\\w+");
        try {
            sellerController.addProduct(new Product(TokenGenerator.generateProductId(), name, brand, price,
                    (Seller) Controller.getCurrentAccount(), quantity
                    , parentCategory, description, attribute));
            System.out.println("Adding Product Request Sent ! ");
        } catch (NotAllowedActivityException e) {
            System.out.println(e.getMessage());
        }
    }

    private Map<Attributes, String> getAttributesToAddProduct(Category parentCategory) {
        return parentCategory.getAttributes().stream()
                .peek(System.out::println)
                .collect(Collectors.toMap(Function.identity(), a -> inputInFormat(
                        "Please enter a value for field : ",
                        "\\w+")));
    }
}
