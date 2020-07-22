package message;

import control.Client;
import message.Messages.ClientToServer.*;
import message.Messages.ServerToClient.*;

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
    private AddToCartMessage addToCartMessage;

    // Server --> Clients
    private ExceptionMessage exceptionMessage;
    private GetAuctionMessage getAuctionMessage;
    private GetBidMessage getBidMessage;
    private GetBidListMessage getBidListMessage;
    private GetBuyerLogMessage getBuyerLogMessage;
    private GetCartMessage getCartMessage;
    private GetCategoryMessage getCategoryMessage;
    private GetChatMessage getChatMessage;
    private GetCustomerMessage getCustomerMessage;
    private GetItemOfOrderMessage getItemOfOrderMessage;
    private GetMangerMessage getMangerMessage;
    private GetOffCodeMessage getOffCodeMessage;
    private GetProductMessage getProductMessage;
    private GetProductsListMessage getProductsListMessage;
    private GetRateMessage getRateMessage;
    private GetSellerLogMessage getSellerLogMessage;
    private GetSellerMessage getSellerMessage;
    private GetServiceMessage getServiceMessage;
    private GetStatusMessage getStatusMessage;
    private GetTextMessageMessage getTextMessageMessage;
    private GetWalletMessage getWalletMessage;


    private Message(String receiver) {
        this.sender = Client.getInstance().getClientName();
        this.receiver = receiver;
        date = LocalDate.now();
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

    public GetAuctionMessage getGetAuctionMessage() {
        return getAuctionMessage;
    }

    public void setGetAuctionMessage(GetAuctionMessage getAuctionMessage) {
        this.getAuctionMessage = getAuctionMessage;
    }

    public GetBidMessage getGetBidMessage() {
        return getBidMessage;
    }

    public void setGetBidMessage(GetBidMessage getBidMessage) {
        this.getBidMessage = getBidMessage;
    }

    public GetBidListMessage getGetBidListMessage() {
        return getBidListMessage;
    }

    public void setGetBidListMessage(GetBidListMessage getBidListMessage) {
        this.getBidListMessage = getBidListMessage;
    }

    public GetBuyerLogMessage getGetBuyerLogMessage() {
        return getBuyerLogMessage;
    }

    public void setGetBuyerLogMessage(GetBuyerLogMessage getBuyerLogMessage) {
        this.getBuyerLogMessage = getBuyerLogMessage;
    }

    public GetCartMessage getGetCartMessage() {
        return getCartMessage;
    }

    public void setGetCartMessage(GetCartMessage getCartMessage) {
        this.getCartMessage = getCartMessage;
    }

    public GetCategoryMessage getGetCategoryMessage() {
        return getCategoryMessage;
    }

    public void setGetCategoryMessage(GetCategoryMessage getCategoryMessage) {
        this.getCategoryMessage = getCategoryMessage;
    }

    public GetChatMessage getGetChatMessage() {
        return getChatMessage;
    }

    public void setGetChatMessage(GetChatMessage getChatMessage) {
        this.getChatMessage = getChatMessage;
    }

    public GetCustomerMessage getGetCustomerMessage() {
        return getCustomerMessage;
    }

    public void setGetCustomerMessage(GetCustomerMessage getCustomerMessage) {
        this.getCustomerMessage = getCustomerMessage;
    }

    public GetItemOfOrderMessage getGetItemOfOrderMessage() {
        return getItemOfOrderMessage;
    }

    public void setGetItemOfOrderMessage(GetItemOfOrderMessage getItemOfOrderMessage) {
        this.getItemOfOrderMessage = getItemOfOrderMessage;
    }

    public GetMangerMessage getGetMangerMessage() {
        return getMangerMessage;
    }

    public void setGetMangerMessage(GetMangerMessage getMangerMessage) {
        this.getMangerMessage = getMangerMessage;
    }

    public GetOffCodeMessage getGetOffCodeMessage() {
        return getOffCodeMessage;
    }

    public void setGetOffCodeMessage(GetOffCodeMessage getOffCodeMessage) {
        this.getOffCodeMessage = getOffCodeMessage;
    }

    public GetProductMessage getGetProductMessage() {
        return getProductMessage;
    }

    public void setGetProductMessage(GetProductMessage getProductMessage) {
        this.getProductMessage = getProductMessage;
    }

    public GetProductsListMessage getGetProductsListMessage() {
        return getProductsListMessage;
    }

    public void setGetProductsListMessage(GetProductsListMessage getProductsListMessage) {
        this.getProductsListMessage = getProductsListMessage;
    }

    public GetRateMessage getGetRateMessage() {
        return getRateMessage;
    }

    public void setGetRateMessage(GetRateMessage getRateMessage) {
        this.getRateMessage = getRateMessage;
    }

    public GetSellerLogMessage getGetSellerLogMessage() {
        return getSellerLogMessage;
    }

    public void setGetSellerLogMessage(GetSellerLogMessage getSellerLogMessage) {
        this.getSellerLogMessage = getSellerLogMessage;
    }

    public GetSellerMessage getGetSellerMessage() {
        return getSellerMessage;
    }

    public void setGetSellerMessage(GetSellerMessage getSellerMessage) {
        this.getSellerMessage = getSellerMessage;
    }

    public GetServiceMessage getGetServiceMessage() {
        return getServiceMessage;
    }

    public void setGetServiceMessage(GetServiceMessage getServiceMessage) {
        this.getServiceMessage = getServiceMessage;
    }

    public GetStatusMessage getGetStatusMessage() {
        return getStatusMessage;
    }

    public void setGetStatusMessage(GetStatusMessage getStatusMessage) {
        this.getStatusMessage = getStatusMessage;
    }

    public GetTextMessageMessage getGetTextMessageMessage() {
        return getTextMessageMessage;
    }

    public void setGetTextMessageMessage(GetTextMessageMessage getTextMessageMessage) {
        this.getTextMessageMessage = getTextMessageMessage;
    }

    public GetWalletMessage getGetWalletMessage() {
        return getWalletMessage;
    }

    public void setGetWalletMessage(GetWalletMessage getWalletMessage) {
        this.getWalletMessage = getWalletMessage;
    }
}
