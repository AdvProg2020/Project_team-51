package view.Profile.SellerMenu;

import control.Controller;
import control.Exceptions.InvalidProductIdException;
import control.SellerController;
import model.Product;
import view.LoginMenu;
import view.MainMenu;
import view.Menu;

public class SellerMenu extends Menu {

    private SellerController sellerController;

    public SellerMenu(Menu parentMenu) {
        super("Seller Menu", parentMenu);
        this.sellerController = new SellerController(Controller.getCurrentAccount());
        subMenus.put(1, new PersonalInfoMenu(this, sellerController));
        subMenus.put(2, new Menu("Company Info", this) {
            @Override
            public void executeMenu() {
                viewCompanyInformation();
            }
        });
        subMenus.put(3, new Menu("Sales History", this) {
            @Override
            public void executeMenu() {
                viewSalesHistory();
            }
        });
        subMenus.put(4, new ManageProductsMenu(this, sellerController));
        subMenus.put(5, new Menu("Show Categories", this) {
            @Override
            public void executeMenu() {
                showCategories();
            }
        });
        subMenus.put(6, new ViewOffsMenu(this, sellerController));
        subMenus.put(7, new Menu("Balance", this) {
            @Override
            public void executeMenu() {
                viewSellerBalance();
            }
        });
        subMenus.put(8, new AddProductMenu(this, sellerController));
        subMenus.put(9, new Menu("Remove Product", this) {
            @Override
            public void executeMenu() {
                removeProduct();
            }
        });
    }

    @Override
    public void showMenu() {
        System.out.println("1. Personal Info");
        System.out.println("2. Company Info");
        System.out.println("3. Sales History");
        System.out.println("4. Manage Products");
        System.out.println("5. Show Categories");
        System.out.println("6. View Offs");
        System.out.println("7. Balance");
        System.out.println("8. Add Product");
        System.out.println("9. Remove Product");
        System.out.println("10. Back");
        if (Controller.getCurrentAccount() == null)
            System.out.println("11. Login");
        else
            System.out.println("11. Logout");
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

        this.executeMenu();
    }

    private void viewCompanyInformation() {
        System.out.println(sellerController.viewCompanyInfo());
        command = inputInFormat("write Back in order to get back to last Menu : ", "(?i)back");
    }

    private void viewSalesHistory() {
        sellerController.viewSalesHistory().forEach(System.out::println);
        command = inputInFormat("write Back in order to get back to last Menu : ", "(?i)back");
    }

    private void removeProduct() {
        String id = inputInFormat("Enter a Product id : ", "\\w+");
        Product product = null;
        try {
            product = Product.getProductById(id);
            sellerController.removeProduct(product);
        } catch (InvalidProductIdException e) {
            System.out.println("invalid product id");
            return;
        }
    }

    private void showCategories() {
        sellerController.showCategories().forEach(System.out::println);
    }

    private void viewSellerBalance() {
        System.out.println(sellerController.getBalance() + " $");
    }


}

