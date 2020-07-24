package message.Messages.ClientToServer.ClientToServer;

import model.People.Seller;
import model.Product;

public class AddToCartMessage {

    private Product product;
    private Seller seller;

    public AddToCartMessage(Product product, Seller seller) {
        this.product = product;
        this.seller = seller;
    }

    public Product getProduct() {
        return product;
    }

    public Seller getSeller() {
        return seller;
    }
}
