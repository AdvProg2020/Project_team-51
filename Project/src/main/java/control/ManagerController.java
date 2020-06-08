package control;

import control.Exceptions.NotAllowedActivityException;
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

    public static Boolean isThisFieldValid(Product product, String field) {
        return product.getAttributes().containsKey(field);
    }

    public static void editPhoneNumber(String phoneNumber) {
        if (currentAccount != null) currentAccount.setPhoneNumber(phoneNumber);
    }

    public static void deleteUser(String username) {
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

    public static Boolean isHeAbleToCreateManger() throws Exception {
        if (currentAccount instanceof Manager) return true;
        if (!Account.doesManagerExist()) return true;
        throw new NotAllowedActivityException("only managers are able to create manager");
    }

    public static void removeProduct(String productId) {
        ArrayList<Product> allProducts = model.Product.getAllProducts();
        for (Product product : allProducts) {
            if (product.getProductId().equals(productId)) {
                allProducts.remove(product);
                break;
            }
        }
        model.Product.setAllProducts(allProducts);
    }

    public static Boolean isThisCodeValid(String code) {
        List<OffCode> offCodes = model.OffCode.getAllOffCodes();
        for (OffCode offCode : offCodes) {
            if (offCode.getOffCode().equals(code)) {
                return offCode.getBeginDate().before(new Date()) && offCode.getEndDate().after(new Date());
            }
        }
        return false;
    }

    public static void createDiscountCode(String offCode, ArrayList<Account> appliedAccounts,
                                          String startDate, String endDate
            , int offPercent, Double maxDiscount) throws ParseException {
        new OffCode(offCode, new SimpleDateFormat("dd/MM/yyyy").parse(startDate),
                new SimpleDateFormat("dd/MM/yyyy").parse(endDate),
                appliedAccounts,
                offPercent,
                maxDiscount
        );
    }

    public static void editDiscountCode(String code, ArrayList<Account> appliedAccounts,
                                        String startDate, String endDate
            , int offPercent, Double maxDiscount) throws ParseException {
        OffCode offCode = OffCode.getOffIdById(code);
        if (appliedAccounts != null) offCode.setAppliedAccounts(appliedAccounts);
        if (startDate != null) offCode.setBeginDate(new SimpleDateFormat("dd/MM/yyyy").parse(startDate));
        if (endDate != null) offCode.setEndDate(new SimpleDateFormat("dd/MM/yyyy").parse(endDate));
        if (offPercent != 0) offCode.setOffPercentage(offPercent);
        if (maxDiscount != 0) offCode.setMaxDiscount(maxDiscount);
    }

    public static void removeDiscountCode(String code) {
        List<OffCode> offCodes = model.OffCode.getAllOffCodes();
        for (OffCode offCode : offCodes) {
            if (offCode.getOffCode().equals(code)) {
                offCodes.remove(offCode);
                break;
            }
            model.OffCode.setAllOffCodes(offCodes);
        }
    }

    // date must be in format dd/mm/yyyy

    public static Boolean isRequestValid(String requestId) {
        return true;
    }

    // date must be in format dd/mm/yyyy

    public static Request getRequestDetail(String requestId) {
        return null;
    }

    public static void acceptRequest(String requestId) {

    }

    public static void rejectRequest(String requestId) {

    }

    public static Boolean isCategoryValid(String categoryName) {
        return getCategoryByName(categoryName) != null;
    }

    public static void addCategory(String[] info) {
        if (!(currentAccount instanceof Manager)) return;
        Category newCategory = new Category(info[0], getCategoryByName(info[1]));
        List<Category> allCategories = ((Manager) currentAccount).getAllCategories();
        allCategories.add(newCategory);
        ((Manager) currentAccount).setAllCategories(allCategories);
    }

    public static void editCategory(Category category, ArrayList<Product> removeProducts
            , ArrayList<Product> addProducts, String name, Category parent) {
        List<Product> products = category.getCategoryProducts();
        products.removeAll(removeProducts);
        products.addAll(addProducts);
        if (name != null) {
            if (!isCategoryValid(name)) category.setName(name);
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

    private static Request getRequestById(String id) {
        for (Request request : Manager.getAllRequests()) {
            if (request.getRequestId().equals(id)) return request;
        }
        return null;
    }

    private static Category getCategoryByName(String name) {
        Manager manager = (Manager) currentAccount;
        for (Category category1 : manager.getAllCategories()) {
            if (category1.getName().equals(name)) return category1;
        }
        return null;
    }

    public void editFirstName(String firstName) {
        if (currentAccount != null) {
            currentAccount.setFirstName(firstName);
        }
    }

    public void editLastName(String lastName) {
        if (currentAccount != null) {
            currentAccount.setLastName(lastName);
        }
    }

    public void editEmail(String email) {
        if (currentAccount != null) {
            currentAccount.setEmail(email);
        }
    }
}
