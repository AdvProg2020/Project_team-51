package view.Profile.ManagerMenu;

import control.Controller;
import control.Exceptions.InvalidProductIdException;
import control.ManagerController;
import view.LoginMenu;
import view.MainMenu;
import view.Menu;

public class ManageProducts extends Menu {
    ManagerController managerController;

    public ManageProducts( Menu parentMenu) {
        super("manage products", parentMenu);
        subMenus.put(1, new Menu("delete product" , this) {
            @Override
            public void executeMenu() {
                deleteProduct();
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

    void deleteProduct(){
        String id = inputInFormat("please enter product id" , ""); //todo get the regex for id
        try {
            managerController.removeProduct(id);
        } catch (InvalidProductIdException e) {
            System.err.println(e.getMessage());
        }
    }
}
