package view.Profile.CustomerMenu;

import control.Controller;
import control.CustomerController;
import control.Exceptions.WrongFormatException;
import view.LoginMenu;
import view.MainMenu;
import view.Menu;

import javax.management.InstanceAlreadyExistsException;

public class EditPersonalInfo extends Menu {


    private CustomerController customerController;

    public EditPersonalInfo(Menu parentMenu, CustomerController customerController) {
        super("Edit Personal Info", parentMenu);
        this.customerController = customerController;
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

    private void getFirstName() {
        System.out.println("Please Enter Your New First Name : ");
        command = inputInFormat("Invalid Format !", "(?i)\\w+");
        try {
            customerController.editFirstName(command);
            System.out.println("New first name submitted !");
        } catch (InstanceAlreadyExistsException e) {
            System.out.println("This is your old first name ! ");
            getFirstName();
        }
    }

    private void getLastName() {
        System.out.println("Please Enter Your New Last Name : ");
        command = inputInFormat("Invalid Format !", "(?i)\\w+");
        try {
            customerController.editLastName(command);
            System.out.println("New last name submitted !");
        } catch (InstanceAlreadyExistsException e) {
            System.out.println("This is your old last name ! ");
            getLastName();
        }
    }

    private void getEmail() {
        System.out.println("Please Enter Your New Email : ");
        command = inputInFormat("Invalid Format !", "(?i)\\w+@\\w+\\.\\w+");
        try {
            customerController.editEmail(command);
            System.out.println("New email submitted !");
        } catch (InstanceAlreadyExistsException e) {
            System.out.println("This is your old email ! ");
            getEmail();
        } catch (IllegalArgumentException e) {
            System.out.println("Email has already used for other Account");
            getEmail();
        }
    }

    private void getPhone() {
        System.out.println("Please Enter Your New Phone Number : ");
        command = inputInFormat("Invalid Format !", "(?i)[0-9]+");
        try {
            CustomerController.editPhoneNumber(command);
            System.out.println("New phoneNumber submitted !");
        } catch (InstanceAlreadyExistsException e) {
            System.out.println("This is your old phone number ! ");
            getPhone();
        } catch (WrongFormatException e) {
            System.out.println("Phone number has already used for other Account");
            getPhone();
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid Format !");
            getPhone();
        }
    }


}
