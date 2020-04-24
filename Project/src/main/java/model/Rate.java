package model;

import model.People.Account;
import model.People.Customer;

public class Rate {

    private Customer account ;
    private  Product product;
    private short score ;

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

    public short getScore() {
        return score;
    }

    public void setScore(short score) {
        this.score = score;
    }
}
