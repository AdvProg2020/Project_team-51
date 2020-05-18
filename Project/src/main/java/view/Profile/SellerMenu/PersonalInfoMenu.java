package view.Profile.SellerMenu;

import control.Controller;
import view.LoginMenu;
import view.MainMenu;
import view.Menu;

public class PersonalInfoMenu extends Menu {


    public PersonalInfoMenu(Menu parentMenu) {
        super("View Personal Info", parentMenu);
        subMenus.put(1, new EditPersonalInfo(this));

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

    private void editPersonalInfoField(Menu parent) {
        var editPersonalInfo = new EditPersonalInfo(this);
        editPersonalInfo.showMenu();
        editPersonalInfo.executeMenu();
    }
}
