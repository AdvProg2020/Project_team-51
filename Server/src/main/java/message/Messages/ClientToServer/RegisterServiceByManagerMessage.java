package message.Messages.ClientToServer;

import model.People.Seller;

public class RegisterServiceByManagerMessage {

    private Seller seller;

    public RegisterServiceByManagerMessage(Seller seller) {
        this.seller = seller;
    }

    public Seller getSeller() {
        return seller;
    }
}
