package model.People;

import model.Auction;
import model.OrderLog.Order;
import model.Product;

import java.util.ArrayList;

public class Seller extends Account{

    private String brandName ;
    private ArrayList<Order> historyOfSells = new ArrayList<Order>();
    private ArrayList<Product> availableProducts = new ArrayList<Product>();
    private ArrayList<Auction> allAuctions = new ArrayList<Auction>() ;

    public Seller(String username, String firstName, String lastName, Double balance,
                  String email, String phoneNumber , String brandName) {
        super(username, firstName, lastName, balance, email, phoneNumber);
        this.brandName = brandName ;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public ArrayList<Order> getHistoryOfSells() {
        return historyOfSells;
    }

    public void setHistoryOfSells(ArrayList<Order> historyOfSells) {
        this.historyOfSells = historyOfSells;
    }

    public ArrayList<Product> getAvailableProducts() {
        return availableProducts;
    }

    public void setAvailableProducts(ArrayList<Product> availableProducts) {
        this.availableProducts = availableProducts;
    }

    public ArrayList<Auction> getAllAuctions() {
        return allAuctions;
    }

    public void setAllAuctions(ArrayList<Auction> allAuctions) {
        this.allAuctions = allAuctions;
    }
}
