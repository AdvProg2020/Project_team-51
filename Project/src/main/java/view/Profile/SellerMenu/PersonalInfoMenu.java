package view.Profile.SellerMenu;

import control.Controller;
import control.SellerController;
import view.LoginAndRegisterMenu.LoginAndRegisterMenu;
import view.MainMenu;
import view.Menu;

public class PersonalInfoMenu extends Menu {


    private SellerController sellerController;

    public PersonalInfoMenu(Menu parentMenu, SellerController sellerController) {
        super("View Personal Info", parentMenu);
        this.sellerController = sellerController;
        subMenus.put(1, new EditPersonalInfo(this));

    }

    @Override
    public void executeMenu() {

        menusHistory.push(this);
        System.out.println(sellerController.viewPersonalInfo());
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

    private void editPersonalInfoField(Menu parent) {
        var editPersonalInfo = new EditPersonalInfo(this);
        editPersonalInfo.showMenu();
        editPersonalInfo.executeMenu();
    }
}
