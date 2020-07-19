package message.Messages;

import model.Requests.EditAuctionRequest;

public class AcceptEditAuctionRequestMessage {

    private EditAuctionRequest editAuctionRequest;

    public AcceptEditAuctionRequestMessage(EditAuctionRequest editAuctionRequest) {
        this.editAuctionRequest = editAuctionRequest;
    }

    public EditAuctionRequest getEditAuctionRequest() {
        return editAuctionRequest;
    }

}
