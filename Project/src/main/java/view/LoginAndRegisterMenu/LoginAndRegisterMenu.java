package view.LoginAndRegisterMenu;

import view.LoginAndRegisterMenu.RegisterMenu.RegisterMenu;
import view.Menu;

public class LoginAndRegisterMenu extends Menu {

    public LoginAndRegisterMenu(Menu parentMenu) {
        super("Login And Register", parentMenu);
        subMenus.put(1, new LoginMenu(this));
        subMenus.put(2, new RegisterMenu(this));
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
