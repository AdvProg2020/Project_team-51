package control;

import model.*;
import model.OrderLog.BuyerLog;
import model.OrderLog.Order;
import model.People.Account;
import model.People.Customer;
import model.People.Seller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SingleProductController extends Controller {

    private Product product ;

    public SingleProductController(Account currentAccount) {
        super(currentAccount);
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String digest(){

        StringBuilder digest = new StringBuilder("");
        digest.append(product.getDescription() + "\n");
        digest.append("Total Price : " + product.getPrice() + "\n");
        digest.append("Discount : " + (getProductDiscountAmount(product.getSellersForThisProduct().get(0).getUsername())) + "%" + "\n");
        digest.append("Category : " + product.getParentCategory().getName() + "\n");
        digest.append("Sellers :" + "\n");
        for (Seller seller : product.getSellersForThisProduct()) {
            digest.append(seller.getBrandName() + "\n");
        }
        digest.append("Rate : " + averageRate() + "\n");

        return digest.toString() ;
    }

    public void addToCart(String seller){

        for (ItemOfOrder item : cart) {
            if(item.getProduct()== product){
                item.addQuantity();
                return;
            }
        }

        var sellerOfProduct = Account.getAccountById(seller);
        if (sellerOfProduct instanceof  Seller)
        cart.add(new ItemOfOrder((Seller)sellerOfProduct,product , product.getPrice()
                , this.getProductDiscountAmount(seller), new Date()));

    }

    public HashMap<Attributes, String> showAttributes(){
        return product.getAttributes();
    }

    public HashMap<String,String> compare(String productId){
        var comparedProduct = Product.getProductById(productId);
        var comparisonHashMap = new HashMap<String,String>();
        for (Map.Entry<Attributes, String> attribute : product.getAttributes().entrySet()) {
            String comparedProductAttribute = getProductAttribute(attribute.getKey().getField(),comparedProduct);
            if ( comparedProduct != null){
                comparisonHashMap.put(attribute.getValue(), comparedProductAttribute);
            }
            else {
                comparisonHashMap.put(attribute.getValue(), "-");
            }
        }

        for (Map.Entry<Attributes, String> attribute : comparedProduct.getAttributes().entrySet()) {
            String comparedProductAttribute = getProductAttribute(attribute.getKey().getField(),product);
            if ( comparedProduct == null){
                comparisonHashMap.put("-", attribute.getValue());
            }
        }

        return comparisonHashMap;
    }

    public void addComment(String title ,String content){
        product.getComments().add(new Comment(currentAccount,product,content,title,
                new Status(StatusStates.CREATE_PROCESSING),isCurrentAccountBuyer()));
    }

    public ArrayList<Comment> getComments (){
        return product.getComments();
    }

    private double getProductDiscountAmount(String seller){
        var sellerOfProduct = Account.getAccountById(seller);
        if (sellerOfProduct instanceof Seller){
            for (Auction auction : ((Seller) sellerOfProduct).getAllAuctions()) {
                for (Product appliedProduct : auction.getAppliedProducts()) {
                    if (product == appliedProduct) {
                        return auction.getOffPercentage();
                    }
                }
            }
        }

        return  0;
    }

    private boolean isCurrentAccountBuyer(){
        if (currentAccount instanceof Customer){
            for (Order historyOfOrder : ((Customer) currentAccount).getHistoryOfOrders()) {
                for (ItemOfOrder item : ((BuyerLog) historyOfOrder).getItems()) {
                    if (item.getProduct()==product)
                        return true;
                }
            }
        }
        return false;
    }

    private double averageRate(){

        double sum = 0 ;
        for (Rate rate : product.getRating()) {
            sum += rate.getScore();
        }

        return sum/product.getRating().size() ;
    }

    private String getProductAttribute(String filed , Product product){
        for (Map.Entry<Attributes, String> attribute : product.getAttributes().entrySet()) {
            if (attribute.getKey().getField().equals(filed))
                return attribute.getValue();
        }

        return null;
    }
}
