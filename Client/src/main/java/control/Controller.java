package control;

import control.Exceptions.*;
import message.Message;
import model.ItemOfOrder;
import model.People.Account;
import model.People.Manager;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    public static boolean isThereAnyManager;
    protected static Account currentAccount;
    protected static List<ItemOfOrder> cart = new ArrayList<>();

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
        Client.getInstance().addToSendingMessagesAndSend(Message.makeLoginMessage("Server", username, password));
    }

    public static boolean doesPasswordMatches(String user, String password) throws InvalidUsernameException {
        return hasUserWithThisUsername(user) && Account.getAccountById(user).getPassword().equals(password);
    }

    public static void logout() throws HaveNotLoggedInException {
        Client.getInstance().addToSendingMessagesAndSend(Message.makeLogoutMessage("Server"));
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
