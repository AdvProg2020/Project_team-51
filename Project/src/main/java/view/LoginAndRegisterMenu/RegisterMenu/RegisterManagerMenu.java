package view.LoginAndRegisterMenu.RegisterMenu;

import control.Controller;
import control.Exceptions.*;
import model.People.Manager;
import view.MainMenu;
import view.Menu;

import java.util.regex.Pattern;

public class RegisterManagerMenu extends Menu {

    private static final int MINIMUM_PASSWORD_LENGTH = 4;
    String username, password, email, firstName, lastName, number, balance;

    public RegisterManagerMenu(Menu parentMenu) {
        super("Register Manager", parentMenu);
    }

    private static boolean doesMatch(String pattern, String anotherString) {
        return Pattern.compile(pattern).matcher(anotherString).find();
    }

    @Override
    public void showMenu() {

    }

    @Override
    public void executeMenu() {
        getUsername();
    }

    private void getUsername() {
        username = inputInFormat("Please Enter Username :", "\\w+");
        if (username.equalsIgnoreCase("back")) return;
        try {
            Controller.isUserNameUsed(username);
            getPassword();
        } catch (InvalidUsernameException e) {
            System.out.println(e.getMessage());
            getUsername();
        }
    }

    private void getPassword() {
        password = inputInFormat("Please Enter Password :", "\\w+");
        if (password.equalsIgnoreCase("back")) getUsername();
        try {
            checkPassword(password);
            getFirstName();
        } catch (WeakPasswordException e) {
            System.out.println(e.getMessage());
            getPassword();
        }
    }

    private void getFirstName() {
        firstName = inputInFormat("Please Enter FirstName :", "\\w+");
        if (firstName.equalsIgnoreCase("back")) getPassword();
        try {
            checkName(firstName);
            getLastName();
        } catch (WrongFormatException e) {
            System.err.println(e.getMessage());
            getFirstName();
        }
    }

    private void getLastName() {
        lastName = inputInFormat("Please Enter LastName :", "\\w+");
        if (lastName.equalsIgnoreCase("back")) getFirstName();
        try {
            checkName(lastName);
            getEmail();
        } catch (WrongFormatException e) {
            System.err.println(e.getMessage());
            getLastName();
        }
    }

    private void getEmail() {
        email = inputInFormat("Please Enter Email :", ".+");
        if (email.equalsIgnoreCase("back")) getLastName();
        try {
            checkEmail(email);
            getNumber();
        } catch (WrongFormatException e) {
            System.err.println("Email format is wrong !");
            getEmail();
        } catch (InvalidEmailException e) {
            System.err.println("Email has submitted before !");
            getEmail();
        }
    }

    private void checkEmail(String email) throws WrongFormatException, InvalidEmailException {
        if (!email.matches("\\w+@\\w+\\.\\w+")) throw new WrongFormatException("email");
        Controller.isEmailUsed(email);
    }

    private void getNumber() {
        number = inputInFormat("Please Enter Number :", "\\w+");
        if (number.equalsIgnoreCase("back")) getEmail();
        try {
            checkNumber(number);
            Controller.setCurrentAccount(new Manager(username, password, firstName, lastName, email, number));
            goToNextMenu();
        } catch (WrongFormatException e) {
            System.err.println("Number format is wrong !");
            getNumber();
        } catch (InvalidPhoneNumberException e) {
            System.err.println("Number has submitted before !");
            getNumber();
        }
    }

    private void checkNumber(String number) throws WrongFormatException, InvalidPhoneNumberException {
        if (!number.matches("(09\\d{9}|\\+989\\d{9})")) throw new WrongFormatException("phone number");
        Controller.isNumberUsed(number);
    }

    private void checkName(String name) throws WrongFormatException {
        if (doesMatch("(\\W|\\d)", name)) throw new WrongFormatException("name");
    }

    private void checkPassword(String string) throws WeakPasswordException {
        if (string.length() < MINIMUM_PASSWORD_LENGTH) throw new WeakPasswordException();
    }

    private void goToNextMenu() {
        Menu nextMenu = new MainMenu();
        nextMenu.showMenu();
        nextMenu.executeMenu();
    }

}
