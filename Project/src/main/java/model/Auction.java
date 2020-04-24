package model;

import model.People.Seller;

import java.util.ArrayList;
import java.util.Date;

public class Auction {

    private static ArrayList<Auction> allAuctions = new ArrayList<Auction>();
    private Seller seller ;
    private String auctionId ;
    private Date beginDate;
    private Date endDate;
    private ArrayList<Product> appliedProducts = new ArrayList<Product>();
    private int offPercentage;
    private Status auctionStatus ;

    public Auction(Seller seller ,Date beginDate, Date endDate, ArrayList<Product> appliedProducts, int offPercentage) {
        this.seller=seller;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.appliedProducts = appliedProducts;
        this.offPercentage = offPercentage;
    }

    public static ArrayList<Auction> getAllAuctions() {
        return allAuctions;
    }

    public String getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(String auctionId) {
        this.auctionId = auctionId;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public ArrayList<Product> getAppliedProducts() {
        return appliedProducts;
    }

    public void setAppliedProducts(ArrayList<Product> appliedProducts) {
        this.appliedProducts = appliedProducts;
    }

    public int getOffPercentage() {
        return offPercentage;
    }

    public void setOffPercentage(int offPercentage) {
        this.offPercentage = offPercentage;
    }

    public Status getAuctionStatus() {
        return auctionStatus;
    }

    public void setAuctionStatus(Status auctionStatus) {
        this.auctionStatus = auctionStatus;
    }
}
