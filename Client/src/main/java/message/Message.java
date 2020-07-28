package message;

import control.Client;
import control.Controller;
import message.Messages.ClientToServer.*;
import message.Messages.ServerToClient.*;
import model.*;
import model.People.*;
import model.Requests.*;

import java.time.LocalDateTime;
import java.util.List;

public class Message {

    private MessageType messageType;

    // serverName || clientName
    private String sender;
    private String receiver;
    // In order to validate !!
    private LocalDateTime date;
    private String token;

    // Clients --> Server
    private AcceptAddAuctionRequestMessage acceptAddAuctionRequestMessage;
    private AcceptAddCommentRequestMessage acceptAddCommentRequestMessage;
    private AcceptAddItemRequestMessage acceptAddItemRequestMessage;
    private AcceptAddSellerForItemRequestMessage acceptAddSellerForItemRequestMessage;
    private AcceptAddSellerRequestMessage acceptAddSellerRequestMessage;
    private AcceptEditAuctionRequestMessage acceptEditAuctionRequestMessage;
    private AcceptEditProductRequestMessage acceptEditProductRequestMessage;
    private ApplyOffCodeMessage applyOffCodeMessage;
    private CreateOffCodeMessage createOffCodeMessage;
    private ChargeWalletMessage chargeWalletMessage;
    private TakeFromWalletMessage takeFromWalletMessage;
    private CreateAddAuctionRequestMessage createAddAuctionRequestMessage;
    private CreateAddCommentRequestMessage createAddCommentRequestMessage;
    private CreateAddItemRequestMessage createAddItemRequestMessage;
    private CreateAddSellerForItemRequestMessage createAddSellerForItemRequestMessage;
    private CreateAddSellerRequestMessage createAddSellerRequestMessage;
    private CreateEditAuctionRequestMessage createEditAuctionRequestMessage;
    private CreateEditProductRequestMessage createEditProductRequestMessage;
    private CreateBidMessage createBidMessage;
    private CreateCategoryMessage createCategoryMessage;
    private CreateFileForSaleMessage createFileForSaleMessage;
    private BuyFileMessage buyFileMessage;
    private DecrementProductQuantityMessage decrementProductQuantityMessage;
    private IncrementProductQuantityMessage incrementProductQuantityMessage;
    private RemoveProductFromCartMessage removeProductFromCartMessage;
    private PayCartViaWalletMessage payCartViaWalletMessage;
    private PayCartViaBankMessage payCartViaBankMessage;
    private LoginMessage loginMessage;
    private LogoutMessage logoutMessage;
    private RegisterCustomerMessage registerCustomerMessage;
    private RegisterManagerMessage registerManagerMessage;
    private RegisterServiceByManagerMessage registerServiceByManagerMessage;
    private RegisterManagerByManagerMessage registerManagerByManagerMessage;
    private SetWageMessage setWageMessage;
    private ResponseToClientMessage responseToClientMessage;
    private AddToCartMessage addToCartMessage;
    private IsThereAnyManagerMessage isThereAnyManagerMessage;
    private GiveMeTheDataMessage giveMeTheDataMessage;
    private GetProductsListByCategoryNameMessage getProductsListByCategoryNameMessage;
    private GetProductsListBySearchMessage getProductsListBySearchMessage;
    private P2PServerPortMessage p2PServerPortMessage;
    private GetOnlineUsersMessage getOnlineUsersMessage;
    private GetOnlineServiceMessage getOnlineServiceMessage;

    // Server --> Clients
    private ExceptionMessage exceptionMessage;
    private DataMessage dataMessage;
    private UpdateAccountMessage updateAccountMessage;
    private UpdateCartMessage updateCartMessage;
    private UpdateCategoriesMessage updateCategoriesMessage;
    private UpdateProductsListMessage updateProductsListMessage;
    private UpdateRequestsMessage updateRequestsMessage;
    private AddP2PFileServerMessage addP2PFileServerMessage;
    private P2PReceiveMessage p2PReceiveMessage;
    private OnlineUsersMessage onlineUsersMessage;
    private OnlineServicesMessage onlineServicesMessage;


    public Message(String receiver) {
        this.sender = Client.getInstance().getClientName();
        this.receiver = receiver;
        date = LocalDateTime.now();
        System.out.println(date);
        token = Client.getInstance().getAuthToken();
    }


    public static Message convertJsonToMessage(String messageJson) {
        return JsonConverter.fromJson(messageJson, Message.class);
    }

    public static Message makeIsThereAnyManagerMessage(String receiver) {
        Message message = new Message(receiver);
        message.isThereAnyManagerMessage = new IsThereAnyManagerMessage();
        message.messageType = MessageType.IS_THERE_ANY_MANAGER;
        return message;
    }

    public static Message makeP2PServerPortMessage(String receiver, Product product, int port) {
        Message message = new Message(receiver);
        message.p2PServerPortMessage = new P2PServerPortMessage(product, port);
        message.messageType = MessageType.P2P_SERVER;
        return message;
    }

    public static Message makeGetOnlineUsersMessage(String receiver) {
        Message message = new Message(receiver);
        message.getOnlineUsersMessage = new GetOnlineUsersMessage();
        message.messageType = MessageType.GET_ONLINE_USERS;
        return message;
    }

    public static Message makeGetProductsListByCategoryNameMessage(String receiver, String categoryName) {
        Message message = new Message(receiver);
        message.getProductsListByCategoryNameMessage = new GetProductsListByCategoryNameMessage(categoryName);
        message.messageType = MessageType.IS_THERE_ANY_MANAGER;
        return message;
    }

    public static Message makeGiveMeTheDataMessage(String receiver) {
        Message message = new Message(receiver);
        message.giveMeTheDataMessage = new GiveMeTheDataMessage();
        message.messageType = MessageType.GIVE_DATA;
        return message;
    }

    public static Message makeRegisterCustomerMessage(String receiver, Customer customer) {
        Message message = new Message(receiver);
        message.registerCustomerMessage = new RegisterCustomerMessage(customer);
        message.messageType = MessageType.REGISTER_CUSTOMER;
        return message;
    }

    public static Message makeRegisterManagerMessage(String receiver, Manager manager) {
        Message message = new Message(receiver);
        message.registerManagerMessage = new RegisterManagerMessage(manager);
        message.messageType = MessageType.REGISTER_MANAGER;
        return message;
    }

    public static Message makeRegisterManagerByManagerMessage(String receiver, Manager manager) {
        Message message = new Message(receiver);
        message.registerManagerByManagerMessage = new RegisterManagerByManagerMessage(manager);
        message.messageType = MessageType.REGISTER_MANAGER_BY_MANAGER;
        return message;
    }

    public static Message makeRegisterServiceByManagerMessage(String receiver, Service service) {
        Message message = new Message(receiver);
        message.registerServiceByManagerMessage = new RegisterServiceByManagerMessage(service);
        message.messageType = MessageType.REGISTER_SERVICE_BY_MANAGER;
        return message;
    }

    public static Message makeRemoveProductFromCartMessage(String receiver, Product product) {
        Message message = new Message(receiver);
        message.removeProductFromCartMessage = new RemoveProductFromCartMessage(product);
        message.messageType = MessageType.REMOVE_PRODUCT_FROM_CART;
        return message;
    }

    public static Message makeSetWageMessage(String receiver, int wage) {
        Message message = new Message(receiver);
        message.setWageMessage = new SetWageMessage(wage);
        message.messageType = MessageType.SET_WAGE;
        return message;
    }

    public static Message makeLoginMessage(String receiver, String username, String password) {
        Message message = new Message(receiver);
        message.loginMessage = new LoginMessage(username, password);
        message.messageType = MessageType.LOGIN;
        return message;
    }

    public static Message makeLogoutMessage(String receiver) {
        Message message = new Message(receiver);
        message.logoutMessage = new LogoutMessage(Controller.getCurrentAccount());
        message.messageType = MessageType.LOGOUT;
        return message;
    }

    public static Message makeAddToCartMessage(String receiver, Product product, Seller seller) {
        Message message = new Message(receiver);
        message.addToCartMessage = new AddToCartMessage(product, seller);
        message.messageType = MessageType.ADD_TO_CART;
        return message;
    }

    public static Message makeUpdateAccountMessage(String receiver, Account pervAccount) {
        Message message = new Message(receiver);
        message.updateAccountMessage = new UpdateAccountMessage(pervAccount);
        message.messageType = MessageType.UPDATE_ACCOUNT;
        return message;
    }

    public static Message makeGetOnlineServicesMessage(String receiver) {
        Message message = new Message(receiver);
        message.getOnlineServiceMessage = new GetOnlineServiceMessage();
        message.messageType = MessageType.GET_ONLINE_SERVICES;
        return message;
    }

    public static Message makeAcceptAddAuctionRequestMessage(String receiver, AddAuctionRequest addAuctionRequest) {
        Message message = new Message(receiver);
        message.acceptAddAuctionRequestMessage = new AcceptAddAuctionRequestMessage(addAuctionRequest);
        message.messageType = MessageType.ACCEPT_ADD_AUCTION_REQUEST;
        return message;
    }

    public static Message makeAcceptAddCommentRequestMessage(String receiver, AddCommentRequest addCommentRequest) {
        Message message = new Message(receiver);
        message.acceptAddCommentRequestMessage = new AcceptAddCommentRequestMessage(addCommentRequest);
        message.messageType = MessageType.ACCEPT_ADD_COMMENT_REQUEST;
        return message;
    }

    public static Message makeAcceptAddItemRequestMessage(String receiver, AddItemRequest addItemRequest) {
        Message message = new Message(receiver);
        message.acceptAddItemRequestMessage = new AcceptAddItemRequestMessage(addItemRequest);
        message.messageType = MessageType.ACCEPT_ADD_ITEM_REQUEST;
        return message;
    }

    public static Message makeAcceptAddSellerForItemRequestMessage(String receiver, AddSellerForItemRequest addSellerForItemRequest) {
        Message message = new Message(receiver);
        message.acceptAddSellerForItemRequestMessage = new AcceptAddSellerForItemRequestMessage(addSellerForItemRequest);
        message.messageType = MessageType.ACCEPT_ADD_SELLER_FOR_ITEM_REQUEST;
        return message;
    }

    public static Message makeAcceptAddSellerRequestMessage(String receiver, AddSellerRequest addSellerRequest) {
        Message message = new Message(receiver);
        message.acceptAddSellerRequestMessage = new AcceptAddSellerRequestMessage(addSellerRequest);
        message.messageType = MessageType.ACCEPT_ADD_SELLER_REQUEST;
        return message;
    }

    public static Message makeAcceptEditAuctionRequestMessage(String receiver, EditAuctionRequest editAuctionRequest) {
        Message message = new Message(receiver);
        message.acceptEditAuctionRequestMessage = new AcceptEditAuctionRequestMessage(editAuctionRequest);
        message.messageType = MessageType.ACCEPT_EDIT_AUCTION_REQUEST;
        return message;
    }

    public static Message makeAcceptEditProductRequestMessage(String receiver, EditProductRequest editProductRequest) {
        Message message = new Message(receiver);
        message.acceptEditProductRequestMessage = new AcceptEditProductRequestMessage(editProductRequest);
        message.messageType = MessageType.ACCEPT_EDIT_PRODUCT_REQUEST;
        return message;
    }

    public static Message makeCreateAddAuctionRequestMessage(String receiver, Auction auction, Seller seller) {
        Message message = new Message(receiver);
        message.createAddAuctionRequestMessage = new CreateAddAuctionRequestMessage(auction, seller);
        message.messageType = MessageType.CREATE_ADD_AUCTION_REQUEST;
        return message;
    }

    public static Message makeCreateAddCommentRequestMessage(String receiver, Comment comment) {
        Message message = new Message(receiver);
        message.createAddCommentRequestMessage = new CreateAddCommentRequestMessage(comment);
        message.messageType = MessageType.CREATE_ADD_COMMENT_REQUEST;
        return message;
    }

    public static Message makeCreateAddItemRequestMessage(String receiver, Product product, Seller seller) {
        Message message = new Message(receiver);
        message.createAddItemRequestMessage = new CreateAddItemRequestMessage(product, seller);
        message.messageType = MessageType.CREATE_ADD_ITEM_REQUEST;
        return message;
    }

    public static Message makeCreateAddSellerForItemRequestMessage(String receiver, Product product, Seller seller, int quantity, double price) {
        Message message = new Message(receiver);
        message.createAddSellerForItemRequestMessage = new CreateAddSellerForItemRequestMessage(product, seller, quantity, price);
        message.messageType = MessageType.CREATE_ADD_SELLER_FOR_ITEM_REQUEST;
        return message;
    }

    public static Message makeCreateAddSellerRequestMessage(String receiver, Seller seller) {
        Message message = new Message(receiver);
        message.createAddSellerRequestMessage = new CreateAddSellerRequestMessage(seller);
        message.messageType = MessageType.CREATE_ADD_SELLER_REQUEST;
        return message;
    }

    public static Message makeCreateEditAuctionRequestMessage(String receiver, Auction auction, String field, String value) {
        Message message = new Message(receiver);
        message.createEditAuctionRequestMessage = new CreateEditAuctionRequestMessage(auction, field, value);
        message.messageType = MessageType.CREATE_EDIT_AUCTION_REQUEST;
        return message;
    }

    public static Message makeCreateEditProductRequestMessage(String receiver, Product product, Seller seller, String field, String value) {
        Message message = new Message(receiver);
        message.createEditProductRequestMessage = new CreateEditProductRequestMessage(product, seller, field, value);
        message.messageType = MessageType.CREATE_EDIT_PRODUCT_REQUEST;
        return message;
    }

    public static Message makeApplyOffCodeMessage(String receiver, OffCode offCode, List<ItemOfOrder> cart) {
        Message message = new Message(receiver);
        message.applyOffCodeMessage = new ApplyOffCodeMessage(offCode, cart);
        message.messageType = MessageType.APPLY_OFF_CODE;
        return message;
    }

    public static Message makeCreateBidMessage(String receiver, Seller seller, Product product) {
        Message message = new Message(receiver);
        message.createBidMessage = new CreateBidMessage(seller, product);
        message.messageType = MessageType.CREATE_BID;
        return message;
    }

    public static Message makeCreateCategoryMessage(String receiver, Category category) {
        Message message = new Message(receiver);
        message.createCategoryMessage = new CreateCategoryMessage(category);
        message.messageType = MessageType.CREATE_CATEGORY;
        return message;
    }

    public static Message makeCreateOffCodeMessage(String receiver, OffCode offCode) {
        Message message = new Message(receiver);
        message.createOffCodeMessage = new CreateOffCodeMessage(offCode);
        message.messageType = MessageType.CREATE_OFF_CODE;
        return message;
    }

    public static Message makeCreateFileForSaleMessage(String receiver) {
        Message message = new Message(receiver);
        message.createFileForSaleMessage = new CreateFileForSaleMessage(); //TODO
        message.messageType = MessageType.CREATE_FILE_FOR_SALE;
        return message;
    }

    public static Message makeIncrementProductQuantityMessage(String receiver, ItemOfOrder itemOfOrder, int number) {
        Message message = new Message(receiver);
        message.incrementProductQuantityMessage = new IncrementProductQuantityMessage(itemOfOrder, number);
        message.messageType = MessageType.INCREMENT_PRODUCT_QUANTITY;
        return message;
    }

    public static Message makeDecrementProductQuantityMessage(String receiver, ItemOfOrder itemOfOrder, int number) {
        Message message = new Message(receiver);
        message.decrementProductQuantityMessage = new DecrementProductQuantityMessage(itemOfOrder, number);
        message.messageType = MessageType.DECREMENT_PRODUCT_QUANTITY;
        return message;
    }

    public static Message makePayCartViaWalletMessage(String receiver, List<ItemOfOrder> cart) {
        Message message = new Message(receiver);
        message.payCartViaWalletMessage = new PayCartViaWalletMessage(cart);
        message.messageType = MessageType.PAY_CART_VIA_WALLET;
        return message;
    }

    public static Message makePayCartViaBankMessage(String receiver, List<ItemOfOrder> cart) {
        Message message = new Message(receiver);
        message.payCartViaBankMessage = new PayCartViaBankMessage(cart);
        message.messageType = MessageType.PAY_CART_VIA_BANK;
        return message;
    }

    public static Message makeBuyFileMessage(String receiver, double price) {
        Message message = new Message(receiver);
        message.buyFileMessage = new BuyFileMessage(price); //TODO
        message.messageType = MessageType.BUY_FILE;
        return message;
    }

    public static Message makeChargeWalletMessage(String receiver) {
        Message message = new Message(receiver);
        message.chargeWalletMessage = new ChargeWalletMessage();//TODO
        message.messageType = MessageType.BUY_FILE;
        return message;
    }


    public static Message makeExceptionMessage(String receiver, String exceptionString) {
        Message message = new Message(receiver);
        message.exceptionMessage = new ExceptionMessage(exceptionString);
        message.messageType = MessageType.SEND_EXCEPTION;
        return message;
    }


    public String toJson() {
        return JsonConverter.toJson(this);
    }

    public IsThereAnyManagerMessage getIsThereAnyManagerMessage() {
        return isThereAnyManagerMessage;
    }

    public void setIsThereAnyManagerMessage(IsThereAnyManagerMessage isThereAnyManagerMessage) {
        this.isThereAnyManagerMessage = isThereAnyManagerMessage;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public ResponseToClientMessage getResponseToClientMessage() {
        return responseToClientMessage;
    }

    public void setResponseToClientMessage(ResponseToClientMessage responseToClientMessage) {
        this.responseToClientMessage = responseToClientMessage;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        token = token;
    }

    public AcceptAddAuctionRequestMessage getAcceptAddAuctionRequestMessage() {
        return acceptAddAuctionRequestMessage;
    }

    public void setAcceptAddAuctionRequestMessage(AcceptAddAuctionRequestMessage acceptAddAuctionRequestMessage) {
        this.acceptAddAuctionRequestMessage = acceptAddAuctionRequestMessage;
    }

    public AcceptAddCommentRequestMessage getAcceptAddCommentRequestMessage() {
        return acceptAddCommentRequestMessage;
    }

    public void setAcceptAddCommentRequestMessage(AcceptAddCommentRequestMessage acceptAddCommentRequestMessage) {
        this.acceptAddCommentRequestMessage = acceptAddCommentRequestMessage;
    }

    public AcceptAddItemRequestMessage getAcceptAddItemRequestMessage() {
        return acceptAddItemRequestMessage;
    }

    public void setAcceptAddItemRequestMessage(AcceptAddItemRequestMessage acceptAddItemRequestMessage) {
        this.acceptAddItemRequestMessage = acceptAddItemRequestMessage;
    }

    public AcceptAddSellerForItemRequestMessage getAcceptAddSellerForItemRequestMessage() {
        return acceptAddSellerForItemRequestMessage;
    }

    public void setAcceptAddSellerForItemRequestMessage(AcceptAddSellerForItemRequestMessage acceptAddSellerForItemRequestMessage) {
        this.acceptAddSellerForItemRequestMessage = acceptAddSellerForItemRequestMessage;
    }

    public AcceptAddSellerRequestMessage getAcceptAddSellerRequestMessage() {
        return acceptAddSellerRequestMessage;
    }

    public void setAcceptAddSellerRequestMessage(AcceptAddSellerRequestMessage acceptAddSellerRequestMessage) {
        this.acceptAddSellerRequestMessage = acceptAddSellerRequestMessage;
    }

    public AcceptEditAuctionRequestMessage getAcceptEditAuctionRequestMessage() {
        return acceptEditAuctionRequestMessage;
    }

    public void setAcceptEditAuctionRequestMessage(AcceptEditAuctionRequestMessage acceptEditAuctionRequestMessage) {
        this.acceptEditAuctionRequestMessage = acceptEditAuctionRequestMessage;
    }

    public AcceptEditProductRequestMessage getAcceptEditProductRequestMessage() {
        return acceptEditProductRequestMessage;
    }

    public void setAcceptEditProductRequestMessage(AcceptEditProductRequestMessage acceptEditProductRequestMessage) {
        this.acceptEditProductRequestMessage = acceptEditProductRequestMessage;
    }

    public ApplyOffCodeMessage getApplyOffCodeMessage() {
        return applyOffCodeMessage;
    }

    public void setApplyOffCodeMessage(ApplyOffCodeMessage applyOffCodeMessage) {
        this.applyOffCodeMessage = applyOffCodeMessage;
    }

    public CreateOffCodeMessage getCreateOffCodeMessage() {
        return createOffCodeMessage;
    }

    public void setCreateOffCodeMessage(CreateOffCodeMessage createOffCodeMessage) {
        this.createOffCodeMessage = createOffCodeMessage;
    }

    public ChargeWalletMessage getChargeWalletMessage() {
        return chargeWalletMessage;
    }

    public void setChargeWalletMessage(ChargeWalletMessage chargeWalletMessage) {
        this.chargeWalletMessage = chargeWalletMessage;
    }

    public TakeFromWalletMessage getTakeFromWalletMessage() {
        return takeFromWalletMessage;
    }

    public void setTakeFromWalletMessage(TakeFromWalletMessage takeFromWalletMessage) {
        this.takeFromWalletMessage = takeFromWalletMessage;
    }

    public CreateAddAuctionRequestMessage getCreateAddAuctionRequestMessage() {
        return createAddAuctionRequestMessage;
    }

    public void setCreateAddAuctionRequestMessage(CreateAddAuctionRequestMessage createAddAuctionRequestMessage) {
        this.createAddAuctionRequestMessage = createAddAuctionRequestMessage;
    }

    public CreateAddCommentRequestMessage getCreateAddCommentRequestMessage() {
        return createAddCommentRequestMessage;
    }

    public void setCreateAddCommentRequestMessage(CreateAddCommentRequestMessage createAddCommentRequestMessage) {
        this.createAddCommentRequestMessage = createAddCommentRequestMessage;
    }

    public CreateAddItemRequestMessage getCreateAddItemRequestMessage() {
        return createAddItemRequestMessage;
    }

    public void setCreateAddItemRequestMessage(CreateAddItemRequestMessage createAddItemRequestMessage) {
        this.createAddItemRequestMessage = createAddItemRequestMessage;
    }

    public CreateAddSellerForItemRequestMessage getCreateAddSellerForItemRequestMessage() {
        return createAddSellerForItemRequestMessage;
    }

    public void setCreateAddSellerForItemRequestMessage(CreateAddSellerForItemRequestMessage createAddSellerForItemRequestMessage) {
        this.createAddSellerForItemRequestMessage = createAddSellerForItemRequestMessage;
    }

    public CreateAddSellerRequestMessage getCreateAddSellerRequestMessage() {
        return createAddSellerRequestMessage;
    }

    public void setCreateAddSellerRequestMessage(CreateAddSellerRequestMessage createAddSellerRequestMessage) {
        this.createAddSellerRequestMessage = createAddSellerRequestMessage;
    }

    public CreateEditAuctionRequestMessage getCreateEditAuctionRequestMessage() {
        return createEditAuctionRequestMessage;
    }

    public void setCreateEditAuctionRequestMessage(CreateEditAuctionRequestMessage createEditAuctionRequestMessage) {
        this.createEditAuctionRequestMessage = createEditAuctionRequestMessage;
    }

    public CreateEditProductRequestMessage getCreateEditProductRequestMessage() {
        return createEditProductRequestMessage;
    }

    public void setCreateEditProductRequestMessage(CreateEditProductRequestMessage createEditProductRequestMessage) {
        this.createEditProductRequestMessage = createEditProductRequestMessage;
    }

    public CreateBidMessage getCreateBidMessage() {
        return createBidMessage;
    }

    public void setCreateBidMessage(CreateBidMessage createBidMessage) {
        this.createBidMessage = createBidMessage;
    }

    public CreateCategoryMessage getCreateCategoryMessage() {
        return createCategoryMessage;
    }

    public void setCreateCategoryMessage(CreateCategoryMessage createCategoryMessage) {
        this.createCategoryMessage = createCategoryMessage;
    }

    public CreateFileForSaleMessage getCreateFileForSaleMessage() {
        return createFileForSaleMessage;
    }

    public void setCreateFileForSaleMessage(CreateFileForSaleMessage createFileForSaleMessage) {
        this.createFileForSaleMessage = createFileForSaleMessage;
    }

    public BuyFileMessage getBuyFileMessage() {
        return buyFileMessage;
    }

    public void setBuyFileMessage(BuyFileMessage buyFileMessage) {
        this.buyFileMessage = buyFileMessage;
    }

    public DecrementProductQuantityMessage getDecrementProductQuantityMessage() {
        return decrementProductQuantityMessage;
    }

    public void setDecrementProductQuantityMessage(DecrementProductQuantityMessage decrementProductQuantityMessage) {
        this.decrementProductQuantityMessage = decrementProductQuantityMessage;
    }

    public IncrementProductQuantityMessage getIncrementProductQuantityMessage() {
        return incrementProductQuantityMessage;
    }

    public void setIncrementProductQuantityMessage(IncrementProductQuantityMessage incrementProductQuantityMessage) {
        this.incrementProductQuantityMessage = incrementProductQuantityMessage;
    }

    public RemoveProductFromCartMessage getRemoveProductFromCartMessage() {
        return removeProductFromCartMessage;
    }

    public void setRemoveProductFromCartMessage(RemoveProductFromCartMessage removeProductFromCartMessage) {
        this.removeProductFromCartMessage = removeProductFromCartMessage;
    }

    public PayCartViaWalletMessage getPayCartViaWalletMessage() {
        return payCartViaWalletMessage;
    }

    public void setPayCartViaWalletMessage(PayCartViaWalletMessage payCartViaWalletMessage) {
        this.payCartViaWalletMessage = payCartViaWalletMessage;
    }

    public PayCartViaBankMessage getPayCartViaBankMessage() {
        return payCartViaBankMessage;
    }

    public void setPayCartViaBankMessage(PayCartViaBankMessage payCartViaBankMessage) {
        this.payCartViaBankMessage = payCartViaBankMessage;
    }

    public LoginMessage getLoginMessage() {
        return loginMessage;
    }

    public void setLoginMessage(LoginMessage loginMessage) {
        this.loginMessage = loginMessage;
    }

    public LogoutMessage getLogoutMessage() {
        return logoutMessage;
    }

    public void setLogoutMessage(LogoutMessage logoutMessage) {
        this.logoutMessage = logoutMessage;
    }

    public RegisterCustomerMessage getRegisterCustomerMessage() {
        return registerCustomerMessage;
    }

    public void setRegisterCustomerMessage(RegisterCustomerMessage registerCustomerMessage) {
        this.registerCustomerMessage = registerCustomerMessage;
    }

    public RegisterManagerMessage getRegisterManagerMessage() {
        return registerManagerMessage;
    }

    public void setRegisterManagerMessage(RegisterManagerMessage registerManagerMessage) {
        this.registerManagerMessage = registerManagerMessage;
    }

    public RegisterServiceByManagerMessage getRegisterServiceByManagerMessage() {
        return registerServiceByManagerMessage;
    }

    public void setRegisterServiceByManagerMessage(RegisterServiceByManagerMessage registerServiceByManagerMessage) {
        this.registerServiceByManagerMessage = registerServiceByManagerMessage;
    }

    public RegisterManagerByManagerMessage getRegisterManagerByManagerMessage() {
        return registerManagerByManagerMessage;
    }

    public void setRegisterManagerByManagerMessage(RegisterManagerByManagerMessage registerManagerByManagerMessage) {
        this.registerManagerByManagerMessage = registerManagerByManagerMessage;
    }

    public SetWageMessage getSetWageMessage() {
        return setWageMessage;
    }

    public void setSetWageMessage(SetWageMessage setWageMessage) {
        this.setWageMessage = setWageMessage;
    }

    public AddToCartMessage getAddToCartMessage() {
        return addToCartMessage;
    }

    public void setAddToCartMessage(AddToCartMessage addToCartMessage) {
        this.addToCartMessage = addToCartMessage;
    }


    // Server --> Client GETTERS & SETTERS


    public ExceptionMessage getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(ExceptionMessage exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public GiveMeTheDataMessage getGiveMeTheDataMessage() {
        return giveMeTheDataMessage;
    }

    public DataMessage getDataMessage() {
        return dataMessage;
    }

    public UpdateAccountMessage getUpdateAccountMessage() {
        return updateAccountMessage;
    }

    public UpdateRequestsMessage getUpdateRequestsMessage() {
        return updateRequestsMessage;
    }

    public UpdateProductsListMessage getUpdateProductsListMessage() {
        return updateProductsListMessage;
    }

    public UpdateCategoriesMessage getUpdateCategoriesMessage() {
        return updateCategoriesMessage;
    }

    public UpdateCartMessage getUpdateCartMessage() {
        return updateCartMessage;
    }

    public AddP2PFileServerMessage getAddP2PFileServerMessage() {
        return addP2PFileServerMessage;
    }

    public P2PReceiveMessage getP2PReceiveMessage() {
        return p2PReceiveMessage;
    }

    public void setP2PReceiveMessage(P2PReceiveMessage p2PReceiveMessage) {
        this.p2PReceiveMessage = p2PReceiveMessage;
    }

    public OnlineUsersMessage getOnlineUsersMessage() {
        return onlineUsersMessage;
    }

    public OnlineServicesMessage getOnlineServicesMessage() {
        return onlineServicesMessage;
    }
}
