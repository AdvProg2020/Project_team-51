package model;

import model.People.Customer;

public class Rate {

    private Customer account ;
    private  Product product;
    private int score ;

    public Rate(Customer account, Product product, short score) {
        this.account = account;
        this.product = product;
        this.score = score;
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
}
