package message.requests;

import model.People.Seller;

public class RegisterServiceManager {

    private Seller seller;

    public RegisterServiceManager(Seller seller) {
        this.seller = seller;
    }

    public Seller getSeller() {
        return seller;
    }
}
