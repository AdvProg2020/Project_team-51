package message.requests;

import model.People.Seller;
import model.Product;

public class CreateAddSellerForItemRequestMessage {

    private Product product;
    private Seller seller;
    private int quantity;
    private double price;

    public CreateAddSellerForItemRequestMessage(Product product, Seller seller, int quantity, double price) {
        this.product = product;
        this.seller = seller;
        this.quantity = quantity;
        this.price = price;
    }

    public Seller getSeller() {
        return seller;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }
}
