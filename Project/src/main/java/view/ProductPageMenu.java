package view;

import control.Controller;
import control.Exceptions.*;
import control.SingleProductController;
import model.Attributes;
import model.Comment;
import model.Product;
import view.LoginAndRegisterMenu.LoginAndRegisterMenu;

import java.util.HashMap;
import java.util.Map;

public class ProductPageMenu extends Menu {

    private Product product;
    private SingleProductController singleProductController;

    public ProductPageMenu(Menu parentMenu, Product product) {
        super("Product Page", parentMenu);
        this.product = product;
        this.singleProductController = new SingleProductController(Controller.getCurrentAccount(), product);
        subMenus.put(1, new Menu("Digest", this) {
            @Override
            public void executeMenu() {
                digest();
            }
        });
        subMenus.put(2, new Menu("Attributes", this) {
            @Override
            public void executeMenu() {
                attributes();
            }
        });
        subMenus.put(3, new Menu("Digest", this) {
            @Override
            public void executeMenu() {
                compare();
            }
        });
        subMenus.put(4, new Menu("Digest", this) {
            @Override
            public void executeMenu() {
                comments();
            }
        });
        subMenus.put(5, new CommentsMenu(this, singleProductController));
    }

    @Override
    public void showMenu() {
        System.out.println("1. Digest");
        System.out.println("2. Attributes");
        System.out.println("3. Compare [PID]");
        System.out.println("4. Comments");
        System.out.println("5 Add Comment");
        System.out.println("6. Back");
        if (Controller.getCurrentAccount() == null)
            System.out.println("7. Login");
        else
            System.out.println("7. Logout");
    }

    @Override
    public void executeMenu() {
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
                var login = new LoginAndRegisterMenu(this);
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

    private void digest() {
        try {
            System.out.println(singleProductController.digest());
        } catch (InvalidUsernameException e) {
        }
    }

    private void addToCart() {
        if (!Controller.isLoggedIn()) {
            new LoginAndRegisterMenu(this);
        }
        try {
            singleProductController.addToCart(selectSeller());
        } catch (LackOfProductException | NotAllowedActivityException | InvalidUsernameException e) {
            System.out.println(e.getMessage());
        }
    }

    private String selectSeller() {
        System.out.println("Select Seller :");
        var sellers = product.getSellersForThisProduct();
        for (int i = 0; i < sellers.size(); i++) {
            System.out.println((i + 1) + ". " + sellers.get(i).getBrandName());
        }
        int option = 0;

        while ((option <= 0) && (option > sellers.size())) {
            option = getOption();
        }

        return sellers.get(option - 1).getUsername();
    }

    private void attributes() {
        var attributes = singleProductController.showAttributes();
        for (Map.Entry<Attributes, String> attribute : attributes.entrySet()) {
            System.out.println(attribute.getKey().getField() + " : " + attribute.getValue());
        }
    }

    private void compare() {
        String id = inputInFormat("Please Enter A Valid PID : ", "\\w+");
        Product otherProduct = null;
        try {
            otherProduct = Product.getProductById(id);
        } catch (InvalidProductIdException e) {
            System.out.println(e.getMessage());
            compare();
        }
        System.out.println("---------------------------");
        System.out.printf("|%12s|%12s|", product.getName(), otherProduct.getName());
        HashMap<String, String> comparison;
        try {
            comparison = singleProductController.compare(id);
        } catch (SameProductForComparisonException | InvalidProductIdException e) {
            System.out.println(e.getMessage());
            return;
        }
        for (Map.Entry<String, String> compare : comparison.entrySet()) {
            System.out.printf("|%12s|%12s|", compare.getKey(), compare.getValue());
        }
        System.out.println("---------------------------");
    }

    private void comments() {
        var comments = singleProductController.getComments();
        for (Comment comment : comments) {
            System.out.println(comment);
        }
    }

    private void addComment() {


        try {
            singleProductController.addComment(getCommentTitle(), getCommentContent());
        } catch (NotAllowedActivityException e) {
            System.out.println(e.getMessage());
        }
    }

    private String getCommentTitle() {
        return inputInFormat("Title : ", "w+");
    }

    private String getCommentContent() {
        return inputInFormat("Content : ", "w+");
    }
}
