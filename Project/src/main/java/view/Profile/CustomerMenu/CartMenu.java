package view.Profile.CustomerMenu;

import control.Controller;
import control.CustomerController;
import control.Exceptions.InvalidProductIdException;
import control.ProductController;
import model.Product;
import view.LoginMenu;
import view.MainMenu;
import view.Menu;
import view.ProductListMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CartMenu extends Menu {

    private CustomerController customerController;
    private List<Product> productsOfCart = new ArrayList<>();

    public CartMenu(Menu parentMenu, CustomerController customerController) {
        super("Cart Menu", parentMenu);
        this.customerController = customerController;
        productsOfCart = Controller.getCart().stream().map(a -> a.getProduct()).collect(Collectors.toList());
        subMenus.put(1, new ProductListMenu(this, productsOfCart));
        subMenus.put(2, new Menu("View Product", this) {
            @Override
            public void executeMenu() {
                viewProductById();
            }
        });
        subMenus.put(3, new Menu("Increase Product", this) {
            @Override
            public void executeMenu() {
                increaseProductQuantity();
            }
        });
        subMenus.put(4, new Menu("Decrease Product", this) {
            @Override
            public void executeMenu() {
                decreaseProductQuantity();
            }
        });
        subMenus.put(5, new Menu("Show Total Price", this) {
            @Override
            public void executeMenu() {
                decreaseProductQuantity();
            }
        });
        subMenus.put(5, new PurchaseMenu(this, customerController));
    }

    @Override
    public void executeMenu() {
        menusHistory.push(this);
        viewCart();
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

    private void viewCart() {
        Controller.getCart().stream().forEach(System.out::println);
    }

    private void showProducts() {
        var currentSort = ProductController.getCurrentSort().getSort();
        var productsOfThisCategory = currentSort.applySort(this.productsOfCart, currentSort.getAscending());
        for (int i = 0; i < productsOfThisCategory.size(); i++) {
            if (i != 0)
                System.out.println("--------------------------");
            var product = productsOfThisCategory.get(i);
            System.out.println((i + 1) + ". " + product.getName() + " |  " + product.getPrice() + "$" + "  |  " + product.getProductId());
        }
    }

    private void viewProductById() {
        String id = inputInFormat("Please enter a valid product Id :", "\\w+");
        if (id.equalsIgnoreCase("back")) return;
        Product product = null;
        try {
            product = Product.getProductById(id);
            System.out.println(product);
        } catch (InvalidProductIdException e) {
            System.out.println("Invalid Product Id");
            viewProductById();
        }
    }

    private void increaseProductQuantity() {
        String id = inputInFormat("Please enter a valid product Id :", "\\w+");
        if (id.equalsIgnoreCase("back")) return;
        Product product = null;
        try {
            product = Product.getProductById(id);
            customerController.increaseProduct(product);
            System.out.println("Product Incremented ! ");
        } catch (InvalidProductIdException e) {
            System.out.println("Invalid Product Id");
            increaseProductQuantity();
        }
    }

    private void decreaseProductQuantity() {
        String id = inputInFormat("Please enter a valid product Id :", "\\w+");
        if (id.equalsIgnoreCase("back")) return;
        Product product = null;
        try {
            product = Product.getProductById(id);
            try {
                customerController.decreaseProduct(product);
                System.out.println("Product Incremented ! ");
            } catch (InvalidProductIdException e) {
                System.out.println("Product is not in cart ! ");
                decreaseProductQuantity();
            }
        } catch (InvalidProductIdException e) {
            System.out.println("Invalid Product Id");
            decreaseProductQuantity();
        }
    }

    private void showTotalPrice() {
        System.out.println("The total price is : " + customerController.showTotalPrice() + "$");
        System.out.println("The total price with discount is : " + customerController.showTotalPriceWithDiscount() + "$");
    }

}
