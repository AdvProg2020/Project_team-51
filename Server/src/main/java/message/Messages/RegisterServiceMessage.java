package message.Messages;

import model.People.Seller;

public class RegisterServiceMessage {

    private Seller seller;

    public RegisterServiceMessage(Seller seller) {
        this.seller = seller;
    }

    public Seller getSeller() {
        return seller;
    }
}
