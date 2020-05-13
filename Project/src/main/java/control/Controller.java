package control;

import model.ItemOfOrder;
import model.People.Account;
import model.People.Customer;
import model.People.Seller;

import java.util.ArrayList;

public class Controller {
    protected static Account currentAccount ;
    protected static ArrayList<ItemOfOrder> cart = new ArrayList<ItemOfOrder>();

    public Controller(Account currentAccount) {
        this.currentAccount = currentAccount;
    }

    public static boolean hasUserWithThisUsername(String username){
        return Account.getAccountById(username)!=null;
    }

    public static boolean isLoggedIn(){
        return currentAccount!=null ? true : false ;
    }

    // String [String type , String username,String password , String firstName, String lastName,
    // Double balance, String email, String phoneNumber , String brand name]
    public static void register (String [] registerInfo){

        if (registerInfo[0].equals("customer")){
            new Customer(registerInfo[1],registerInfo[2], registerInfo[3] ,
                    registerInfo [4] , Double.parseDouble( registerInfo[5] ),
                    registerInfo[6] , registerInfo[7]);

        } else if (registerInfo[0].equals("seller")){
            new Seller (registerInfo[1],registerInfo[2], registerInfo[3] ,
                    registerInfo [4] , Double.parseDouble( registerInfo[5] ),
                    registerInfo[6] , registerInfo[7] , registerInfo[8]);

        } else if (registerInfo[0].equals("manager")){
            new Customer(registerInfo[1],registerInfo[2], registerInfo[3] ,
                    registerInfo [4] , Double.parseDouble( registerInfo[5] ),
                    registerInfo[6] , registerInfo[7]);
        }
    }



    public static void login(String username , String password){
        if (doesPasswordMatches(username,password)) {
            var loginAccount = Account.getAccountById(username);
            currentAccount = loginAccount;
            if (currentAccount instanceof Customer)
            ((Customer) currentAccount).setCart(cart);
        }
    }

    private static boolean doesPasswordMatches(String user , String password){

        if ( hasUserWithThisUsername(user) || !Account.getAccountById(user).getPassword().equals(password) ){
            return false;
        }
        return true;
    }

    public static void logout (){
        currentAccount = null;
    }

    public static Account getCurrentAccount() {
        return currentAccount;
    }
}
