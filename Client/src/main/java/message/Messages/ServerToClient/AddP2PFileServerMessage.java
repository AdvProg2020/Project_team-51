package message.Messages.ServerToClient;

import model.Product;

public class AddP2PFileServerMessage {

    private Product product;

    public AddP2PFileServerMessage(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
