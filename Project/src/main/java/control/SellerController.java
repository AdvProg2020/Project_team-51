package control;

import control.Exceptions.InvalidAuctionIdException;
import control.Exceptions.InvalidUsernameException;
import control.Exceptions.NotAllowedActivityException;
import control.Exceptions.WrongFormatException;
import model.*;
import model.OrderLog.SellerLog;
import model.People.Account;
import model.People.Customer;
import model.People.Seller;
import model.Requests.AddAuctionRequest;
import model.Requests.AddItemRequest;
import model.Requests.EditProductRequest;

import javax.management.InstanceAlreadyExistsException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SellerController extends Controller {
    public SellerController(Account currentAccount) {
        super(currentAccount);
    }

    public static Boolean isThisFieldValid(Product product, String field) {
        return product.getAttributes().containsKey(field);
    }

    public static void editFirstName(String firstName) throws InstanceAlreadyExistsException {
        if (currentAccount.getFirstName().equals(firstName))
            throw new InstanceAlreadyExistsException();
        currentAccount.setFirstName(firstName);
    }

    public static void editLastName(String lastName) throws InstanceAlreadyExistsException {
        if (currentAccount.getLastName().equals(lastName))
            throw new InstanceAlreadyExistsException();
        currentAccount.setLastName(lastName);
    }

    public static void editEmail(String email) throws InstanceAlreadyExistsException, IllegalArgumentException {
        if (currentAccount.getEmail().equals(email))
            throw new InstanceAlreadyExistsException();
        var emails = getAllEmails();
        if (emails.contains(email))
            throw new IllegalArgumentException();

        currentAccount.setEmail(email);
    }

    public static void editPhoneNumber(String phoneNumber) throws InstanceAlreadyExistsException, IllegalArgumentException, WrongFormatException {
        if (currentAccount.getPhoneNumber().equals(phoneNumber))
            throw new InstanceAlreadyExistsException();
        var phoneNumbers = getAllPhoneNumbers();
        if (phoneNumbers.contains(phoneNumber))
            throw new IllegalArgumentException();
        if (phoneNumber.length() != 11 || !phoneNumber.startsWith("09"))
            throw new WrongFormatException("");
        currentAccount.setPhoneNumber(phoneNumber);
    }

    public static void editBrand(String brand) throws InstanceAlreadyExistsException, IllegalArgumentException {
        if (((Seller) currentAccount).getBrandName().equals(brand))
            throw new InstanceAlreadyExistsException();
        ((Seller) currentAccount).setBrandName(brand);
    }

    public static ArrayList<Category> getAllCategories() {
        return Category.getAllCategories();

    }

    public static boolean isProductNameTaken(String name) {
        for (Product p : Product.getAllProducts()) {
            if (p.getName().equals(name)) return true;
        }
        return false;
    }

    public static void editProduct(Product product, String field, String value) {

        if (currentAccount instanceof Seller) {
            for (Map.Entry<Attributes, String> attribute : product.getAttributes().entrySet()) {
                if (attribute.getKey().getField().equalsIgnoreCase(field)) {
                    new EditProductRequest(product, (Seller) currentAccount
                            , field.toLowerCase(), value);
                }
            }
        }
    }

    public static void addAuction(Auction auction) throws NotAllowedActivityException {
        if (currentAccount instanceof Seller)
            new AddAuctionRequest(auction, (Seller) currentAccount);
        else
            throw new NotAllowedActivityException("You are not allowed to add Auction");
    }

    //old edit auction

//    public static void editAuction(String auctionId, String field, String value, String description)
//            throws InvalidAuctionIdException, InvalidFieldException {
//
//        var auction = Auction.getAuctionById(auctionId);
//        if (auction == null) throw new InvalidAuctionIdException();
//
//        if (field.equalsIgnoreCase("begin date")) {
//            new EditAuctionRequest(auction, "begin date", value);
//        } else if (field.equalsIgnoreCase("end date")) {
//            new EditAuctionRequest(auction, "end date", value);
//        } else if (field.equalsIgnoreCase("off percentage")) {
//            new EditAuctionRequest(auction, "off percentage", value);
//        } else if (field.equalsIgnoreCase("add product")) {
//            new EditAuctionRequest(auction, "add product", value);
//        } else if (field.equalsIgnoreCase("remove product")) {
//            new EditAuctionRequest(auction, "remove product", value);
//        } else throw new InvalidFieldException("Field is invalid ! ");
//
//    } // old

    private static List<String> getAllEmails() {
        return Account.getAllAccounts().stream().map(Account::getEmail).collect(Collectors.toList());
    }

    private static List<String> getAllPhoneNumbers() {
        return Account.getAllAccounts().stream().map(Account::getPhoneNumber).collect(Collectors.toList());
    }

    public List<SellerLog> getSellerLogs() {
        return ((Seller) currentAccount).getHistoryOfSells();
    }

    // TODO: ۲۴/۰۶/۲۰۲۰  create new edit auction method
    public void editAuction(Auction auction, Date newStartdate, Date newEndDate
            , ArrayList<Product> newProducts, int newOffPercent) {
        auction.setBeginDate(newStartdate);
        auction.setEndDate(newEndDate);
        auction.setAppliedProducts(newProducts);
        auction.setOffPercentage(newOffPercent);
        auction.setAuctionStatus(Status.PENDING_EDIT);
    }

    public String viewCompanyInfo() {
        return "Brand name : " + ((Seller) currentAccount).getBrandName();
    }

    public List<String> showCategories() {
        var allCategories = Category.getAllCategories();
        return allCategories.stream().filter(c -> c.getSubCategories() == null).map(Category::getPathOfCategory)
                .collect(Collectors.toList());
    }

    public List<String> showSellersProducts() {
        return ((Seller) currentAccount).getAvailableProducts().stream().map(Product::toString).collect(Collectors.toList());
    }

    public void addProduct(Product product) throws NotAllowedActivityException {

        if (currentAccount instanceof Seller)
            new AddItemRequest(product, (Seller) currentAccount);
        else throw new NotAllowedActivityException("You are not allowed to add products .");

    }

    public void removeProduct(Product product) {

        if (currentAccount instanceof Seller) {
            ((Seller) currentAccount).getAvailableProducts().remove(product);
        }
    }

    public List<Category> listCategories() {
        var allCategories = Category.getAllCategories();
        return allCategories.stream().filter(c -> c.getSubCategories() == null)
                .collect(Collectors.toList());
    }

    public String showProductDetails(Product product) throws InvalidUsernameException {
        var productController = new SingleProductController(currentAccount, product);
        return productController.digest();
    }

    public List<Product> getSellerProducts() {
        return ((Seller) currentAccount).getAvailableProducts();
    }

    public List<String> viewSalesHistory() {
        return ((Seller) currentAccount).getHistoryOfSells().stream().map(s -> {
            StringBuilder builder = new StringBuilder();
            builder.append("order Id : " + s.getOrderID() + "\n"
                    + "Buyer : " + s.getBuyer().getFirstName() + " " +
                    s.getBuyer().getLastName() + "\n" +
                    "Products :" + "\n");
            (s.getItems().stream().map(ItemOfOrder::toString))
                    .forEach(builder::append);
            return builder.toString();
        })
                .collect(Collectors.toList());
    }

    public List<String> viewProductBuyers(Product product) {
        return ((Seller) currentAccount).getHistoryOfSells().stream()
                .filter(a -> a.getItems().stream().map(b -> (b.getProduct())).equals(product))
                .map(SellerLog::getBuyer)
                .distinct()
                .map(Customer::getUsername)
                .collect(Collectors.toList());
    }

    public List<Product> getProductsForAuction() {
        List products = ((Seller) currentAccount).getAvailableProducts();
        for (Auction auction : ((Seller) currentAccount).getAllAuctions()) {
            for (Product p : auction.getAppliedProducts()) {
                if (auction.getEndDate().after(new Date())) products.remove(p);
            }
        }
        return products;
    }

    public List<String> viewOffs() {
        return ((Seller) currentAccount).getAllAuctions().stream().map(a -> {
            StringBuilder builder = new StringBuilder();
            builder.append("ID : " + a.getAuctionId() +
                    "  Off : " + a.getOffPercentage() +
                    "  from : " + a.getBeginDate().toString() +
                    "  to : " + a.getEndDate().toString());
            return builder.toString();
        })
                .collect(Collectors.toList());
    }

    public String viewPersonalInfo() {
        Seller seller = (Seller) currentAccount;
        return seller.getUsername() + "\n" +
                "First Name : " + seller.getFirstName() + "\n" +
                "Last Name : " + seller.getLastName() + "\n" +
                "Email : " + seller.getEmail() + "\n" +
                "Phone : " + seller.getPhoneNumber() + "\n" +
                "Brand : " + seller.getBrandName();
    }

    public double getBalance() {
        return currentAccount.getBalance();
    }

    public Auction viewOffById(String id) throws InvalidAuctionIdException {
        Seller seller = (Seller) currentAccount;
        var auction = Auction.getAuctionById(id);
        if (auction == null || !seller.getAllAuctions().contains(auction))
            throw new InvalidAuctionIdException();
        return auction;
    }

    public List<Auction> getSellerAuctions() {
        return ((Seller) currentAccount).getAllAuctions();
    }

    public void changePassword(String text) {
        if (text.length() > 4) currentAccount.setPassword(text);
    }

    public boolean doesAuctionExist(String text) {
        for (Auction a : Auction.getAllAuctions()) {
            if (a.getAuctionId().equals(text)) return true;
        }
        return false;
    }
}
