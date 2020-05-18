package control;

import control.Exceptions.*;
import model.ItemOfOrder;
import model.People.Account;
import model.People.Customer;
import model.People.Manager;
import model.People.Seller;
import model.Requests.AddSellerRequest;

import java.util.ArrayList;

public class Controller {
    protected static Account currentAccount;
    protected static ArrayList<ItemOfOrder> cart = new ArrayList<>();

    public Controller(Account currentAccount) {
        Controller.currentAccount = currentAccount;
    }

    public static boolean hasUserWithThisUsername(String username) {
        return Account.getAccountById(username) != null;
    }

    public static boolean isLoggedIn() {
        return currentAccount != null;
    }

    // String [String type , String username,String password , String firstName, String lastName,
    // Double balance, String email, String phoneNumber , String brand name]
    public static void register(String[] registerInfo) {

        switch (registerInfo[0]) {
            case "customer":
                new Customer(registerInfo[1], registerInfo[2], registerInfo[3],
                        registerInfo[4], Double.parseDouble(registerInfo[5]),
                        registerInfo[6], registerInfo[7]);

                break;
            case "seller":
                var newSeller = new Seller(registerInfo[1], registerInfo[2], registerInfo[3],
                        registerInfo[4], Double.parseDouble(registerInfo[5]),
                        registerInfo[6], registerInfo[7], registerInfo[8]);
                new AddSellerRequest("sth", newSeller);

                break;
            case "manager":
                new Manager(registerInfo[1], registerInfo[2], registerInfo[3],
                        registerInfo[4], Double.parseDouble(registerInfo[5]),
                        registerInfo[6], registerInfo[7]);
                break;
        }
    }


    public static void login(String username, String password) throws WrongPasswordException {
        if (doesPasswordMatches(username, password)) {
            currentAccount = Account.getAccountById(username);
            if (currentAccount instanceof Customer)
                ((Customer) currentAccount).setCart(cart);
        } else {
            throw new WrongPasswordException();
        }
    }

    public static boolean doesPasswordMatches(String user, String password) {

        assert Account.getAccountById(user) != null;
        return !hasUserWithThisUsername(user) && Account.getAccountById(user).getPassword().equals(password);
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

    public static void setCurrentAccount(String username) {
        currentAccount = Manager.getAccountById(username);
    }

    public static void isEmailUsed(String Email) throws InvalidEmailException {
        for (Account account : model.People.Account.getAllAccounts()) {
            if (account.getEmail().equalsIgnoreCase(Email)) throw new InvalidEmailException();
        }
    }

    public static void isNumberUsed(String number) throws Exception {
        for (Account account : model.People.Account.getAllAccounts()) {
            if (account.getPhoneNumber().equalsIgnoreCase(number)) throw new InvalidPhoneNumberException();
        }
    }

    public static void isUserNameUsed(String username) throws Exception {
        for (Account account : model.People.Account.getAllAccounts()) {
            if (account.getUsername().equalsIgnoreCase(username)) throw new InvalidUsernameException();
        }
    }
}
