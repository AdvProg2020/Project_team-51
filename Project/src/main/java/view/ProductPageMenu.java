package view;

import control.Controller;
import control.Exceptions.InvalidProductIdException;
import control.Exceptions.LackOfProductException;
import control.Exceptions.NotAllowedActivityException;
import control.Exceptions.SameProductForComparisonException;
import control.SingleProductController;
import model.Attributes;
import model.Comment;
import model.Product;

import java.util.HashMap;
import java.util.Map;

public class ProductPageMenu extends Menu {

    private Product product;
    private SingleProductController singleProductController ;

    public ProductPageMenu(Menu parentMenu , Product product) {
        super("Product Page", parentMenu);
        this.product=product;
        this.singleProductController = new SingleProductController(Controller.getCurrentAccount());
        this.singleProductController.setProduct(product);
    }

    @Override
    public void showMenu() {
        System.out.println("1. Digest");
        System.out.println("2. Attributes");
        System.out.println("3. Compare [PID]");
        System.out.println("4. Comments");
        System.out.println("5. Back");
    }

    @Override
    public void executeMenu() {
        command = inputInFormat("Please Enter A Valid Command" , "(?i)(digest|attributes|(compare\\s+([0-9]+))|comments|back)");
        if (command.matches("(?i)digest")){
            digest();
        } else if (command.matches("(?i)attributes")){
            attributes();
        } else if (command.matches("(?i)(compare\\s+([0-9]+))")) {
            compare(command.split("\\s+")[1]);
        } else if (command.matches("(?i)comments")){
            var commentsMenu = new Menu("Comments Menu" , this){

                @Override
                public void showMenu() {
                    System.out.println("1. Add Comment");
                    System.out.println("2. Back");
                }

                @Override
                public void executeMenu() {
                    String command = inputInFormat("Please Enter A valid Command", "(?i)(add\\s+comment|back)");
                    if (command.matches("(?i)(add\\s+comment)")){
                        addComment();
                    } else {
                        back();
                    }
                }
            };
            menusHistory.push(this);
            commentsMenu.showMenu();
            commentsMenu.executeMenu();
        } else if (command.matches("(?i)back")){
            back();
        }

        this.showMenu();
        this.executeMenu();
    }

    private void digest() {
        System.out.println(singleProductController.digest());
    }

    private void addToCart() {
        if (!Controller.isLoggedIn()){
            new LoginMenu(this);
        }
        try {
            singleProductController.addToCart(selectSeller());
        } catch (LackOfProductException | NotAllowedActivityException e) {
            System.out.println(e.getMessage());
        }
    }

    private String selectSeller() {

        System.out.println("Select Seller :");
        var sellers = product.getSellersForThisProduct();
        for (int i = 0; i < sellers.size() ; i++) {
            System.out.println((i+1) + ". " + sellers.get(i).getBrandName());
        }
        int option = 0 ;

        while ((option <= 0) && (option >sellers.size())){
            option = getOption();
        }

        return sellers.get(option-1).getUsername();
    }

    private void attributes() {
        var attributes = singleProductController.showAttributes();
        for (Map.Entry<Attributes, String> attribute : attributes.entrySet()) {
            System.out.println(attribute.getKey().getField() + " : " + attribute.getValue());
        }
    }

    private void compare(String id) {
        Product otherProduct;
        try {
            otherProduct = Product.getProductById(id);
        } catch (InvalidProductIdException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("---------------------------");
        System.out.printf("|%12s|%12s|",product.getName() , otherProduct.getName());
        HashMap<String, String> comparison;
        try {
            comparison = singleProductController.compare(id);
        } catch (SameProductForComparisonException | InvalidProductIdException e) {
            System.out.println(e.getMessage());
            return;
        }
        for (Map.Entry<String, String> compare : comparison.entrySet()) {
            System.out.printf("|%12s|%12s|", compare.getKey() , compare.getValue());
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
            singleProductController.addComment(getCommentTitle(),getCommentContent());
        } catch (NotAllowedActivityException e) {
            System.out.println(e.getMessage());
        }
    }

    private String getCommentTitle(){
        return inputInFormat("Title : " , "w+");
    }

    private String getCommentContent(){
        return inputInFormat("Content : " , "w+");
    }
}
