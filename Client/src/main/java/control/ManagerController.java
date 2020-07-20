package control;

import control.Exceptions.*;
import model.*;
import model.People.Account;
import model.People.Customer;
import model.People.Manager;
import model.People.Seller;
import model.Requests.AddSellerRequest;
import model.Requests.Request;

import java.util.*;

public class ManagerController extends Controller {

    public ManagerController(Account currentAccount) {
        super(currentAccount);
    }

    public static Attributes getAttributeById(String id) throws Exception {
        return Attributes.getAttributeById(id);
    }

    public static Boolean isHeAbleToCreateManger() {
        if (currentAccount instanceof Manager) return true;
        return !Account.doesManagerExist();
    }

    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public Boolean isThisFieldValid(Product product, String field) {
        return product.getAttributes().containsKey(field);
    }

    public void editPhoneNumber(String phoneNumber) throws Exception {
        checkPhoneNumber(phoneNumber);
        if (currentAccount != null) currentAccount.setPhoneNumber(phoneNumber);
    }

    public List<Account> getAllProfiles() {
        return Account.getAllAccounts();
    }

    public void deleteUser(String username) throws Exception {
        if (!hasUserWithThisUsername(username)) throw new InvalidUsernameException();
        if (currentAccount.getUserName().equals(username)) throw new Exception("cant delete logged in account");
        List<Account> allAccounts;
        allAccounts = Account.getAllAccounts();
        for (Account account : allAccounts) {
            if (account.getUserName().equals(username)) {
                allAccounts.remove(account);
                break;
            }
        }
        Account.setAllAccounts(allAccounts);
    }

    // what is this used for
    public void createManager(String[] info) {
        //new Manager(info[0], info[1], info[2], info[3], Double.valueOf(info[4]), info[5], info[6]);
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

    public ArrayList<Category> getAllCategories() {
        return Category.getAllCategories();
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

    public void tryCreateOffcode(String code) throws InvalidOffCodeException {
        if (isThisCodeValid(code)) throw new InvalidOffCodeException();
    }

    public boolean isCodeUsedBefore(String code) {
        List<OffCode> offCodes = model.OffCode.getAllOffCodes();
        for (OffCode offCode : offCodes) {
            if (offCode.getOffCode().equals(code)) {
                return true;
            }
        }
        return false;
    }

    public void createDiscountCode(ArrayList<Account> appliedAccounts,
                                   Date startDate, Date endDate
            , int offPercent, Double maxDiscount, int repeat) {
        new OffCode(TokenGenerator.generateOffCode(), startDate,
                endDate,
                appliedAccounts,
                offPercent,
                maxDiscount,
                repeat
        );
    }

    public void setAccountType(Account a, String type) {
        if (type.equals("Manager")) changeTypeToManager(a);
        if (type.equals("Customer")) changeTypeToCustomer(a);
        if (type.equals("Seller")) changeTypeToSeller(a);
    }

    public List<Product> getAllProducts() {
        return Product.getAllProducts();
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        for (Account a : Account.getAllAccounts()) {
            if (a instanceof Customer) customers.add((Customer) a);
        }
        return customers;
    }

    public void changeTypeToManager(Account a) {
        if (a instanceof Manager) return;
        try {
            deleteUser(a.getUserName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Manager(a.getUserName(),
                a.getPassword(),
                a.getFirstName(),
                a.getLastName(),
                a.getEmail(),
                a.getPhoneNumber());
    }

    public void changeTypeToSeller(Account a) {
        if (a instanceof Seller) return;
        try {
            deleteUser(a.getUserName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new AddSellerRequest(new Seller(a.getUserName(),
                a.getPassword(),
                a.getFirstName(),
                a.getLastName(),
                1000.0,
                a.getEmail(),
                a.getPhoneNumber(),
                "not Specified"));
    }

    public void changeTypeToCustomer(Account a) {
        if (a instanceof Customer) return;
        try {
            deleteUser(a.getUserName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Customer(a.getUserName(),
                a.getPassword(),
                a.getFirstName(),
                a.getLastName(),
                1000.0,
                a.getEmail(),
                a.getPhoneNumber());
    }

    public void editDiscountCode(OffCode offCode, List<Account> appliedAccounts,
                                 Date startDate, Date endDate
            , int offPercent, Double maxDiscount, int repeat) {
        offCode.setAppliedAccounts(appliedAccounts);
        offCode.setBeginDate(startDate);
        offCode.setEndDate(endDate);
        offCode.setOffPercentage(offPercent);
        offCode.setMaxDiscount(maxDiscount);
        offCode.setRepeat(repeat);
    }

    public void removeDiscountCode(String code) throws InvalidOffCodeException {
        List<OffCode> offCodes = model.OffCode.getAllOffCodes();
        for (OffCode offCode : offCodes) {
            if (offCode.getOffCode().equals(code)) {
                offCodes.remove(offCode);
                model.OffCode.setAllOffCodes(offCodes);
                return;
            }
        }
        throw new InvalidOffCodeException();
    }

    public Boolean isRequestValid(String requestId) {
        for (Request r : Request.getAllRequests()) {
            if (r.getRequestId().equals(requestId)) return true;
        }
        return false;
    }

    public Request getRequestById(String id) throws Exception {
        for (Request request : model.Requests.Request.getAllRequests()) {
            if (request.getRequestId().equals(id)) return request;
        }
        throw new Exception("request id is invalid");
    }

    public String getRequestDetail(String requestId) throws Exception {
        if (!isRequestValid(requestId)) throw new Exception("request id is invalid");
        return getRequestById(requestId).toString();
    }

    public void acceptRequest(String requestId) throws Exception {
        Request request = getRequestById(requestId);
        if (request == null) throw new Exception("request is invalid");
        request.accept();
    }

    //not done
    public void rejectRequest(String requestId) throws Exception {
        Request r = getRequestById(requestId);
        if (r == null) throw new Exception("request id is invalid");
    }

    public Boolean isCategoryValid(String categoryName) {
        return getCategoryByName(categoryName) != null;
    }

    public void addCategory(String name, String parentName, ArrayList<Attributes> attributes) {
        if (!(currentAccount instanceof Manager)) return;
        Category newCategory = new Category(name, getCategoryByName(parentName), attributes);
        List<Category> allCategories = ((Manager) currentAccount).getAllCategories();
        allCategories.add(newCategory);
        ((Manager) currentAccount).setAllCategories(allCategories);
    }

    public void editCategory(Category category, ArrayList<Product> newProducts, String name, Category parent) {
        List<Product> products = category.getCategoryProducts();
        category.getCategoryProducts().clear();
        category.getCategoryProducts().addAll(newProducts);
        if (name != null) {
            if (!this.isCategoryValid(name)) category.setName(name);
        }
        if (parent != null) {
            Map<Integer, Category> subCategories = category.getParentCategory().getSubCategories();
            subCategories.remove(category);
            category.getParentCategory().setSubCategories(subCategories);
            category.setParentCategory(parent);
            Map<Integer, Category> subCategories1 = parent.getSubCategories();
            subCategories1.put(subCategories1.size() + 1, category);
        }
    }

    public void addProductToCategory(String categoryId, String pid) throws Exception {
        Product product = Product.getProductById(pid);
        Category category1 = Category.getCategoryById(categoryId);
        category1.getCategoryProducts().add(product);
    }

    public void removeProductFromCategory(String categoryId, String pid) throws Exception {
        Product product = Product.getProductById(pid);
        Category category1 = Category.getCategoryById(categoryId);
        if (!category1.getCategoryProducts().contains(product))
            throw new Exception("category does not contaon the product");
        category1.getCategoryProducts().remove(product);
    }

    public void addChildCategory(String categoryName, String childCategoryName) throws Exception {
        Category parent = getCategoryByName(categoryName);
        Category child = getCategoryByName(childCategoryName);
        if (parent.getParentCategory().equals(child)) throw new Exception("a category's parent cannot be its child");
        if (parent.equals(child)) throw new Exception("a category cannot be its own child");
        if (!parent.getSubCategories().containsValue(child))
            parent.setSubCategories((Map<Integer, Category>) parent.getSubCategories()
                    .put(parent.getSubCategories().size(), child));
    }

    public void removeChildCategory(String categoryName, String childCategoryName) throws Exception {
        Category parent = getCategoryByName(categoryName);
        Category child = getCategoryByName(childCategoryName);
        if (parent.getSubCategories().containsValue(child)) {
            Integer key = getKeyByValue(parent.getSubCategories(), child);
            parent.getSubCategories().remove(key, child);
        }
    }

    public void removeCategory(String categoryName) throws Exception {
        Category category;
        if ((category = getCategoryByName(categoryName)) == null) throw new NoCategoriesFoundException(
                "category is invalid"
        );
        Category.getAllCategories().remove(category);
    }

    public Category getCategoryByName(String name) {
        Manager manager = (Manager) currentAccount;
        for (Category category : Category.getAllCategories()) {
            if (category.getName().equals(name)) return category;
        }
        return null;
    }

//incomplete

    public Category getCategoryById(String id) {
        Manager manager = (Manager) currentAccount;
        for (Category category1 : manager.getAllCategories()) {
            if (category1.getCategoryId().equals(id)) return category1;
        }
        return null;
    }

    public void editCategoryName(String beforeName, String afterName) throws Exception {
        Category c2 = getCategoryByName(afterName);
        Category c = getCategoryByName(beforeName);
        if (c == null) throw new Exception("category is invalid");
        if (c2 != null) throw new Exception("this name is taken");
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
        if (!email.matches("^\\w+@\\w+\\.\\w+$")) throw new WrongFormatException("email");
        for (Account a : model.People.Account.getAllAccounts()) {
            if (a.getEmail().equals(email)) throw new Exception("email is used before");
        }
    }

    public void checkPhoneNumber(String number) throws Exception {
        if (!number.matches("^\\d{11}$")) throw new WrongFormatException("phone number");
        for (Account account : Account.getAllAccounts()) {
            if (account.getPhoneNumber().equals(number)) throw new Exception("number is used before");
        }
    }

    public void addAccountToOffcode(String code, String id) throws Exception {
        Account a = Account.getAccountById(id);
        if (a == null) throw new InvalidUsernameException();
        OffCode o = OffCode.getOffIdById(code);
        if (o == null) throw new InvalidOffCodeException();
        List<Account> accounts = o.getAppliedAccounts();
        accounts.add(a);
        o.setAppliedAccounts(accounts);
    }

    public void removeAccountFromOffcode(String code, String id) throws Exception {
        Account a = Account.getAccountById(id);
        if (a == null) throw new InvalidUsernameException();
        OffCode o = OffCode.getOffIdById(code);
        if (o == null) throw new InvalidOffCodeException();
        List<Account> accounts = o.getAppliedAccounts();
        accounts.remove(a);
        o.setAppliedAccounts(accounts);
    }

    public List<OffCode> getAllOffcodes() {
        return OffCode.getAllOffCodes();
    }

//    public Request getRequestById(String id) throws Exception {
//        for (Request r : model.Requests.Request.getAllRequests()){
//            if (r.getRequestId().equals(id))  return r;
//        }
//        throw new Exception("invlaid Request id");
//    }

    public ArrayList<Request> getAllRequests() {
        ArrayList<Request> pendingRequests = new ArrayList<>();
        for (Request r : model.Requests.Request.getAllRequests()) {
            if (r.getStatus() == Status.PENDING_CREATE ||
                    r.getStatus() == Status.PENDING_EDIT) {
                pendingRequests.add(r);
            }
        }
        return pendingRequests;
    }

    public Product getProductById(String id) throws InvalidProductIdException {
        return Product.getProductById(id);
    }

    public Account getAccountById(String id) throws Exception {
        if (Account.getAccountById(id) != null) return Account.getAccountById(id);
        throw new InvalidUsernameException();
    }

    public void changePassword(String text) {
        currentAccount.setPassword(text);
    }
}
