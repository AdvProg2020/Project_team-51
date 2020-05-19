package view.Profile.SellerMenu;

import control.Controller;
import control.Exceptions.WrongFormatException;
import control.SellerController;
import view.LoginAndRegisterMenu.LoginAndRegisterMenu;
import view.MainMenu;
import view.Menu;

import javax.management.InstanceAlreadyExistsException;

public class EditPersonalInfo extends Menu {


    public EditPersonalInfo(Menu parentMenu) {
        super("Edit Personal Info", parentMenu);
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
        subMenus.put(5, new Menu("Brand", this) {
            @Override
            public void executeMenu() {
                getBrand();
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
            SellerController.editFirstName(command);
            System.out.println("New first name submitted !");
        } catch (InstanceAlreadyExistsException e) {
            System.out.println("This is your old first name ! ");
            getFirstName();
        }
    }

    private void getLastName() {
        System.out.println("Please Enter Your New Last Name : ");
        command = inputInFormat("Invalid Format !", "(?i)\\w+");
        if (command.equalsIgnoreCase("back")) return;
        try {
            SellerController.editLastName(command);
            System.out.println("New last name submitted !");
        } catch (InstanceAlreadyExistsException e) {
            System.out.println("This is your old last name ! ");
            getLastName();
        }
    }

    private void getEmail() {
        System.out.println("Please Enter Your New Email : ");
        command = inputInFormat("Invalid Format !", "(?i)\\w+@\\w+\\.\\w+");
        if (command.equalsIgnoreCase("back")) return;
        try {
            SellerController.editEmail(command);
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
        if (command.equalsIgnoreCase("back")) return;
        try {
            SellerController.editPhoneNumber(command);
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

    private void getBrand() {
        System.out.println("Please Enter Your New Brand Name : ");
        command = inputInFormat("Invalid Format !", "(?i)\\w+");
        if (command.equalsIgnoreCase("back")) return;
        try {
            SellerController.editBrand(command);
            System.out.println("New brand name submitted !");
        } catch (InstanceAlreadyExistsException e) {
            System.out.println("This is your old brand name ! ");
            getBrand();
        }
    }

}
