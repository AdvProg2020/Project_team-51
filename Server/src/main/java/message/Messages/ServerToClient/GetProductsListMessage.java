package message.Messages.ServerToClient;

import model.Product;

import java.util.ArrayList;
import java.util.List;

public class GetProductsListMessage {

    private List<Product> products = new ArrayList<>();

    public GetProductsListMessage(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }
}
