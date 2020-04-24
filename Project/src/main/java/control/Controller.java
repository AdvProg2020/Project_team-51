package control;

import model.ItemOfOrder;
import model.People.Account;

import java.util.ArrayList;

public class Controller {
    protected Account currentAccount ;
    protected static ArrayList<ItemOfOrder> cart = new ArrayList<ItemOfOrder>();

    public Controller(Account currentAccount) {
        this.currentAccount = currentAccount;
    }

    public static boolean hasUserWithThisUsername(String username){
        return true;
    }

    public static boolean isLoggedIn(){
        return true;
    }

    public static void register(String [] registerInfo){

    }

    public static void login(String [] loginInfo){

    }

    public static boolean doesPasswordMatches(String user , String password){
        return true;
    }

    public static void logout (){

    }

}
