package message.requests;

import model.People.Seller;
import model.Product;

public class CreateAddSellerForItemRequestMessage {

    private Seller seller;
    private Product product;

    public CreateAddSellerForItemRequestMessage(Seller seller, Product product) {
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
