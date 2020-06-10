package view.Profile.ManagerMenu;

import control.Controller;
import control.ManagerController;
import view.LoginMenu;
import view.MainMenu;
import view.Menu;

public class ManagerMenu extends Menu {

    public ManagerController managerController;
    public ManagerMenu(Menu parentMenu) {
        super("manager Menu", parentMenu);
        managerController = new ManagerController(Controller.getCurrentAccount());

        subMenus.put(1,new ManageCategories(this));
        subMenus.put(2,new ManageOffCodes(this , managerController));
        subMenus.put(3,new ManageProducts(this));
        subMenus.put(4,new ManageRequests(this,managerController));
        subMenus.put(5,new ManageUsers(this , managerController));

    }

    @Override
    public void showMenu(){
        System.out.println("1. manage categories");
        System.out.println("2. manage off codes");  //humanity
        System.out.println("3. manage products");
        System.out.println("4. manage requests");
        System.out.println("5. manage users");
        System.out.println("6. back");
        // do i have to put login here?
        System.out.println("");

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
}
