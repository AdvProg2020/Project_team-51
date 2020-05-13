package control;

import model.Attributes;
import model.Auction;
import model.OffCode;
import model.People.Account;
import model.People.Seller;
import model.Product;
import model.Requests.Request;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SellerController extends Controller {
    public SellerController(Account currentAccount) {
        super(currentAccount);
    }

    public static Boolean isThisFieldValid(Product product,String field) {
        return product.getAttributes().keySet().contains(field);
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

    public static Boolean isThisPidValid(String productId){
        return Product.getAllProducts().contains(Product.getProductById(productId));
    }

    public static void editProduct(String productId, String field , String value , String description){
//        var product = Product.getProductById(productId);
//        if (currentAccount instanceof Seller){
//            for (Map.Entry<Attributes, String> attribute : product.getAttributes().entrySet()) {
//                if (attribute.getKey().getField().equals(field)){
//                    new Request<Map.Entry<Attributes,String>> (attribute,currentAccount,value,description,new Date());
//                }
//            }
//        }
    }


    public static void addProduct(Product product){
//        if(currentAccount instanceof  Seller){
//            new Request<Product>(product
//                    ,currentAccount,"" , "New Product Request" , new Date());
//        }
    }

    public static void removeProduct(String productId){
        if (currentAccount instanceof Seller){
            ((Seller) currentAccount).getAvailableProducts().remove(Product.getProductById(productId));
        }
    }

    public static Boolean isOffIdValid(String offId){
        return OffCode.getAllOffCodes().contains(OffCode.getOffIdByProductId(offId));
    }


    public static void editOffId(String auctionId , String field , String value , String description){
        var auction = Auction.getAuctionById(auctionId);
        if (auction!=null){
            if (field.equals("begin date")){
                // send proper request
            } else if (field.equals("end date")){
                // send proper request
            } else if (field.equals("off percentage")){
                // send proper request
            }
        }
    }

    public static void addOffId(Auction auction){
//        if(currentAccount instanceof  Seller){
//            new Request<Auction>(auction
//                    ,currentAccount,"" , "New Auction Request" , new Date());
//        }
    }

}
