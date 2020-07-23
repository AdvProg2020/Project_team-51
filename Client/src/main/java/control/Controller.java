package control;

import control.Exceptions.*;
import model.ItemOfOrder;
import model.People.Account;
import model.People.Customer;
import model.People.Manager;
import model.People.Seller;
import model.Status;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    protected static Account currentAccount;
    protected static List<ItemOfOrder> cart = new ArrayList<>();
    public static boolean isThereAnyManager;

    public Controller(Account currentAccount) {
        Controller.currentAccount = currentAccount;
    }

    public static boolean hasUserWithThisUsername(String username) throws InvalidUsernameException {
        return Account.getAccountById(username) != null;
    }

    public static boolean isLoggedIn() {
        return currentAccount != null;
    }

    public static void login(String username, String password) throws WrongPasswordException, InvalidUsernameException {
        var account = Account.getAccountById(username);
        if (doesPasswordMatches(username, password)
                && (!(account instanceof Seller) || ((((Seller) account).getStatus().equals(Status.APPROVED))))) {
            currentAccount = Account.getAccountById(username);
            if (currentAccount instanceof Customer)
                ((Customer) currentAccount).setCart(cart);
            if (currentAccount == null) throw new InvalidUsernameException();
        } else {
            throw new WrongPasswordException();
        }
    }

    public static boolean doesPasswordMatches(String user, String password) throws InvalidUsernameException {
        return hasUserWithThisUsername(user) && Account.getAccountById(user).getPassword().equals(password);
    }

    public static void logout() throws HaveNotLoggedInException {
        if (currentAccount != null)
            currentAccount = null;
        else {
            throw new HaveNotLoggedInException();
        }

    }

    public static Account getCurrentAccount() {
        return currentAccount;
    }

    public static void setCurrentAccount(String username) throws InvalidUsernameException {
        currentAccount = Manager.getAccountById(username);
    }

    public static void setCurrentAccount(Account account) {
        currentAccount = account;
    }

    public static void isEmailUsed(String Email) throws InvalidEmailException {
        for (Account account : model.People.Account.getAllAccounts()) {
            if (account.getEmail().equalsIgnoreCase(Email)) throw new InvalidEmailException();
        }
    }

    public static void isNumberUsed(String number) throws InvalidPhoneNumberException {
        for (Account account : model.People.Account.getAllAccounts()) {
            if (account.getPhoneNumber().equalsIgnoreCase(number)) throw new InvalidPhoneNumberException();
        }
    }

    public static boolean isUserNameUsed(String username) throws InvalidUsernameException {
        for (Account account : Account.getAllAccounts()) {
            if (account.getUserName().equalsIgnoreCase(username)) throw new InvalidUsernameException();
        }

        return false;
    }

    public static boolean isThereAnyManager() {
        return isThereAnyManager;
    }

    public static void setIsThereAnyManager(boolean isThere) {
        isThereAnyManager = isThere;
    }

    public static List<ItemOfOrder> getCart() {
        return cart;
    }
}
