package model.People;

import control.Exceptions.InvalidUsernameException;
import javafx.scene.image.ImageView;
import model.Chat;

import java.util.ArrayList;
import java.util.List;

public abstract class Account {
    protected static List<Account> allAccounts = new ArrayList<>();
    protected String username;
    protected String firstName;
    protected String lastName;
    protected Double balance;
    protected String email;
    protected String phoneNumber;
    private String password;
    private ImageView image;
    private ArrayList<Chat> chats;

    public Account(String username, String password, String firstName, String lastName, Double balance, String email, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = balance;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.chats = new ArrayList<>();
        if (this instanceof Customer || this instanceof Manager)
            allAccounts.add(this);
    }

    public static List<Account> getAllAccounts() {
        return allAccounts;
    }

    public static void setAllAccounts(List<Account> accounts) {
        allAccounts = accounts;
    }

    public static void addSeller(Seller seller) {
        allAccounts.add(seller);
    }

    public static Account getAccountById(String username) throws InvalidUsernameException {

        for (Account account : allAccounts) {
            if (account.username.equals(username)) {
                return account;
            }
        }

        throw new InvalidUsernameException();
    }

    public static boolean doesManagerExist() {
        for (Account account : allAccounts) {
            if (account instanceof Manager) return true;
        }
        return false;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBalanceString() {
        return Double.toString(balance);
    }

    public ArrayList<Chat> getChats() {
        return chats;
    }

    public String getType() {
        if (this instanceof Customer) return "Customer";
        if (this instanceof Manager) return "Manager";
        return "Seller";
    }

    @Override
    public String toString() {
        return "Account{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", balance=" + balance +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
