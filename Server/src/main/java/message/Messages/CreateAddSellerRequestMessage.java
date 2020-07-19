package message.Messages;

import model.People.Seller;

public class CreateAddSellerRequestMessage {

    private Seller seller;

    public CreateAddSellerRequestMessage(Seller seller) {
        this.seller = seller;
    }

    public Seller getSeller() {
        return seller;
    }
}
