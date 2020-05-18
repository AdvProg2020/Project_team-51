package model.Requests;

import control.Exceptions.InvalidProductIdException;
import model.People.Seller;
import model.Product;

public class AddSellerForItemRequest extends Request {


    private Product product;
    private Seller seller;
    private int quantity;
    private double price;

    public AddSellerForItemRequest(String requestId, Product product, Seller seller, int quantity, double price) {
        super(requestId, "add");
        this.product = product;
        this.seller = seller;
        this.quantity = quantity;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Add Item Request :" +
                "Product : " + product.getName() +
                "\nSeller : " + seller.getUsername() + " | " + seller.getBrandName()
                ;
    }

    @Override
    public void accept() throws InvalidProductIdException {
//
//        product.setQuantity(quantity);
//        product.setPrice();

    }
}
