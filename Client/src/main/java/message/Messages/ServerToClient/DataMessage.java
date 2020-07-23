package message.Messages.ServerToClient;

import model.Bid;
import model.Category;
import model.Product;

import java.util.ArrayList;
import java.util.List;

public class DataMessage {

    List<Category> categories = new ArrayList<>();
    List<Product> products = new ArrayList<>();
    List<Bid> bids = new ArrayList<>();
    boolean isThereAnyManager = false;

    public DataMessage(List<Category> categories, List<Product> products, List<Bid> bids, boolean isThereAnyManager) {
        this.categories = categories;
        this.products = products;
        this.bids = bids;
        this.isThereAnyManager = isThereAnyManager;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public boolean isThereAnyManager() {
        return isThereAnyManager;
    }
}
