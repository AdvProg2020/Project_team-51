package control;

import model.People.Account;
import model.Product;

import java.util.ArrayList;

public class CustomerController extends Controller {

    public CustomerController(Account currentAccount) {
        super(currentAccount);
    }

    public static Boolean isThisFieldValid(String field) {
        return true;
    }

    public static void editFirstName (String firstName) {

    }

    public static void editLastName (String lastName) {

    }

    public static void editEmail (String email) {

    }

    public static void editPhoneNumber (String phoneNumber) {

    }

    public static Boolean isThisPidValid(String productId){
        return true;
    }

    public static Product viewProduct(String pid){
        return null;
    }

    public static void increaseProduct(String productId ,Integer number){

    }

    public static void decreaseProduct(String productId ,Integer number){

    }

    public static Double showTotalPrice(){
        return null;
    }

    public static void purchase(){

    }

    public static void emptyCard(){

    }

    public static Double viewBalance(){
        return null;
    }

    public static Boolean rateProduct(String productId ,Integer rate){
        return null;
    }

    public static ArrayList<String> viewDiscountCodes(){
        return null;
    }

}
