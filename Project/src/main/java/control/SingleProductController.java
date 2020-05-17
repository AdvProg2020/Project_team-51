package control;

import control.Exceptions.InvalidProductIdException;
import control.Exceptions.LackOfProductException;
import control.Exceptions.NotAllowedActivityException;
import control.Exceptions.SameProductForComparisonException;
import model.*;
import model.OrderLog.BuyerLog;
import model.OrderLog.Order;
import model.People.Account;
import model.People.Customer;
import model.People.Manager;
import model.People.Seller;
import model.Requests.AddCommentRequest;

import java.util.*;

public class SingleProductController extends Controller {

    private Product product ;

    public SingleProductController(Account currentAccount , Product product) {
        super(currentAccount);
        this.product = product ;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String digest(){

        StringBuilder digest = new StringBuilder();
        digest.append(product.getDescription()).append("\n");
        digest.append("Total Price : ").append(product.getPrice()).append("\n");
        digest.append("Discount : ").append(getProductDiscountAmount(product.getSellersForThisProduct().get(0).getUsername())).append("%").append("\n");
        digest.append("Category : ").append(product.getParentCategory().getName()).append("\n");
        digest.append("Sellers :" + "\n");
        for (Seller seller : product.getSellersForThisProduct()) {
            digest.append(seller.getBrandName()).append("\n");
        }
        digest.append("Rate : ").append(averageRate()).append("\n");

        return digest.toString() ;
    }

    public void addToCart(String seller) throws LackOfProductException, NotAllowedActivityException {

        if (currentAccount instanceof Seller | currentAccount instanceof Manager)
            throw new NotAllowedActivityException("You are not allowed to buy product");
        for (ItemOfOrder item : cart) {
            if(item.getProduct()== product){
                if (product.getQuantity() == 0)
                    throw new LackOfProductException("This Product Is Not Available !");
                else
                    item.addQuantity();
                return;
            }
        }

        var sellerOfProduct = Account.getAccountById(seller);
        if (sellerOfProduct instanceof  Seller)
        cart.add(new ItemOfOrder((Seller)sellerOfProduct,product , product.getPrice()
                , this.getProductDiscountAmount(seller), new Date()));

    }

    public Map<Attributes, String> showAttributes(){
        return product.getAttributes();
    }

    public HashMap<String,String> compare(String productId) throws SameProductForComparisonException, InvalidProductIdException {
        var comparedProduct = Product.getProductById(productId);
        if (comparedProduct.equals(product))
            throw new SameProductForComparisonException();
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

    public void addComment(String title ,String content) throws NotAllowedActivityException{
        if(currentAccount==null)
            throw new NotAllowedActivityException("You should login first !");
        new AddCommentRequest(TokenGenerator.generateRequestId(),new Comment(currentAccount,product,content,title,
                new Status(StatusStates.PENDING_CREATE),isCurrentAccountBuyer()));
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
