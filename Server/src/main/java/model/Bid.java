package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import model.People.Seller;

public class Bid {

    private Seller seller;
    private Product product;
    private DoubleProperty currentPrice = new SimpleDoubleProperty(0);

    public Bid(Seller seller, Product product) {
        this.seller = seller;
        this.product = product;
    }

    public Seller getSeller() {
        return seller;
    }

    public Product getProduct() {
        return product;
    }

    public double getCurrentPrice() {
        return currentPrice.get();
    }

    public void setCurrentPrice(double currentPrice) {
        if (currentPrice > this.currentPrice.get())
            this.currentPrice.set(currentPrice);
    }

    public DoubleProperty currentPriceProperty() {
        return currentPrice;
    }
}
