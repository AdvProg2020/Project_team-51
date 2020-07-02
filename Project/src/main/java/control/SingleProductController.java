package control;

import control.Exceptions.*;
import model.*;
import model.OrderLog.Order;
import model.People.Account;
import model.People.Customer;
import model.People.Manager;
import model.People.Seller;
import model.Requests.AddCommentRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SingleProductController extends Controller {

    private Product product;

    public SingleProductController(Account currentAccount, Product product) {
        super(currentAccount);
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String digest() throws InvalidUsernameException {

        StringBuilder digest = new StringBuilder();
        digest.append(product.getDescription()).append("\n");
        digest.append("Total Price : ").append(product.getAveragePrice()).append("\n");
//        digest.append("Discount : ").append(getProductDiscountAmount(product.getSellersForThisProduct().get(0).getUsername())).append("%").append("\n");
//        digest.append("Category : ").append(product.getParentCategory().getName()).append("\n");
//        digest.append("Sellers :" + "\n");
//        for (Seller seller : product.getSellersForThisProduct()) {
//            digest.append(seller.getBrandName()).append("\n");
//        }
//        digest.append("Rate : ").append(averageRate()).append("\n");

        return digest.toString();
    }

    public void addToCart(String seller) throws LackOfProductException, NotAllowedActivityException, InvalidUsernameException {

        if (currentAccount instanceof Seller | currentAccount instanceof Manager)
            throw new NotAllowedActivityException("You are not allowed to buy product");
        for (ItemOfOrder item : cart) {
            if (item.getProduct() == product) {
                if (product.getTotalQuantity() == 0)
                    throw new LackOfProductException("This Product Is Not Available !");
                else
                    item.incrementQuantity();
                return;
            }
        }

        var sellerOfProduct = Account.getAccountById(seller);
        if (sellerOfProduct instanceof Seller)
            cart.add(new ItemOfOrder((Seller) sellerOfProduct, product, product.getPriceForSeller((Seller) sellerOfProduct)
                    , this.getProductDiscountAmount(seller), new Date()));

    }

    public Map<Attributes, String> showAttributes() {
        return product.getAttributes();
    }

    public HashMap<String, String> compare(String productId) throws SameProductForComparisonException, InvalidProductIdException {
        var comparedProduct = Product.getProductById(productId);
        if (comparedProduct.equals(product))
            throw new SameProductForComparisonException();
        var comparisonHashMap = new HashMap<String, String>();
        for (Map.Entry<Attributes, String> attribute : product.getAttributes().entrySet()) {
            String comparedProductAttribute = getProductAttribute(attribute.getKey().getField(), comparedProduct);
            if (comparedProduct != null) {
                comparisonHashMap.put(attribute.getValue(), comparedProductAttribute);
            } else {
                comparisonHashMap.put(attribute.getValue(), "-");
            }
        }

        for (Map.Entry<Attributes, String> attribute : comparedProduct.getAttributes().entrySet()) {
            String comparedProductAttribute = getProductAttribute(attribute.getKey().getField(), product);
            if (comparedProduct == null) {
                comparisonHashMap.put("-", attribute.getValue());
            }
        }

        return comparisonHashMap;
    }

    public void addComment(String title, String content) throws NotAllowedActivityException {
        if (currentAccount == null)
            throw new NotAllowedActivityException("You should login first !");
        new AddCommentRequest(new Comment(currentAccount, product, content, title,
                Status.PENDING_CREATE, isCurrentAccountBuyer()));
    }

    public List<Comment> getComments() {
        return product.getComments();
    }

    private int getProductDiscountAmount(String seller) throws InvalidUsernameException {

        var sellerOfProduct = Account.getAccountById(seller);
        if (sellerOfProduct instanceof Seller) {
            for (Auction auction : ((Seller) sellerOfProduct).getAllAuctions()) {
                for (Product appliedProduct : auction.getAppliedProducts()) {
                    if (product == appliedProduct) {
                        return auction.getOffPercentage();
                    }
                }
            }
        }

        return 0;
    }

    private boolean isCurrentAccountBuyer() {

        if (currentAccount instanceof Customer) {
            for (Order historyOfOrder : ((Customer) currentAccount).getHistoryOfOrders()) {
                for (ItemOfOrder item : historyOfOrder.getItems()) {
                    if (item.getProduct() == product)
                        return true;
                }
            }
        }

        return false;
    }

    private double averageRate() {

        double sum = 0;
        for (Rate rate : product.getRating()) {
            sum += rate.getScore();
        }

        return sum / product.getRating().size();
    }

    private String getProductAttribute(String filed, Product product) {
        for (Map.Entry<Attributes, String> attribute : product.getAttributes().entrySet()) {
            if (attribute.getKey().getField().equals(filed))
                return attribute.getValue();
        }

        return null;
    }


}
