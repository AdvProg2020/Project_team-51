package model.Requests;

import control.Exceptions.InvalidProductIdException;
import model.People.Seller;
import model.Product;
import model.Status;

public class AddSellerForItemRequest extends Request {


    private Product product;
    private Seller seller;
    private int quantity;
    private double price;

    public AddSellerForItemRequest(Product product, Seller seller, int quantity, double price) {
        super("add");
        this.product = product;
        this.seller = seller;
        this.quantity = quantity;
        this.price = price;
    }

    @Override
    public String toString() {
        return "" + getRequestId() + " Add Item Request :" +
                "Product : " + product.getName() +
                "\nSeller : " + seller.getUserName() + " | " + seller.getBrandName()
                ;
    }

    @Override
    public void accept() throws InvalidProductIdException {
        product.addSellerForThisProduct(seller);
        product.addPriceOfNewSeller(seller, price);
        product.addQuantityOfNewSeller(seller, quantity);
        this.status = Status.APPROVED;
    }

    public Product getProduct() {
        return product;
    }

    public Seller getSeller() {
        return seller;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }
}
