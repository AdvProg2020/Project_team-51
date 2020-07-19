package message.requests;

import model.People.Seller;
import model.Product;

public class CreateEditProductRequestMessage {

    private Product product;
    private Seller seller;
    private String field;
    private String value;

    public CreateEditProductRequestMessage(Product product, Seller seller, String field, String value) {
        this.product = product;
        this.seller = seller;
        this.field = field;
        this.value = value;
    }

    public Product getProduct() {
        return product;
    }

    public Seller getSeller() {
        return seller;
    }

    public String getField() {
        return field;
    }

    public String getValue() {
        return value;
    }
}
