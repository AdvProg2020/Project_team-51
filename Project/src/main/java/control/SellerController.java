package control;

import control.Exceptions.*;
import model.*;
import model.OrderLog.SellerLog;
import model.People.Account;
import model.People.Customer;
import model.People.Seller;
import model.Requests.AddAuctionRequest;
import model.Requests.AddItemRequest;
import model.Requests.EditAuctionRequest;
import model.Requests.EditProductRequest;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SellerController extends Controller {
    public SellerController(Account currentAccount) {
        super(currentAccount);
    }

    public static Boolean isThisFieldValid(Product product,String field) {
        return product.getAttributes().containsKey(field);
    }

    public static void editFirstName (String firstName) throws InstanceAlreadyExistsException {
        if (currentAccount.getFirstName().equals(firstName))
            throw new InstanceAlreadyExistsException();
        currentAccount.setFirstName(firstName);
    }

    public static void editLastName (String lastName) throws InstanceAlreadyExistsException  {
        if (currentAccount.getLastName().equals(lastName))
            throw new InstanceAlreadyExistsException();
        currentAccount.setLastName(lastName);
    }

    public static void editEmail (String email) throws InstanceAlreadyExistsException , IllegalArgumentException{
        if (currentAccount.getEmail().equals(email))
            throw new InstanceAlreadyExistsException();
        var emails = getAllEmails();
        if(emails.contains(email))
            throw new IllegalArgumentException();
        
        currentAccount.setEmail(email);
    }

    public static void editPhoneNumber (String phoneNumber) throws InstanceAlreadyExistsException , IllegalArgumentException , WrongFormatException {
        if (currentAccount.getPhoneNumber().equals(phoneNumber))
            throw new InstanceAlreadyExistsException();
        var phoneNumbers = getAllPhoneNumbers();
        if(phoneNumbers.contains(phoneNumber))
            throw new IllegalArgumentException();
        if ( phoneNumber.length() != 11 || !phoneNumber.startsWith("09"))
            throw new WrongFormatException("");
        currentAccount.setPhoneNumber(phoneNumber);
    }

    public static void editBrand (String brand) throws InstanceAlreadyExistsException , IllegalArgumentException {
        if (((Seller)currentAccount).getBrandName().equals(brand))
            throw new InstanceAlreadyExistsException();
        ((Seller) currentAccount).setBrandName(brand);
    }

    public String viewCompanyInfo(){
        return "Brand name : " + ((Seller)currentAccount).getBrandName() ;
    }

    public List<String> showCategories(){
        var allCategories = Category.getAllCategories();
        return allCategories.stream().filter(c -> c.getSubCategories()==null).map(Category::getPathOfCategory)
                .collect(Collectors.toList());
    }

    public List<String> showSellersProducts(){
        return ((Seller)currentAccount).getAvailableProducts().stream().map(Product::toString).collect(Collectors.toList());
    }
    public static Boolean isThisPidValid(String productId) throws InvalidProductIdException {
        return Product.getAllProducts().contains(Product.getProductById(productId));
    }

    public static void editProduct(String productId, String field , String value , String description) throws InvalidProductIdException {

        var product = Product.getProductById(productId);
        if (currentAccount instanceof Seller){
            for (Map.Entry<Attributes, String> attribute : product.getAttributes().entrySet()) {
                if (attribute.getKey().getField().equals(field)){
                    new EditProductRequest(TokenGenerator.generateRequestId(),product,(Seller) currentAccount , field , value);
                }
            }
        }
    }

    public static void addProduct(Product product) throws NotAllowedActivityException {

        if(currentAccount instanceof  Seller)
           new AddItemRequest(TokenGenerator.generateRequestId(),product,(Seller) currentAccount);
         else throw new NotAllowedActivityException("You are not allowed to add products .");

    }

    public static void removeProduct(String productId) throws InvalidProductIdException {

        if (currentAccount instanceof Seller){
            ((Seller) currentAccount).getAvailableProducts().remove(Product.getProductById(productId));
        }
    }

    public static Boolean isOffIdValid(String offId){
        return OffCode.getAllOffCodes().contains(OffCode.getOffIdById(offId));
    }

    public static void editAuction(String auctionId , String field , String value , String description)
            throws InvalidAuctionIdException , InvalidFieldException {

        var auction = Auction.getAuctionById(auctionId);
        if (auction==null) throw new InvalidAuctionIdException();

        if (field.equalsIgnoreCase("begin date")){
            new EditAuctionRequest(TokenGenerator.generateRequestId(),auction,"begin date",value);
        } else if (field.equalsIgnoreCase("end date")){
            new EditAuctionRequest(TokenGenerator.generateRequestId(),auction,"end date",value);
        } else if (field.equalsIgnoreCase("off percentage")){
            new EditAuctionRequest(TokenGenerator.generateRequestId(),auction,"off percentage",value);
        } else if (field.equalsIgnoreCase("add product")){
            new EditAuctionRequest(TokenGenerator.generateRequestId(),auction,"add product",value);
        } else if (field.equalsIgnoreCase("remove product")){
            new EditAuctionRequest(TokenGenerator.generateRequestId(),auction,"remove product",value);
        } else throw new InvalidFieldException("Field is invalid ! ");

    }

    public static void addAuction(Auction auction) throws NotAllowedActivityException{
        if(currentAccount instanceof  Seller)
            new AddAuctionRequest(TokenGenerator.generateRequestId() , auction , (Seller) currentAccount);
        else
            throw new NotAllowedActivityException("You are not allowed to add Auction");
    }

    public String showProductDetails(Product product){
        var productController = new SingleProductController(currentAccount,product);
        return productController.digest();
    }

    public List<String> viewSalesHistory(){
        return ((Seller)currentAccount).getHistoryOfSells().stream().map(s -> { StringBuilder builder = new StringBuilder("");
                                                                    builder.append("order Id : " + s.getOrderID() + "\n"
                                                                    + "Buyer : " + s.getBuyer().getFirstName() + " " +
                                                                    s.getBuyer().getLastName() + "\n"  +
                                                                    "Products :" + "\n" );
                                                                    (s.getItems().stream().map(ItemOfOrder::toString))
                                                                     .forEach(builder::append);
                                                                     return builder.toString();})
                                                                    .collect(Collectors.toList());
    }

    public List<String> viewProductBuyers(Product product){
        return ((Seller)currentAccount).getHistoryOfSells().stream()
                .filter( a -> a.getItems().stream().map(b-> (b.getProduct())).equals(product))
                .map(SellerLog::getBuyer)
                .distinct()
                .map(Customer::getUsername)
                .collect(Collectors.toList());
    }

    public List<String> viewOffs () {
        return ((Seller) currentAccount).getAllAuctions().stream().map(a -> { StringBuilder builder = new StringBuilder("");
                                                                             builder.append("ID : " + a.getAuctionId() +
                                                                                            "  Off : " + a.getOffPercentage() +
                                                                                            "  from : " + a.getBeginDate().toString() +
                                                                                            "  to : " + a.getEndDate().toString());
                                                                              return builder.toString(); })
                                                                              .collect(Collectors.toList());
    }

    public String viewPersonalInfo(){
        Seller seller = (Seller) currentAccount ;
        return seller.getUsername() + "\n" +
                "First Name : " + seller.getFirstName() + "\n" +
                "Last Name : " + seller.getLastName() + "\n" +
                "Email : " + seller.getEmail() + "\n" +
                "Phone : " + seller.getPhoneNumber() + "\n" +
                "Brand : " + seller.getBrandName() ;
    }

    public double getBalance(){
        return ((Seller)currentAccount).getBalance();
    }
    
    private static List<String> getAllEmails(){
        return Account.getAllAccounts().stream().map(Account::getEmail).collect(Collectors.toList());
    }

    private static List<String> getAllPhoneNumbers(){
        return Account.getAllAccounts().stream().map(Account::getPhoneNumber).collect(Collectors.toList());
    }

    public Auction viewOffById(String id) throws InvalidAuctionIdException{
        Seller seller = (Seller) currentAccount ;
        var auction = Auction.getAuctionById(id);
        if (auction==null || !seller.getAllAuctions().contains(auction))
            throw new InvalidAuctionIdException();
        return auction;
    }
}
