package message.Messages.ClientToServer;

import model.People.Seller;
import model.Product;

public class CreateBidMessage {
    //TODO ( have no idea currently )

    private Seller seller;
    private Product product;

    public CreateBidMessage(Seller seller, Product product) {
        this.seller = seller;
        this.product = product;
    }


    public Seller getSeller() {
        return seller;
    }

    public Product getProduct() {
        return product;
    }
}
