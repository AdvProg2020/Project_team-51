package model;

import model.People.Account;

public class Comment {
    private Account account;
    private Product product;
    private String title ;
    private String context ;
    private Status commentStatus;
    private Boolean isBuyer;

    public Comment(Account account, Product product, String context , String title , Status commentStatus, Boolean isBuyer) {
        this.account = account;
        this.product = product;
        this.context = context;
        this.title = title;
        this.commentStatus = commentStatus;
        this.isBuyer = isBuyer;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Status getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(Status commentStatus) {
        this.commentStatus = commentStatus;
    }

    public Boolean getBuyer() {
        return isBuyer;
    }

    public void setBuyer(Boolean buyer) {
        isBuyer = buyer;
    }
}
