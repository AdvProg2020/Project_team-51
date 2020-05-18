package view.Profile.CustomerMenu;

import control.Controller;
import control.CustomerController;
import view.LoginMenu;
import view.MainMenu;
import view.Menu;

public class CustomerMenu extends Menu {

    private CustomerController customerController;

    public CustomerMenu(Menu parentMenu) {
        super("Customer Menu", parentMenu);
        customerController = new CustomerController(Controller.getCurrentAccount());
        subMenus.put(1, new PersonalInfoMenu(this, customerController));
        subMenus.put(2, new CartMenu(this, customerController));
        subMenus.put(3, new OrdersMenu(this, customerController));
        subMenus.put(4, new Menu("View Balance", this) {
            @Override
            public void executeMenu() {
                viewBalance();
            }
        });
        subMenus.put(5, new Menu("View Discount Codes", this) {
            @Override
            public void executeMenu() {
                viewDiscountCodes();
            }
        });
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
    }

    private void viewBalance() {
        System.out.println("Your Balance is : " + customerController.viewBalance() + "$");
    }

    private void viewDiscountCodes() {
        var discountCodes = customerController.viewDiscountCodes();
        if (discountCodes == null || discountCodes.isEmpty()) {
            System.out.println("You have no Discount Code !");
            return;
        }
        customerController.viewDiscountCodes().forEach(System.out::println);
    }
}
