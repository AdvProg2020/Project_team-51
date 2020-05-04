package control;

import model.Category;
import model.OffCode;
import model.People.Account;
import model.People.Manager;
import model.Product;
import model.Requests.Request;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ManagerController extends Controller {
    public ManagerController(Account currentAccount) {
        super(currentAccount);
    }

    public static Boolean isThisFieldValid(String field) {
        return true;
    }

    public void editFirstName (String firstName) {
        if (currentAccount!=null){
            currentAccount.setFirstName(firstName);
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

    public static void deleteUser (String username) {
        ArrayList<Account> allAccounts = new ArrayList<>();
        allAccounts = Account.getAllAccounts();
        for (Account account : allAccounts){
            if (account.getUsername().equals(username)) {allAccounts.remove(account);
            break;}
        }
        Account.setAllAccounts(allAccounts);
    }

    public static void createManager (String[] info){
        if (info.length!=7) return;
        new Manager(info[0],info[1],info[2],info[3],Double.valueOf(info[4]),info[5],info[6]);
    }

    public static Boolean isHeAbleToCreateManger(){
        if (currentAccount instanceof Manager) return true;
        return false;
    }
    //if the pid is taken returns true
    public static Boolean isThisPidValid(String productId){
        ArrayList<Product> products = model.Product.getAllProducts();
        for (Product product : products){
            if (product.getProductId().equals(productId)) return true;
        }
        return false;
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

    public static Boolean isThisCodeValid (String code){
        return true;
    }

    public static void createDiscountCode(String [] info) throws ParseException {
//        new SimpleDateFormat("dd/MM/yyyy").parse(info[1])
        //this requires an arraylist of accounts
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
        return true;
    }

    public static Request getRequestDetail(String requestId){
        return null;
    }

    public static void acceptRequest(String requestId){

    }

    public static void rejectRequest(String requestId){

    }

    public static Boolean isCategoryValid(String category){
        return true;
        }

    public static void addCategory(String[] info){

    }

    public static void editCategory(String [] info){

    }

    public static void  removeCategory(String category){

    }

}
