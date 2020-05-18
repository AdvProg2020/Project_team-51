package view;

import control.Controller;
import control.Exceptions.InvalidPasswordException;
import control.Exceptions.InvalidUsernameException;
import control.Exceptions.WeakPasswordException;
import control.Exceptions.WrongFormatException;
import control.ManagerController;
import control.TokenGenerator;
import model.People.Customer;
import model.People.Manager;
import model.People.Seller;
import model.Requests.AddSellerRequest;

import java.util.regex.Pattern;

public class LoginMenu extends Menu {
    private static final int MINIMUM_PASSWORD_LENGTH = 4;
    String type;
    String username, password, email, firstName, lastName, number, user, pass;

    public LoginMenu(Menu parentMenu) {
        super("Login", parentMenu);
    }

    private static boolean doesMatch(String pattern, String anotherString) {
        return Pattern.compile(pattern).matcher(anotherString).find();
    }

    @Override
    public void showMenu() {
        System.out.println("1. Login\n2. SignUp\nBack");
    }

    @Override
    public void executeMenu() {
        while (true) {
            command = scanner.nextLine();
            if (command.equals("back")) {
                this.parentMenu.showMenu();
                this.parentMenu.executeMenu();
            } else if (command.equals("1")) loginUser();
            else if (command.equals("2")) signup();
            else System.err.println("wrong command");
        }
    }

    public void loginUser() {
        enterUsername();
    }

    private void signup() {
        getType();
    }

    public void getType() {
        try {
            System.out.println("select account type :\n1.customer\n2.seller\n3.manager\nback");
            if ((command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
                this.showMenu();
                this.executeMenu();
            } else if (command.equals("3")) {
                if (ManagerController.isHeAbleToCreateManger()) type = "manager";
            } else if (command.equals("2")) type = "seller";
            else if (command.equals("1")) type = "customer";
            else throw new WrongFormatException("input");
            getUsername();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            getType();
        }
    }

    private void getUsername() {
        try {
            System.out.println("please enter username OR back");
            if ((command = scanner.nextLine().trim()).equalsIgnoreCase("back")) getType();
            checkUsername(command);
            username = command;
            getPassword();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            getUsername();
        }
    }

    private void checkUsername(String string) throws Exception {
        if (doesMatch("\\W", string)) throw new WrongFormatException("username");
        Controller.isUserNameUsed(string);
    }

    private void getPassword() {
        try {
            System.out.println("enter password OR back");
            if ((command = scanner.nextLine().trim()).equalsIgnoreCase("back")) getUsername();
            checkPassword(command);
            password = command;
            getFirstName();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            getPassword();
        }
    }

    private void checkPassword(String string) throws Exception {
        if (doesMatch("\\W", string)) throw new WrongFormatException("password");
        if (string.length() < MINIMUM_PASSWORD_LENGTH) throw new WeakPasswordException();
    }

    private void getFirstName() {
        try {
            System.out.println("please enter your name");
            if ((command = scanner.nextLine().trim()).equalsIgnoreCase("back")) getPassword();
            checkName(command);
            firstName = command;
            getLastName();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            getFirstName();
        }
    }

    private void checkName(String s) throws Exception {
        if (doesMatch("\\s\\s", s)) throw new WrongFormatException("name");
        if (doesMatch("[^a-zA-Z ]", s)) throw new WrongFormatException("name");
    }

    private void getLastName() {
        try {
            System.out.println("please enter your lastName");
            if ((command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
                getFirstName();
            }
            checkName(command);
            lastName = command;
            getEmail();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            getLastName();
        }
    }

    private void getEmail() {
        try {
            System.out.println("enter Email OR back");
            if ((command = scanner.nextLine().trim()).equalsIgnoreCase("back")) getLastName();
            checkEmail(command);
            email = command;
            getNumber();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            getEmail();
        }
    }

    private void checkEmail(String s) throws Exception {
        if (!doesMatch("\\w+@\\w+\\.\\w+", s)) throw new WrongFormatException("email");
        Controller.isEmailUsed(s);
    }

    private void getNumber() {
        try {
            System.out.println("enter phone number OR back");
            if ((command = scanner.nextLine().trim()).equalsIgnoreCase("back")) getEmail();
            checkNumber(command);
            number = command;
            getBalance();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            getNumber();
        }
    }

    private void checkNumber(String s) throws Exception {
        if (doesMatch("\\D", s)) throw new WrongFormatException("phone number");
        Controller.isNumberUsed(s);
    }

    private void getBalance() {
        try {
            System.out.println("enter balance OR back");
            if ((command = scanner.nextLine().trim()).equalsIgnoreCase("back")) getNumber();
            checkBalance(command);
            double balance = Double.parseDouble(command);
            switch (type) {
                case "customer":
                    {new Customer(username, password, firstName, lastName, balance, email, number);
                    break;
                    }
                case "seller": {
                    System.out.println("please enter your brand name");
                    String brand = scanner.nextLine();
                    Seller selller = new Seller(username, password, firstName, lastName, balance, email, number, brand);
                    new AddSellerRequest(TokenGenerator.generateRequestId(), selller);
                    break;
                }
                case "manager":
                    new Manager(username, password, firstName, lastName, balance, email, number);
            }
            this.showMenu();
            this.executeMenu();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            getBalance();
        }
    }


    private void checkBalance(String s) throws Exception {
        if (!doesMatch("^\\d+\\.{0,1}\\d*$", s)) throw new WrongFormatException("balance amount");
        if ((Double.valueOf(s)) < 0) throw new Exception("balance amount must be positive");
    }

    private void enterUsername() {
        System.out.println("please enter username OR back");
        if ((user = scanner.nextLine()).equalsIgnoreCase("back")) {
            this.showMenu();
            this.executeMenu();
        }
        try {
            if (Manager.getAccountById(user) == null) throw new InvalidUsernameException();
            enterPass();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            enterUsername();
        }
    }

    // not completed
    private void enterPass() {
        System.out.println("please enter password or back");
        if ((pass = scanner.nextLine()).equalsIgnoreCase("back")) {
            getUsername();
        }
        try {
            if (!Controller.doesPasswordMatches(user, pass)) throw new InvalidPasswordException();
            Controller.setCurrentAccount(username);
// which menu should it open now ?
        } catch (Exception e) {
            System.err.println(e.getMessage());
            enterPass();
        }
    }


}