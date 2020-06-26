package model;

import control.Exceptions.InvalidProductIdException;
import model.People.Seller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ItemOfOrder {

    public static List<ItemOfOrder> allItemOfOrders = new ArrayList<>();
    private Seller seller;
    private Product product;
    private Double price;
    private int discount;
    private Date date;
    private int quantity;

    public ItemOfOrder(Seller seller, Product product, Double price, int discount, Date date) {
        this.seller = seller;
        this.product = product;
        this.price = price;
        this.discount = discount;
        this.date = date;
        allItemOfOrders.add(this);
        quantity = 0;
    }

    public static void addItemOfOrder(ItemOfOrder itemOfOrder) {
        allItemOfOrders.add(itemOfOrder);
    }

    public int getQuantity() {
        return quantity;
    }

    public void incrementQuantity() {
        quantity++;
    }

    public void decrementQuantity() throws InvalidProductIdException {
        if (quantity <= 0)
            throw new InvalidProductIdException();
        quantity--;
    }

    public double getTotalPrice() {
        return quantity * price;
    }

    public double getTotalPriceWithDiscount() {
        return (quantity * price) * (1 - discount / 100);
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

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getProductString(){
        return product.getName();
    }

    public String getPriceString(){
        return Double.toString(price);
    }

    public String getDiscountString(){
        return Integer.toString(discount);
    }

    public String getDateString (){
        return date.toString();
    }

    public String getQuantityString (){
        return Integer.toString(quantity);
    }

    @Override
    public String toString() {
        return "product : " + product.getName() +
                " ,price : " + price +
                " ,discount : " + discount +
                " ,date : " + date +
                " ,quantity :" + quantity + "\n";
    }
}
