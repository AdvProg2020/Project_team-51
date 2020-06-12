
package view.Profile.ManagerMenu;

import control.Controller;
import control.Exceptions.WrongFormatException;
import control.ManagerController;
import view.LoginAndRegisterMenu.LoginAndRegisterMenu;
import view.MainMenu;
import view.Menu;

import javax.management.InstanceAlreadyExistsException;

public class ManagerEditPersonalInfo extends Menu {

    ManagerController managerController;
    
    public ManagerEditPersonalInfo(Menu parentMenu , ManagerController managerController) {
        super("Edit Personal Info", parentMenu);
        managerController = managerController;
        subMenus.put(1, new Menu("First Name", this) {
            @Override
            public void executeMenu() {
                getFirstName();
            }
        });
        subMenus.put(2, new Menu("Last Name", this) {
            @Override
            public void executeMenu() {
                getLastName();
            }
        });
        subMenus.put(3, new Menu("Email", this) {
            @Override
            public void executeMenu() {
                getEmail();
            }
        });
        subMenus.put(4, new Menu("Phone", this) {
            @Override
            public void executeMenu() {
                getPhone();
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

    private void getFirstName() {
        System.out.println("Please Enter Your New First Name : ");
        command = inputInFormat("Invalid Format !", "(?i)\\w+");
        if (command.equalsIgnoreCase("back")) return;
        try {
            managerController.editFirstName(command);
            System.out.println("New first name submitted !");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            getFirstName();
        }
    }

    private void getLastName() {
        System.out.println("Please Enter Your New Last Name : ");
        command = inputInFormat("Invalid Format !", "(?i)\\w+");
        if (command.equalsIgnoreCase("back")) return;
        try {
            managerController.editLastName(command);
            System.out.println("New last name submitted !");
        } catch (Exception e) {
            System.out.println("This is your old last name ! ");
            getLastName();
        }
    }

    private void getEmail() {
        System.out.println("Please Enter Your New Email : ");
        command = inputInFormat("Invalid Format !", "(?i)\\w+@\\w+\\.\\w+");
        if (command.equalsIgnoreCase("back")) return;
        try {
            managerController.editEmail(command);
            System.out.println("New email submitted !");
        }catch (Exception e){
            System.err.println(e.getMessage());
            getEmail();
        }
    }

    private void getPhone() {
        System.out.println("Please Enter Your New Phone Number : ");
        command = inputInFormat("Invalid Format !", "(?i)[0-9]+");
        if (command.equalsIgnoreCase("back")) return;
        try {
            managerController.editPhoneNumber(command);
            System.out.println("New phoneNumber submitted !");
        } catch (InstanceAlreadyExistsException e) {
            System.out.println("This is your old phone number ! ");
            getPhone();
        } catch (WrongFormatException e) {
            System.out.println("Phone number has already used for other Account");
            getPhone();
        } catch (Exception e) {
            System.out.println("Invalid Format !");
            getPhone();
        }
    }
}
