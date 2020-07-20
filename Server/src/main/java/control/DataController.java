package control;

import Server.Server;
import control.Exceptions.ClientException;
import message.Message;
import model.People.Account;
import model.People.Customer;

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

    public Account getAccount(String username) {
        if (username == null) {
            Server.getInstance().serverPrint("Null Username In getAccount.");
            return null;
        }
        for (Account account : accounts.keySet()) {
            if (account.getUserName().equalsIgnoreCase(username)) {
                return account;
            }
        }
        return null;
    }

    public void registerCustomer(Message message) throws ClientException {
        if (message.getRegisterCustomerMessage().getCustomer().getUserName() == null ||
                getAccount(message.getRegisterCustomerMessage().getCustomer().getUserName()) != null) {
            throw new ClientException("Invalid Username!");
        } else if (message.getLoginMessage().getPassword() == null) {
            throw new ClientException("Invalid Password!");
        } else {
            Customer customer = message.getRegisterCustomerMessage().getCustomer();
            Customer.addCustomer(customer);
            accounts.put(customer, null);
            Server.getInstance().serverPrint(message.getRegisterCustomerMessage().getCustomer().getUserName() + " Is Created!");
        }
    }

    public void registerService(Message message) {

    }

    public void registerManager(Message message) {

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

    public void applyOffCode(Message message) {

    }

    public void payCart(Message message) {

    }

    public void setٌWage(Message message) {

    }

    public void takeFromWallet(Message message) {

    }

    public void createAuction(Message message) {

    }

    public void createOffCode(Message message) {

    }

    public void createBid(Message message) {

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

    public void loginCheck(Message message) throws ClientException {
        loginCheck(message.getSender());
    }

    public void loginCheck(String sender) throws ClientException {
        if (sender == null) {
            throw new ClientException("invalid message!");
        }
        if (!clients.containsKey(sender)) {
            throw new ClientException("Client Wasn't Added!");
        }
        if (clients.get(sender) == null) {
            throw new ClientException("Client Was Not LoggedIn");
        }
    }

}
