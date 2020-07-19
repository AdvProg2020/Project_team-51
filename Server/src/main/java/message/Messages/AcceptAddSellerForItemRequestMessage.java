package message.Messages;

import model.Requests.AddSellerForItemRequest;

public class AcceptAddSellerForItemRequestMessage {

    private AddSellerForItemRequest addSellerForItemRequest;

    public AcceptAddSellerForItemRequestMessage(AddSellerForItemRequest addSellerForItemRequest) {
        this.addSellerForItemRequest = addSellerForItemRequest;
    }

    public AddSellerForItemRequest getAddSellerForItemRequest() {
        return addSellerForItemRequest;
    }

}
