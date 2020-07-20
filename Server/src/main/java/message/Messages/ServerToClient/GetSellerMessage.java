package message.Messages.ServerToClient;

import model.People.Seller;

public class GetSellerMessage {
    private Seller seller;

    public GetSellerMessage(Seller seller) {
        this.seller = seller;
    }

    public Seller getSeller() {
        return seller;
    }
}
