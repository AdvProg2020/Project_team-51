package message.Messages.ClientToServer;

import model.Product;

public class P2PServerPortMessage {

    private Product product;
    private int port;

    public P2PServerPortMessage(Product product, int port) {
        this.product = product;

        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public Product getProduct() {
        return product;
    }
}
