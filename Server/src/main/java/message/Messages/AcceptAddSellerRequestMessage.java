package message.Messages;

import model.Requests.AddSellerRequest;

public class AcceptAddSellerRequestMessage {

    private AddSellerRequest addSellerRequest;

    public AcceptAddSellerRequestMessage(AddSellerRequest addSellerRequest) {
        this.addSellerRequest = addSellerRequest;
    }

    public AddSellerRequest getAddSellerRequest() {
        return addSellerRequest;
    }
}
