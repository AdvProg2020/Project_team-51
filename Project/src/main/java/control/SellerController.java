package control;

import model.People.Account;

public class SellerController extends Controller {
    public SellerController(Account currentAccount) {
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

    public static void editProduct(String productId){

    }

    public static void addProduct(String [] productInfo){

    }

    public static void removeProduct(String productId){

    }

    public static Boolean isOffIdValid(){
        return true;
    }

    public static void editOffId(String [] offInfo){

    }

    public static void addOffId(String[] offInfo){

    }


}
