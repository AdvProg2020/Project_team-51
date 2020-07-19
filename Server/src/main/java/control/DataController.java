package control;

import message.Message;
import model.People.Account;

import java.util.HashMap;
import java.util.Map;

public class DataController {

    private static DataController instance;
    private static Map<String, Account> clients = new HashMap<>();
    private static Map<Account, String> accounts = new HashMap<>();

    private DataController() {

    }

    public static DataController getInstance() {
        if (instance == null)
            instance = new DataController();
        return instance;
    }


    public boolean isOnline(String username) {
        return true;
    }

    public void register(Message message) {

    }

    public void login(Message message) {

    }

    public void logout(Message message) {

    }

    public void addManager(Message message) {

    }

    public void chargeWallet(Message message) {

    }

    public void acceptAddAuctionRequest(Message message) {

    }

    public void acceptAddCommentRequest(Message message) {

    }

    public void acceptAddItemRequest(Message message) {

    }

    public void acceptAddSellerForItemRequest(Message message) {

    }

    public void acceptAddSellerRequest(Message message) {

    }

    public void acceptEditAuctionRequest(Message message) {

    }

    public void acceptEditProductRequest(Message message) {

    }

    public void applyDiscountCode(Message message) {

    }

    public void pay(Message message) {

    }

    public void setٌWage(Message message) {

    }

    public void takeFromWallet(Message message) {

    }

    public void createAuction(Message message) {

    }

    public void createDiscountCode(Message message) {

    }

    public void createBid(Message message) {

    }

    public void createProduct(Message message) {

    }

    public void createCategory(Message message) {

    }

    public void createFileForSale(Message message) {

    }

    public void buyFile(Message message) {

    }

    public void incrementProductQuantity(Message message) {

    }

    public void decrementProductQuantity(Message message) {

    }

    public void removeProductFromCart(Message message) {

    }

    public void createAddCommentRequest(Message message) {

    }

    public void createAddItemRequest(Message message) {

    }

    public void createAddSellerForItemRequest(Message message) {

    }

    public void createAddSellerRequest() {

    }

    public void createEditAuctionRequest() {

    }

    public void createEditProductRequest(Message message) {

    }

}
