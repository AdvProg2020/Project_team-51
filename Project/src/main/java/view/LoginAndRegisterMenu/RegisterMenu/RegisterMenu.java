package view.LoginAndRegisterMenu.RegisterMenu;

import control.Controller;
import view.Menu;

public class RegisterMenu extends Menu {


    public RegisterMenu(Menu parentMenu) {
        super("Register", parentMenu);
        subMenus.put(1, new RegisterCustomerMenu(this));
        subMenus.put(2, new RegisterSellerMenu(this));
        if (!Controller.isThereAnyManager())
            subMenus.put(3, new RegisterManagerMenu(this));
    }

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
        }
        this.executeMenu();
    }
}
