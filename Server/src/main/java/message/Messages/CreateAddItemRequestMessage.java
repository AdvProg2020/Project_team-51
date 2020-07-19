package message.Messages;

import model.People.Seller;
import model.Product;

public class CreateAddItemRequestMessage {

    private Product product;
    private Seller seller;

    public CreateAddItemRequestMessage(Product product, Seller seller) {
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
