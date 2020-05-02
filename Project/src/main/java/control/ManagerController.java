package control;

import model.OffCode;
import model.People.Account;
import model.People.Manager;
import model.Product;
import model.Requests.Request;

import java.util.ArrayList;

public class ManagerController extends Controller {
    public ManagerController(Account currentAccount) {
        super(currentAccount);
    }
//what does this do
    public static Boolean isThisFieldValid(String field) {
        return true;
    }
    // these must be checked in view for their format
    public void editFirstName (String firstName) {
        if (this.currentAccount!=null){
            this.currentAccount.setFirstName(firstName);
        }
    }

    public void editLastName (String lastName) {
        if (currentAccount!=null){
            currentAccount.setLastName(lastName);
        }
    }

    public void editEmail (String email) {
        if (currentAccount!=null){
            currentAccount.setEmail(email);
        }
    }

    public static void editPhoneNumber (String phoneNumber) {
        if (currentAccount!=null) currentAccount.setPhoneNumber(phoneNumber);
    }
//when you delete a customer or a seller then some fields will become null like
    // the person who posted a comment or the seller for a product. these must be handled
    public static void deleteUser (String username) {
        ArrayList<Account> allAccounts = new ArrayList<>();
        allAccounts = Account.getAllAccounts();
        for (Account account : allAccounts){
            if (account.getUsername().equals(username)) {allAccounts.remove(account);
            break;}
        }
        Account.setAllAccounts(allAccounts);
    }
// what are the contains of the info
    public static void createManager (String[] info){

    }
    public static Boolean isHeAbleToCreateManger(){
        if (currentAccount instanceof Manager) return true;
        return false;
    }
// this nigger must be a part of main controller too
    public static Boolean isThisPidValid(String productId){
        return true;
    }

    public static void removeProduct(String productId){
        ArrayList<Product> allProducts = new ArrayList<>();
        allProducts = model.Product.getAllProducts();
        for (Product product : allProducts) {
            if (product.getProductId().equals(productId)) {allProducts.remove(product);
            break;}
        }
        model.Product.setAllProducts(allProducts);
    }
//what should this do too
    public static Boolean isThisCodeValid (String code){
        return true;
    }

    public static void createDiscountCode(String [] info){

    }

    public static void editDiscountCode(String[] info){

    }

    public static void removeDiscountCode(String code){
        ArrayList<OffCode> offCodes = model.OffCode.getAllOffCodes();
        for (OffCode offCode : offCodes) {
            if (offCode.getOffCode().equals(code)){
                offCodes.remove(offCode);
                break;
            }
            model.OffCode.setAllOffCodes(offCodes);
        }
    }

    public static Boolean isRequestValid(String requestId){
        return null;
    }
//there is no id in request
    public static Request getRequestDetail(String requestId){
        return null;
    }

    public static void acceptRequest(String requestId){

    }

    public static void rejectRequest(String requestId){

    }

    public static Boolean isCategoryValid(String category){
        return null;
    }

    public static void addCategory(String[] info){

    }

    public static void editCategory(String [] info){

    }

    public static void  removeCategory(String category){

    }

}
