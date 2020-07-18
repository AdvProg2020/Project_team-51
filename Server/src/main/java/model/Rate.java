package model;

import control.TokenGenerator;
import model.People.Customer;

import java.util.ArrayList;

public class Rate {

    public static ArrayList<Rate> allRates = new ArrayList<>();
    private Customer account;
    private Product product;
    private String rateId;
    private int score;

    public Rate(Customer account, Product product, int score) {
        this.account = account;
        this.product = product;
        this.score = score;
        rateId = TokenGenerator.generateRateId();
        allRates.add(this);
    }

    public static void addRate(Rate rate) {
        allRates.add(rate);
    }

    public Customer getAccount() {
        return account;
    }

    public void setAccount(Customer account) {
        this.account = account;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getRateId() {
        return rateId;
    }
}
