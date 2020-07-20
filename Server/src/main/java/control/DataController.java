package control;

import Server.ClientPortal;
import Server.Server;
import control.Exceptions.*;
import message.Message;
import model.Attributes;
import model.Bid;
import model.OffCode;
import model.People.Account;
import model.People.Customer;
import model.People.Manager;
import model.People.Seller;
import model.Requests.*;

import java.text.ParseException;
import java.util.ArrayList;
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

    public void putClient(String name, Account account) {
        clients.put(name, account);
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
            Server.getInstance().serverPrint(message.getRegisterCustomerMessage().getCustomer().getUserName() + " Is Created!");
        }
    }


    public void registerManager(Message message) throws ClientException {
        if (message.getRegisterManagerMessage().getManager().getUserName() == null ||
                getAccount(message.getRegisterManagerMessage().getManager().getUserName()) != null) {
            throw new ClientException("Invalid Username!");
        } else if (message.getLoginMessage().getPassword() == null) {
            throw new ClientException("Invalid Password!");
        } else if (ManagerController.isThereAnyManager()) {
            Server.getInstance().serverPrint("manager already exists");
        } else {
            Manager manager = message.getRegisterManagerMessage().getManager();
            Manager.addManager(manager);
            Server.getInstance().serverPrint(message.getRegisterManagerMessage().getManager().getUserName() + " Is Created!");
        }
    }

    public void login(Message message) throws ClientException {
        if (message.getLoginMessage().getUsername() == null || message.getSender() == null) {
            throw new ClientException("invalid message!");
        }
        Account account = getAccount(message.getLoginMessage().getUsername());
        if (!ClientPortal.getInstance().hasThisClient(message.getSender())) {
            throw new ClientException("Client Wasn't Added!");
        } else if (account == null) {
            throw new ClientException("Username Not Found!");
        } else if (!account.getPassword().equalsIgnoreCase(message.getLoginMessage().getPassword())) {
            throw new ClientException("Incorrect PassWord!");
        } else if (accounts.get(account) != null) {
            throw new ClientException("Selected Username Is Online!");
        } else if (clients.get(message.getSender()) != null) {
            throw new ClientException("Your Client Has Logged In Before!");
        } else {
            accounts.replace(account, message.getSender());
            clients.replace(message.getSender(), account);
            // TODO -> Send Done Message
            Server.getInstance().serverPrint(message.getSender() + " Is Logged In");
        }
    }

    public void logout(Message message) throws ClientException {
        loginCheck(message);
        accounts.replace(clients.get(message.getSender()), null);
        clients.replace(message.getSender(), null);
        Server.getInstance().serverPrint(message.getSender() + " Is Logged Out.");
        // TODO -> Send Done Message
    }

    public void addManager(Message message) throws ClientException {
        Account account = message.getRegisterManagerByManagerMessage().getManager();
        if (account instanceof Manager) {
            Manager.addManager((Manager) account);
        } else {
            throw new ClientException("You are not allowed to do that");
        }
    }

    public void addToCart(Message message) throws ClientException, InvalidUsernameException, LackOfProductException, NotAllowedActivityException {
        Account account = message.getRegisterManagerByManagerMessage().getManager();
        if (message.getSender() == null) {
            throw new ClientException("invalid message!");
        }
        new SingleProductController(account, message.getAddToCartMessage().getProduct())
                .addToCart(message.getAddToCartMessage().getSeller().getUserName());
    }


    public void acceptAddAuctionRequest(Message message) throws ClientException {
        loginCheck(message);
        if (message.getSender() == null) {
            throw new ClientException("invalid message!");
        } else {
            Account account = getAccount(message.getSender());
            AddAuctionRequest addAuctionRequest = message.getAcceptAddAuctionRequestMessage().getAddAuctionRequest();
            if (addAuctionRequest == null) {
                throw new ClientException("null message!");
            } else if (!(account instanceof Manager)) {
                throw new ClientException("You are not allowed to do that");
            } else {
                addAuctionRequest.accept();
                Server.getInstance().serverPrint("Request got accepted!");
            }
        }
    }

    public void acceptAddCommentRequest(Message message) throws ClientException {
        loginCheck(message);
        if (message.getSender() == null) {
            throw new ClientException("invalid message!");
        } else {
            Account account = getAccount(message.getSender());
            AddCommentRequest addCommentRequest = message.getAcceptAddCommentRequestMessage().getAddCommentRequest();
            if (addCommentRequest == null) {
                throw new ClientException("null message!");
            } else if (!(account instanceof Manager)) {
                throw new ClientException("You are not allowed to do that");
            } else {
                addCommentRequest.accept();
                Server.getInstance().serverPrint("Request got accepted!");
//                Server.getInstance().addToSendingMessages();
            }
        }
    }

    public void acceptAddItemRequest(Message message) throws InvalidProductIdException, ClientException {
        loginCheck(message);
        if (message.getSender() == null) {
            throw new ClientException("invalid message!");
        } else {
            Account account = getAccount(message.getSender());
            AddItemRequest addItemRequest = message.getAcceptAddItemRequestMessage().getAddItemRequest();
            if (addItemRequest == null) {
                throw new ClientException("null message!");
            } else if (!(account instanceof Manager)) {
                throw new ClientException("You are not allowed to do that");
            } else {
                addItemRequest.accept();
                Server.getInstance().serverPrint("Request got accepted!");
            }
        }
    }

    public void acceptAddSellerForItemRequest(Message message) throws ClientException, InvalidProductIdException {
        loginCheck(message);
        if (message.getSender() == null) {
            throw new ClientException("invalid message!");
        } else {
            Account account = getAccount(message.getSender());
            AddSellerForItemRequest addSellerForItemRequest = message.getAcceptAddSellerForItemRequestMessage().getAddSellerForItemRequest();
            if (addSellerForItemRequest == null) {
                throw new ClientException("null message!");
            } else if (!(account instanceof Manager)) {
                throw new ClientException("You are not allowed to do that");
            } else {
                addSellerForItemRequest.accept();
                Server.getInstance().serverPrint("Request got accepted!");
            }
        }
    }

    public void acceptAddSellerRequest(Message message) throws ClientException {
        loginCheck(message);
        if (message.getSender() == null) {
            throw new ClientException("invalid message!");
        } else {
            Account account = getAccount(message.getSender());
            AddSellerRequest addSellerRequest = message.getAcceptAddSellerRequestMessage().getAddSellerRequest();
            if (addSellerRequest == null) {
                throw new ClientException("null message!");
            } else if (!(account instanceof Manager)) {
                throw new ClientException("You are not allowed to do that");
            } else {
                addSellerRequest.accept();
                Server.getInstance().serverPrint("Request got accepted!");
            }
        }
    }

    public void acceptEditAuctionRequest(Message message) throws ClientException, InvalidProductIdException, ParseException {
        loginCheck(message);
        if (message.getSender() == null) {
            throw new ClientException("invalid message!");
        } else {
            Account account = getAccount(message.getSender());
            EditAuctionRequest editAuctionRequest = message.getAcceptEditAuctionRequestMessage().getEditAuctionRequest();
            if (editAuctionRequest == null) {
                throw new ClientException("null message!");
            } else if (!(account instanceof Manager)) {
                throw new ClientException("You are not allowed to do that");
            } else {
                editAuctionRequest.accept();
                Server.getInstance().serverPrint("Request got accepted!");
            }
        }
    }

    public void acceptEditProductRequest(Message message) throws ClientException {
        loginCheck(message);
        if (message.getSender() == null) {
            throw new ClientException("invalid message!");
        } else {
            Account account = getAccount(message.getSender());
            EditProductRequest editProductRequest = message.getAcceptEditProductRequestMessage().getEditProductRequest();
            if (editProductRequest == null) {
                throw new ClientException("null message!");
            } else if (!(account instanceof Manager)) {
                throw new ClientException("You are not allowed to do that");
            } else {
                editProductRequest.accept();
                Server.getInstance().serverPrint("Request got accepted!");
            }
        }
    }

    public void createAddAuctionRequest(Message message) throws ClientException {
        loginCheck(message);
        if (message.getSender() == null) {
            throw new ClientException("invalid message!");
        } else {
            Account account = getAccount(message.getSender());
            if (!(account instanceof Seller)) {
                throw new ClientException("You are not allowed to do that");
            } else {
                var createAddAuctionRequest = message.getCreateAddAuctionRequestMessage();
                new AddAuctionRequest(createAddAuctionRequest.getAuction(), createAddAuctionRequest.getSeller());
                Server.getInstance().serverPrint("Request has created!");
            }
        }
    }


    public void createAddCommentRequest(Message message) throws ClientException {
        loginCheck(message);
        if (message.getSender() == null) {
            throw new ClientException("invalid message!");
        } else {
            Account account = getAccount(message.getSender());
            if (!(account instanceof Customer)) {
                throw new ClientException("You are not allowed to do that");
            } else {
                var createAddCommentRequest = message.getCreateAddCommentRequestMessage();
                new AddCommentRequest(createAddCommentRequest.getComment());
                Server.getInstance().serverPrint("Request has created!");
            }
        }
    }

    public void createAddItemRequest(Message message) throws ClientException {
        loginCheck(message);
        if (message.getSender() == null) {
            throw new ClientException("invalid message!");
        } else {
            Account account = getAccount(message.getSender());
            if (!(account instanceof Seller)) {
                throw new ClientException("You are not allowed to do that");
            } else {
                var createAddItemRequest = message.getCreateAddItemRequestMessage();
                new AddItemRequest(createAddItemRequest.getProduct(), createAddItemRequest.getSeller());
                Server.getInstance().serverPrint("Request has created!");
            }
        }
    }

    public void createAddSellerForItemRequest(Message message) throws ClientException {
        loginCheck(message);
        if (message.getSender() == null) {
            throw new ClientException("invalid message!");
        } else {
            Account account = getAccount(message.getSender());
            if (!(account instanceof Seller)) {
                throw new ClientException("You are not allowed to do that");
            } else {
                var createAddSellerForItemRequest = message.getCreateAddSellerForItemRequestMessage();
                new AddSellerForItemRequest(createAddSellerForItemRequest.getProduct(),
                        createAddSellerForItemRequest.getSeller(),
                        createAddSellerForItemRequest.getQuantity(),
                        createAddSellerForItemRequest.getPrice());
                Server.getInstance().serverPrint("Request has created!");
            }
        }
    }

    public void createAddSellerRequest(Message message) throws ClientException {
        loginCheck(message);
        if (message.getSender() == null) {
            throw new ClientException("invalid message!");
        } else {
            Account account = getAccount(message.getSender());
            if (!(account instanceof Seller)) {
                throw new ClientException("You are not allowed to do that");
            } else {
                var createAddSellerRequest = message.getCreateAddSellerRequestMessage();
                new AddSellerRequest(createAddSellerRequest.getSeller());
                Server.getInstance().serverPrint("Request has created!");
            }
        }
    }

    public void createEditAuctionRequest(Message message) throws ClientException {
        loginCheck(message);
        if (message.getSender() == null) {
            throw new ClientException("invalid message!");
        } else {
            Account account = getAccount(message.getSender());
            if (!(account instanceof Seller)) {
                throw new ClientException("You are not allowed to do that");
            } else {
                var createEditAuctionRequest = message.getCreateEditAuctionRequestMessage();
                new EditAuctionRequest(createEditAuctionRequest.getAuction(), createEditAuctionRequest.getField(), createEditAuctionRequest.getValue());
                Server.getInstance().serverPrint("Request has created!");
            }
        }
    }

    public void createEditProductRequest(Message message) throws ClientException {
        loginCheck(message);
        if (message.getSender() == null) {
            throw new ClientException("invalid message!");
        } else {
            Account account = getAccount(message.getSender());
            if (!(account instanceof Seller)) {
                throw new ClientException("You are not allowed to do that");
            } else {
                var createEditProductRequest = message.getCreateEditProductRequestMessage();
                new EditProductRequest(createEditProductRequest.getProduct(),
                        createEditProductRequest.getSeller(),
                        createEditProductRequest.getField(),
                        createEditProductRequest.getValue());
                Server.getInstance().serverPrint("Request has created!");
            }
        }
    }

    public void createOffCode(Message message) throws ClientException {
        loginCheck(message);
        if (message.getSender() == null) {
            throw new ClientException("invalid message!");
        } else {
            Account account = getAccount(message.getSender());
            if (!(account instanceof Manager)) {
                throw new ClientException("You are not allowed to do that");
            } else {
                var offCode = message.getCreateOffCodeMessage().getOffCode();
                OffCode.addOffCode(offCode);
                Server.getInstance().serverPrint("Off Code has created!");
            }
        }
    }

    public void createBid(Message message) throws ClientException {
        loginCheck(message);
        if (message.getSender() == null) {
            throw new ClientException("invalid message!");
        } else {
            Account account = getAccount(message.getSender());
            if (!(account instanceof Seller)) {
                throw new ClientException("You are not allowed to do that");
            } else {
                new Bid(message.getCreateBidMessage().getSeller(), message.getCreateBidMessage().getProduct());
                Server.getInstance().serverPrint("Bid has created!");
            }
        }
    }

    public void createCategory(Message message) throws ClientException {
        loginCheck(message);
        if (message.getSender() == null) {
            throw new ClientException("invalid message!");
        } else {
            Account account = getAccount(message.getSender());
            if (!(account instanceof Manager)) {
                throw new ClientException("You are not allowed to do that");
            } else {
                var category = message.getCreateCategoryMessage().getCategory();
                if (category != null) {
                    if (category.getParentCategory() != null)
                        new ManagerController(account).addCategory(category.getName(), category.getParentCategory().getName(),
                                (ArrayList<Attributes>) category.getAttributes());
                    else
                        new ManagerController(account).addCategory(category.getName(), null,
                                (ArrayList<Attributes>) category.getAttributes());

                    Server.getInstance().serverPrint("Category has created!");
                }
            }
        }
    }


    public void payCart(Message message) throws ClientException {
        loginCheck(message);
        if (message.getSender() == null) {
            throw new ClientException("invalid message!");
        } else {
            Account account = getAccount(message.getSender());
            if (!(account instanceof Customer)) {
                throw new ClientException("You are not allowed to do that");
            } else {
                var customerController = new CustomerController(account);
                try {
                    customerController.purchase();
                    Server.getInstance().serverPrint("Paid !");
                } catch (InsufficientBalanceException e) {
                    Server.getInstance().serverPrint("Insufficient money");
                    e.printStackTrace();
                }
            }
        }
    }


    public void incrementProductQuantity(Message message) throws ClientException, InvalidProductIdException {
        loginCheck(message);
        if (message.getSender() == null) {
            throw new ClientException("invalid message!");
        } else {
            Account account = getAccount(message.getSender());
            if (!(account instanceof Customer)) {
                throw new ClientException("You are not allowed to do that");
            } else {
                var customerController = new CustomerController(account);
                int number = message.getIncrementProductQuantityMessage().getNumber();
                for (int i = 0; i < number; i++) {
                    customerController.increaseProduct(message.getIncrementProductQuantityMessage().getItemOfOrder().getProduct());
                }
                Server.getInstance().serverPrint("Quantity Incremented");
            }
        }
    }

    public void decrementProductQuantity(Message message) throws ClientException, InvalidProductIdException {
        loginCheck(message);
        if (message.getSender() == null) {
            throw new ClientException("invalid message!");
        } else {
            Account account = getAccount(message.getSender());
            if (!(account instanceof Customer)) {
                throw new ClientException("You are not allowed to do that");
            } else {
                var customerController = new CustomerController(account);
                int number = message.getDecrementProductQuantityMessage().getNumber();
                for (int i = 0; i < number; i++) {
                    customerController.decreaseProduct(message.getDecrementProductQuantityMessage().getItemOfOrder().getProduct());
                }
                Server.getInstance().serverPrint("Quantity Decremented");
            }
        }
    }

    public void applyOffCode(Message message) {

    }

    public void removeProductFromCart(Message message) {
        //TODO
    }

    public void createFileForSale(Message message) {
        //TODO
    }

    public void buyFile(Message message) {
        //TODO
    }

    public void setWage(Message message) {
        //TODO
    }

    public void takeFromWallet(Message message) {
        //TODO
    }


    public void chargeWallet(Message message) {
        //TODO
    }

    public void registerService(Message message) {
        //TODO
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
