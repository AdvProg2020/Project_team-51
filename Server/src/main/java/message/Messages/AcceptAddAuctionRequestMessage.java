package message.Messages;

import model.Requests.AddAuctionRequest;

public class AcceptAddAuctionRequestMessage {

    private AddAuctionRequest addAuctionRequest;

    public AcceptAddAuctionRequestMessage(AddAuctionRequest addAuctionRequest) {
        this.addAuctionRequest = addAuctionRequest;
    }

    public AddAuctionRequest getAddAuctionRequest() {
        return addAuctionRequest;
    }
}
