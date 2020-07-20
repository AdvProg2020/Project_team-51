package message;

import Server.JsonConverter;
import Server.Server;
import message.Messages.*;

import java.time.LocalDate;

public class Message {

    private MessageType messageType;

    // serverName || clientName
    private String sender;
    private String receiver;
    // In order to validate !!
    private LocalDate date;
    private String Token;

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

    // Server --> Clients
    //      TODO (NEED TO FIGURE OUT THE REQUESTS)


    private Message(String receiver) {
        this.sender = Server.getInstance().serverName;
        this.receiver = receiver;
    }


    public static Message convertJsonToMessage(String messageJson) {
        return JsonConverter.fromJson(messageJson, Message.class);
    }


    public String toJson() {
        return JsonConverter.toJson(this);
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public ResponseToClientMessage getResponseToClientMessage() {
        return responseToClientMessage;
    }

    public void setResponseToClientMessage(ResponseToClientMessage responseToClientMessage) {
        this.responseToClientMessage = responseToClientMessage;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
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
}
