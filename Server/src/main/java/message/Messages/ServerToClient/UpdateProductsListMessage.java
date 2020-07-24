package message.Messages.ServerToClient;

import model.Product;

import java.util.ArrayList;
import java.util.List;

public class UpdateProductsListMessage {

    private List<Product> products = new ArrayList<>();

    public UpdateProductsListMessage(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }
}
