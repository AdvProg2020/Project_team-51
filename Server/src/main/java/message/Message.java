package message;

import Server.JsonConverter;
import Server.Server;
import control.Controller;
import message.Messages.ClientToServer.*;
import message.Messages.ServerToClient.*;
import model.Category;
import model.People.Account;
import model.People.Service;
import model.Product;

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
        this.sender = Server.getInstance().serverName;
        this.receiver = receiver;
    }


    public static Message makeIsThereAnyManagerMessage(String receiver, boolean isThere) {
        Message message = new Message(receiver);
        message.isThereAnyManagerMessage = new IsThereAnyManagerMessage(isThere);
        message.messageType = MessageType.IS_THERE_ANY_MANAGER;
        return message;
    }

    public static Message makeDoneMessage(String receiver) {
        Message message = new Message(receiver);
        message.messageType = MessageType.DONE;
        return message;
    }

    public static Message makeAddP2PFileServerMessage(String receiver, Product product) {
        Message message = new Message(receiver);
        message.addP2PFileServerMessage = new AddP2PFileServerMessage(product);
        message.messageType = MessageType.P2P_SERVER;
        return message;
    }

    public static Message makeOnlineUsersMessage(String receiver, List<Account> accounts) {
        Message message = new Message(receiver);
        message.onlineUsersMessage = new OnlineUsersMessage(accounts);
        message.messageType = MessageType.ONLINE_USERS;
        return message;
    }

    public static Message makeOnlineServicesMessage(String receiver, List<Service> accounts) {
        Message message = new Message(receiver);
        message.onlineServicesMessage = new OnlineServicesMessage(accounts);
        message.messageType = MessageType.ONLINE_SERVICES;
        return message;
    }

    public static Message makeP2PReceiveMessage(String receiver, String encodedKey, int port) {
        Message message = new Message(receiver);
        message.p2PReceiveMessage = new P2PReceiveMessage(encodedKey, port);
        message.messageType = MessageType.P2P_RECEIVE;
        return message;
    }

    public static Message makeUpdateAccountMessage(String receiver, Account account) {
        Message message = new Message(receiver);
        message.updateAccountMessage = new UpdateAccountMessage(account);
        message.messageType = MessageType.UPDATE_ACCOUNT;
        return message;
    }

    public static Message makeDataMessage(String receiver) {
        Message message = new Message(receiver);
        message.dataMessage = new DataMessage(Category.getAllCategories(), Product.getAllProducts(),
                null, Controller.isThereAnyManager());
        message.messageType = MessageType.DATA;
        return message;
    }

    public static Message convertJsonToMessage(String messageJson) {
        return JsonConverter.fromJson(messageJson, Message.class);
    }

    public static Message makeExceptionMessage(String receiver, String exceptionString) {
        Message message = new Message(receiver);
        message.exceptionMessage = new ExceptionMessage(exceptionString);
        message.messageType = MessageType.SEND_EXCEPTION;
        return message;
    }

    public UpdateAccountMessage getUpdateAccountMessage() {
        return updateAccountMessage;
    }

    public String toJson() {
        return JsonConverter.toJson(this);
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
        this.token = token;
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

    public GetProductsListBySearchMessage getGetProductsListBySearchMessage() {
        return getProductsListBySearchMessage;
    }

    public GetProductsListByCategoryNameMessage getGetProductsListByCategoryNameMessage() {
        return getProductsListByCategoryNameMessage;
    }

    public P2PServerPortMessage getP2PServerPortMessage() {
        return p2PServerPortMessage;
    }

    public GetOnlineUsersMessage getGetOnlineUsersMessage() {
        return getOnlineUsersMessage;
    }

    public GetOnlineServiceMessage getGetOnlineServiceMessage() {
        return getOnlineServiceMessage;
    }
}
