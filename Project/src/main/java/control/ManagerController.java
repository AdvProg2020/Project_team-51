package control;

import control.Exceptions.*;
import model.Category;
import model.OffCode;
import model.People.Account;
import model.People.Manager;
import model.Product;
import model.Requests.Request;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ManagerController extends Controller {

    public ManagerController(Account currentAccount) {
        super(currentAccount);
    }

    public Boolean isThisFieldValid(Product product, String field) {
        return product.getAttributes().containsKey(field);
    }

    public void editPhoneNumber(String phoneNumber) throws Exception {
        checkPhoneNumber(phoneNumber);
        if (currentAccount != null) currentAccount.setPhoneNumber(phoneNumber);
    }

    public void deleteUser(String username) throws Exception {
        if (hasUserWithThisUsername(username))throw new InvalidUsernameException();
        if (currentAccount.getUsername().equals(username)) throw new Exception("cant delete logged in account");
        List<Account> allAccounts;
        allAccounts = Account.getAllAccounts();
        for (Account account : allAccounts) {
            if (account.getUsername().equals(username)) {
                allAccounts.remove(account);
                break;
            }
        }
        Account.setAllAccounts(allAccounts);
    }
// what is this used for
    public void createManager(String[] info) {
        new Manager(info[0], info[1], info[2], info[3], Double.valueOf(info[4]), info[5], info[6]);
    }

    public Boolean isHeAbleToCreateManger() throws Exception {
        if (currentAccount instanceof Manager) return true;
        if (!Account.doesManagerExist()) return true;
        throw new NotAllowedActivityException("only managers are able to create manager");
    }

    public Boolean isThisPidValid(String productId) {
        ArrayList<Product> products = model.Product.getAllProducts();
        for (Product product : products) {
            if (product.getProductId().equals(productId)) return true;
        }
        return false;
    }

    public void removeProduct(String productId) throws InvalidProductIdException {
        if (!isThisPidValid(productId)) throw new InvalidProductIdException();
        ArrayList<Product> allProducts = model.Product.getAllProducts();
        for (Product product : allProducts) {
            if (product.getProductId().equals(productId)) {
                allProducts.remove(product);
                break;
            }
        }
        model.Product.setAllProducts(allProducts);
    }

    public Boolean isThisCodeValid(String code) {
        List<OffCode> offCodes = model.OffCode.getAllOffCodes();
        for (OffCode offCode : offCodes) {
            if (offCode.getOffCode().equals(code)) {
                return offCode.getBeginDate().before(new Date()) && offCode.getEndDate().after(new Date());
            }
        }
        return false;
    }

    public void tryCreateOffcode (String code) throws InvalidOffCodeException {
        if (isThisCodeValid(code)) throw new InvalidOffCodeException();
    }

    public void createDiscountCode(ArrayList<Account> appliedAccounts,
                                          String startDate, String endDate
            , int offPercent, Double maxDiscount) throws ParseException {
        new OffCode(TokenGenerator.generateOffCode(), new SimpleDateFormat("dd/MM/yyyy").parse(startDate),
                new SimpleDateFormat("dd/MM/yyyy").parse(endDate),
                appliedAccounts,
                offPercent,
                maxDiscount
        );
    }

    public void editDiscountCode(String code, ArrayList<Account> appliedAccounts,
                                        String startDate, String endDate
            , int offPercent, Double maxDiscount) throws Exception {
        OffCode offCode = OffCode.getOffIdById(code);
        if (offCode==null) throw new InvalidOffCodeException();
        if (appliedAccounts != null) offCode.setAppliedAccounts(appliedAccounts);
        if (startDate != null) offCode.setBeginDate(new SimpleDateFormat("dd/MM/yyyy").parse(startDate));
        if (endDate != null) offCode.setEndDate(new SimpleDateFormat("dd/MM/yyyy").parse(endDate));
        if (offPercent != 0) offCode.setOffPercentage(offPercent);
        if (maxDiscount != null) offCode.setMaxDiscount(maxDiscount);
    }

    public void removeDiscountCode(String code) throws InvalidOffCodeException {
        List<OffCode> offCodes = model.OffCode.getAllOffCodes();
        for (OffCode offCode : offCodes) {
            if (offCode.getOffCode().equals(code)) {
                offCodes.remove(offCode);
                break;
            }
            if (!offCode.getOffCode().equals(code)) throw new InvalidOffCodeException();
            model.OffCode.setAllOffCodes(offCodes);
        }
    }

    public Boolean isRequestValid(String requestId) {
        for (Request r : Request.getAllRequests()){
            if (r.getRequestId().equals(requestId)) return true;
        }
        return false;
    }

    public Request getRequestById(String id) {
        for (Request request : Manager.getAllRequests()) {
            if (request.getRequestId().equals(id)) return request;
        }
        return null;
    }

    public String getRequestDetail(String requestId) throws Exception {
        if (!isRequestValid(requestId)) throw new Exception("request id is invalid");
        return getRequestById(requestId).toString();
    }

    public void acceptRequest(String requestId) throws Exception {
        Request request = getRequestById(requestId);
        if (request==null) throw new Exception("request is invalid");
        request.accept();
    }
//not done
    public void rejectRequest(String requestId) throws Exception{
        Request r = getRequestById(requestId);
        if (r==null) throw new Exception("request id is invalid");
    }

    public Boolean isCategoryValid(String categoryName) {
        return getCategoryByName(categoryName) != null;
    }

    public void addCategory(String[] info) {
        if (!(currentAccount instanceof Manager)) return;
        Category newCategory = new Category(info[0], getCategoryByName(info[1]));
        List<Category> allCategories = ((Manager) currentAccount).getAllCategories();
        allCategories.add(newCategory);
        ((Manager) currentAccount).setAllCategories(allCategories);
    }

    private void editCategory(Category category, ArrayList<Product> removeProducts
            , ArrayList<Product> addProducts, String name, Category parent) {
        List<Product> products = category.getCategoryProducts();
        products.removeAll(removeProducts);
        products.addAll(addProducts);
        if (name != null) {
            if (!this.isCategoryValid(name)) category.setName(name);
        }
        if (parent != null) {
            //this doesn't fill the empty number in the map
            Map<Integer, Category> subCategories = category.getParentCategory().getSubCategories();
            subCategories.remove(category);
            category.getParentCategory().setSubCategories(subCategories);
            category.setParentCategory(parent);
            Map<Integer, Category> subCategories1 = parent.getSubCategories();
            subCategories1.put(subCategories1.size() + 1, category);
        }
    }

    public static void removeCategory(String categoryName) {
        Category category = getCategoryByName(categoryName);
        Manager manager = (Manager) currentAccount;
        List<Category> allCategories = manager.getAllCategories();
        allCategories.remove(getCategoryByName(categoryName));
        manager.setAllCategories(allCategories);
    }

    public static Category getCategoryByName(String name) {
        Manager manager = (Manager) currentAccount;
        for (Category category1 : manager.getAllCategories()) {
            if (category1.getName().equals(name)) return category1;
        }
        return null;
    }
//incomplete
    public void editCategoryName (String beforeName , String afterName) throws Exception{
        Category c2 = getCategoryByName(afterName);
        Category c = getCategoryByName(beforeName);
        if (c==null) throw new Exception("category is invalid");
        if (c2!=null) throw new Exception("this name is taken");
        c.setName(afterName);
    }

    public void editFirstName(String firstName) throws WrongFormatException {
        if (firstName.matches("[^a-zA-Z ]")) throw new WrongFormatException("name");
        if (currentAccount != null) {
            currentAccount.setFirstName(firstName);
        }
    }

    public void editLastName(String lastName) throws WrongFormatException {
        if (lastName.matches("[^a-zA-Z ]")) throw new WrongFormatException("name");

        if (currentAccount != null) {
            currentAccount.setLastName(lastName);
        }
    }

    public void editEmail(String email) throws Exception {
        checkEmail(email);
        if (currentAccount != null) {
            currentAccount.setEmail(email);
        }
    }

    private void checkEmail(String email) throws Exception {
        if (!email.matches("$\\w+@[a-zA-Z]+\\.\\w+^")) throw new WrongFormatException("email");
        for (Account a : model.People.Account.getAllAccounts()){
            if (a.getEmail().equals(email)) throw new Exception("email is used before");
        }
    }

    public void checkPhoneNumber(String number) throws Exception {
        if (!number.matches("^\\d{10}$")) throw new WrongFormatException("phone number");
        for (Account account : Account.getAllAccounts()){
            if (account.getPhoneNumber().equals(number)) throw new Exception("number is used before");
        }
    }

    public void addAccountToOffcode (String code,String id)throws Exception{
        Account a = Account.getAccountById(id);
        if (a==null) throw new InvalidUsernameException();
        OffCode o = OffCode.getOffIdById(code);
        if (o==null) throw new InvalidOffCodeException();
        List <Account> accounts = o.getAppliedAccounts();
        accounts.add(a);
        o.setAppliedAccounts(accounts);
    }

    public void removeAccountFromOffcode (String code , String id) throws Exception{
        Account a = Account.getAccountById(id);
        if (a==null) throw new InvalidUsernameException();
        OffCode o = OffCode.getOffIdById(code);
        if (o==null) throw new InvalidOffCodeException();
        List <Account> accounts = o.getAppliedAccounts();
        accounts.remove(a);
        o.setAppliedAccounts(accounts);
    }

    public List<OffCode> getAllOffcodes (){
        return OffCode.getAllOffCodes();
    }

    public ArrayList<Request> getAllRequests (){
        return model.Requests.Request.getAllRequests();
    }

//    public Request getRequestById(String id) throws Exception {
//        for (Request r : model.Requests.Request.getAllRequests()){
//            if (r.getRequestId().equals(id))  return r;
//        }
//        throw new Exception("invlaid Request id");
//    }

    public Product getProductById (String id) throws InvalidProductIdException{
        return Product.getProductById(id);
    }

    public Account getAccountById(String id) throws Exception{
        if (Account.getAccountById(id)!=null) return Account.getAccountById(id);
        throw new InvalidUsernameException();
    }
}
