package model;

import model.People.Account;

import java.util.ArrayList;

public class Comment {
    public static ArrayList<Comment> allComments = new ArrayList<>();
    private Account account;
    private Product product;
    private String title ;
    private String context ;
    private StatusStates commentStatus;
    private Boolean isBuyer;

    public Comment(Account account, Product product, String context , String title , StatusStates commentStatus, Boolean isBuyer) {
        this.account = account;
        this.product = product;
        this.context = context;
        this.title = title;
        this.commentStatus = commentStatus;
        this.isBuyer = isBuyer;
        allComments.add(this);
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

    public StatusStates getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(StatusStates commentStatus) {
        this.commentStatus = commentStatus;
    }

    public Boolean getBuyer() {
        return isBuyer;
    }

    public void setBuyer(Boolean buyer) {
        isBuyer = buyer;
    }

    public static void addComment(Comment comment){
        allComments.add(comment);
    }
}
