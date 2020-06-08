package view;

import control.Controller;
import control.Exceptions.InvalidProductIdException;
import model.Product;
import view.LoginAndRegisterMenu.LoginAndRegisterMenu;

public class AuctionMenu extends Menu {

    public AuctionMenu(Menu parentMenu) {
        super("Auction Menu", parentMenu);
        subMenus.put(1, new ProductListMenu(this, null));
        subMenus.put(2, new Menu("Show Product By Id", this) {
            @Override
            public void executeMenu() {
                showProduct(this);
            }
        });
    }

    @Override
    public void showMenu() {
        System.out.println("1. Offs");
        System.out.println("2. Show Product");
        System.out.println("3. Back");
        if (Controller.getCurrentAccount() == null)
            System.out.println("4. Login");
        else
            System.out.println("4. Logout");

    }

    @Override
    public void executeMenu() {
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


    private void showProduct(Menu parent) {
        String pid = inputInFormat("Enter Product Id : ", "\\w+");
        Product product = null;
        try {
            product = Product.getProductById(pid);
        } catch (InvalidProductIdException e) {
            System.out.println(e.getMessage());
            showProduct(parent);
        }
        var productPage = new ProductPageMenu(parent, product);
        productPage.showMenu();
        productPage.executeMenu();
    }


}

