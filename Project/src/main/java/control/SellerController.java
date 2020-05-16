package control;

import control.Exceptions.InvalidAuctionIdException;
import control.Exceptions.InvalidFieldException;
import control.Exceptions.InvalidProductIdException;
import control.Exceptions.NotAllowedActivityException;
import model.Attributes;
import model.Auction;
import model.OffCode;
import model.People.Account;
import model.People.Seller;
import model.Product;
import model.Requests.AddAuctionRequest;
import model.Requests.AddItemRequest;
import model.Requests.EditAuctionRequest;
import model.Requests.EditProductRequest;

import java.util.Map;

public class SellerController extends Controller {
    public SellerController(Account currentAccount) {
        super(currentAccount);
    }

    public static Boolean isThisFieldValid(Product product,String field) {
        return product.getAttributes().containsKey(field);
    }

    public static void editFirstName (String firstName) {
        if (currentAccount!=null)
            currentAccount.setFirstName(firstName);
    }

    public static void editLastName (String lastName) {
        if (currentAccount!=null)
            currentAccount.setLastName(lastName);
    }

    public static void editEmail (String email) {
        if (currentAccount!=null)
            currentAccount.setEmail(email);
    }

    public static void editPhoneNumber (String phoneNumber) {
        if (currentAccount!=null)
            currentAccount.setPhoneNumber(phoneNumber);
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

    public String viewPersonalInfo(){
        Seller seller = (Seller) currentAccount ;
        return seller.getUsername() + "\n" +
                "First Name : " + seller.getFirstName() + "\n" +
                "Last Name : " + seller.getLastName() + "\n" +
                "Email : " + seller.getEmail() + "\n" +
                "Phone : " + seller.getPhoneNumber() + "\n" +
                "Brand : " + seller.getBrandName() ;
    }
}
