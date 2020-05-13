package control;

import model.Category;
import model.OffCode;
import model.People.Account;
import model.People.Manager;
import model.Product;
import model.Requests.Request;

import javax.print.DocFlavor;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ManagerController extends Controller {
    public ManagerController(Account currentAccount) {
        super(currentAccount);
    }

    public static Boolean isThisFieldValid(Product product,String field) {
        return product.getAttributes().keySet().contains(field);
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
//        ArrayList<Account> allAccounts;
//        allAccounts = Account.getAllAccounts();
//        for (Account account : allAccounts){
//            if (account.getUsername().equals(username)) { allAccounts.remove(account);
//            break;}
//        }
//        Account.setAllAccounts(allAccounts);
    }

    public static void createManager (String[] info){
        new Manager(info[0],info[1],info[2],info[3],Double.valueOf(info[4]),info[5],info[6]);
    }

    public static Boolean isHeAbleToCreateManger(){
        return currentAccount instanceof Manager;
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
        ArrayList <OffCode> offCodes = model.OffCode.getAllOffCodes();
        for (OffCode offCode : offCodes) {
            if (offCode.getOffCode().equals(code)) return true;
        }
        return false;
    }

    public static void createDiscountCode(String [] info) throws ParseException {
        new SimpleDateFormat("dd/MM/yyyy").parse(info[1]);

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

    public static Boolean isCategoryValid(String categoryName){
         return getCategoryByName(categoryName)!=null;
        }

    public static void addCategory(String[] info){
        if (!(currentAccount instanceof Manager)) return;
        Category newCategory = new Category(info[0],getCategoryByName(info[1]));
        ArrayList <Category> allCategories = ((Manager) currentAccount).getAllCategories();
        allCategories.add(newCategory);
        ((Manager) currentAccount).setAllCategories(allCategories);
    }

    public static void editCategory(String [] info){

    }

    public static void  removeCategory(String categoryName){
        Category category = getCategoryByName(categoryName);
        Manager manager = (Manager) currentAccount;
        ArrayList <Category> allCategories = manager.getAllCategories();
        allCategories.remove(getCategoryByName(categoryName));
        manager.setAllCategories(allCategories);
    }

    private static Request getRequestById (String id){
        for (Request request : Manager.getAllRequests()) {
            if (request.getRequestId().equals(id)) return request;
        }
        return null;
    }

    private static Category getCategoryByName (String name){
        Manager manager = (Manager) currentAccount;
        for (Category category1 : manager.getAllCategories()){
            if (category1.getName().equals(name)) return category1;
        }
        return null;
    }
}
