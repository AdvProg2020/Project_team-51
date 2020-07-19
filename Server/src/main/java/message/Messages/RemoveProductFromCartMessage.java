package message.Messages;

import model.Product;

public class RemoveProductFromCartMessage {

    private Product product;

    public RemoveProductFromCartMessage(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }
}
