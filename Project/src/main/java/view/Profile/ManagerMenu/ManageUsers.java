package view.Profile.ManagerMenu;

import control.Controller;
import control.ManagerController;
import model.People.Account;
import view.LoginMenu;
import view.MainMenu;
import view.Menu;

public class ManageUsers extends Menu {
    ManagerController managerController;

    public ManageUsers(Menu parentMenu, ManagerController mc) {
        super("manage users", parentMenu);
        managerController = mc;
        subMenus.put(1, new Menu("view all profiles", this) {
            @Override
            public void executeMenu() {
                viewAllProfiles();
            }
        });
        subMenus.put(2, new Menu("view profile", this) {
            @Override
            public void executeMenu() {
                viewProfile();
            }
        });
        subMenus.put(3, new Menu("editProfile", this) {
            @Override
            public void executeMenu() {
                editProfile();
            }
        });
        subMenus.put(4, new Menu("delete profile", this) {
            @Override
            public void executeMenu() {
                deleteProfile();
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

    private void viewAllProfiles() {
        for (Account a : managerController.getAllProfiles()) {
            System.out.print(a.getUsername() + " name : " + a.getFirstName()
                    + " " + a.getLastName() + " phone : " + a.getPhoneNumber() +
                    " email: " + a.getEmail());
            if (a.getBalance() != null) System.out.println(" balence : " + a.getBalance());
            System.out.println();
        }
    }

    private void viewProfile() {
        String id = inputInFormat("enter id", "^\\w+$");
        try {
            Account a = managerController.getAccountById(id);
            System.out.println(a.getUsername() + " name : " + a.getFirstName()
                    + " " + a.getLastName() + " phone : " + a.getPhoneNumber() +
                    " email: " + a.getEmail() + " balence : " + a.getBalance());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            viewProfile();
        }
    }

    //todo
    private void editProfile() {
        String id = inputInFormat("enter id : ", "^\\w+$");
        Menu m = new Menu("edit profile menu", this) {

            @Override
            public void executeMenu() {
            }
        };
    }

    private void deleteProfile() {
        try {
            String id = inputInFormat("enter id", "^\\w+$");
            managerController.deleteUser(command);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            deleteProfile();
        }
    }

}
