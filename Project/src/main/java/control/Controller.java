package control;

import control.Exceptions.HaveNotLoggedInException;
import control.Exceptions.WrongPasswordException;
import model.ItemOfOrder;
import model.People.Account;
import model.People.Customer;
import model.People.Manager;
import model.People.Seller;
import model.Requests.AddSellerRequest;

import java.util.ArrayList;

public class Controller {
    protected static Account currentAccount ;
    protected static ArrayList<ItemOfOrder> cart = new ArrayList<>();

    public Controller(Account currentAccount) {
        this.currentAccount = currentAccount;
    }

    public static boolean hasUserWithThisUsername(String username){
        return Account.getAccountById(username)!=null;
    }

    public static boolean isLoggedIn(){
        return currentAccount != null;
    }

    // String [String type , String username,String password , String firstName, String lastName,
    // Double balance, String email, String phoneNumber , String brand name]
    public static void register (String [] registerInfo){

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



    public static void login(String username , String password) throws WrongPasswordException {
        if (doesPasswordMatches(username,password)) {
            currentAccount = Account.getAccountById(username);
            if (currentAccount instanceof Customer)
            ((Customer) currentAccount).setCart(cart);
        }
        else {
            throw new WrongPasswordException("Username Or Password Is Wrong !!");
        }
    }

    public static boolean doesPasswordMatches(String user , String password){

        assert Account.getAccountById(user) != null;
        if ( hasUserWithThisUsername(user) || !Account.getAccountById(user).getPassword().equals(password) ){
            return false;
        }
        return true;
    }

    public static void logout() throws HaveNotLoggedInException {
        if (currentAccount!=null)
            currentAccount = null;
        else {
            throw new HaveNotLoggedInException("You Haven't Logged In To Logout !!");
        }

    }

    public static Account getCurrentAccount()  {
        return currentAccount;
    }

    public static void setCurrentAccount (String username){
        currentAccount = Manager.getAccountById(username);
    }
}
