package message.Messages.ClientToServer.ClientToServer;

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
