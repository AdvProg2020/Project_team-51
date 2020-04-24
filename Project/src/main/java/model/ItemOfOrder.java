package model;

import model.People.Seller;

import java.util.Date;

public class ItemOfOrder {
    private Seller seller;
    private Product product ;
    private Double price;
    private Double discount;
    private Date date;

    public ItemOfOrder(Seller seller, Product product, Double price, Double discount, Date date) {
        this.seller = seller;
        this.product = product;
        this.price = price;
        this.discount = discount;
        this.date = date;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }
}
