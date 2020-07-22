package message.Messages.ServerToClient;

import model.Product;

public class GetProductMessage {

    private Product product;

    public GetProductMessage(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }
}
