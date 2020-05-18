package view.Profile.SellerMenu;

import control.Controller;
import control.Exceptions.InvalidProductIdException;
import control.SellerController;
import model.Product;
import view.LoginMenu;
import view.MainMenu;
import view.Menu;

public class ManageProductsMenu extends Menu {

    private SellerController sellerController;

    public ManageProductsMenu(Menu parentMenu, SellerController sellerController) {
        super("Manage Products", parentMenu);
        this.sellerController = sellerController;
        subMenus.put(1, new Menu("View Product Details", this) {
            @Override
            public void executeMenu() {
                viwProductDetails();
            }
        });
        subMenus.put(2, new Menu("View Product Buyers", this) {
            @Override
            public void executeMenu() {
                viewProductBuyers();
            }
        });
        subMenus.put(3, new Menu("Edit Product", this) {
            @Override
            public void executeMenu() {
                editProduct();
            }
        });
    }

    @Override
    public void executeMenu() {
        sellerController.showSellersProducts().forEach(System.out::println);
        menusHistory.push(this);
        int size = subMenus.size();
        int option = getOptionWithRange(1, size);

        if (option <= size) {
            var nextMenu = subMenus.get(option);
            nextMenu.showMenu();
            nextMenu.executeMenu();
        } else if (option == size + 1) {
            back();
        } else if (option == size + 2) {
            if (Controller.getCurrentAccount() == null) {
                var login = new LoginMenu(this);
                login.showMenu();
                login.executeMenu();
            } else {
                logout();
                var mainMenu = new MainMenu();
                mainMenu.showMenu();
                mainMenu.executeMenu();
            }
        }

        this.executeMenu();

    }

    private void viewProductBuyers() {
        String id = inputInFormat("Enter a Product id : ", "\\w+");
        Product product = null;
        try {
            product = Product.getProductById(id);
            sellerController.viewProductBuyers(product).forEach(System.out::println);
        } catch (InvalidProductIdException e) {
            System.out.println("invalid product id");
            viewProductBuyers();
        }

    }

    private void viwProductDetails() {

        String id = inputInFormat("Enter a Product id : ", "\\w+");
        Product product;
        try {
            product = Product.getProductById(id);
            System.out.println(sellerController.showProductDetails(product));
        } catch (InvalidProductIdException e) {
            System.out.println("invalid product id");
            viwProductDetails();
        }

    }

    private void editProduct() {
        String id = inputInFormat("Enter a Product id : ", "\\w+");
        Product product;
        try {
            product = Product.getProductById(id);
            System.out.println("Name\nPrice\nDescription\nQuantity");
            String field = inputInFormat("Select a option : ", "(?i)(name|price|description|quantity)");
            String value = inputInFormat("Enter a value for selected field : ", "\\W+");
            SellerController.editProduct(product, field, value);
        } catch (InvalidProductIdException e) {
            System.out.println("invalid product id");
            return;
        }
    }

}
