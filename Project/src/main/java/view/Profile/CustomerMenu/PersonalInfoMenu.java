package view.Profile.CustomerMenu;

import control.Controller;
import control.CustomerController;
import view.LoginMenu;
import view.MainMenu;
import view.Menu;

public class PersonalInfoMenu extends Menu {

    private CustomerController customerController;

    public PersonalInfoMenu(Menu parentMenu, CustomerController customerController) {
        super("View Personal Info", parentMenu);
        this.customerController = customerController;
        subMenus.put(1, new EditPersonalInfo(this, customerController));
    }

    @Override
    public void executeMenu() {
        menusHistory.push(this);
        viewPersonaInfo();
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

    public void viewPersonaInfo() {
        System.out.println(customerController.viewPersonalInfo());
    }
}
