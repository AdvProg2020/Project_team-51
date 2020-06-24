package model;

import model.People.Seller;

import java.util.ArrayList;
import java.util.Date;

public class Auction {
//todo auctions dont have name
    private static ArrayList<Auction> allAuctions = new ArrayList<Auction>();
    private Seller seller;
    private String auctionId;
    private Date beginDate;
    private Date endDate;
    private ArrayList<Product> appliedProducts = new ArrayList<Product>();
    private int offPercentage;
    private Status auctionStatus;

    public Auction(Seller seller, Date beginDate, Date endDate, ArrayList<Product> appliedProducts, int offPercentage) {
        this.seller = seller;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.appliedProducts = appliedProducts;
        this.offPercentage = offPercentage;
        this.auctionStatus = Status.PENDING_CREATE;
        allAuctions.add(this);
    }

    public static ArrayList<Auction> getAllAuctions() {
        return allAuctions;
    }

    public static Auction getAuctionById(String auctionId) {
        for (Auction auction : allAuctions) {
            if (auction.getAuctionId().equals(auctionId))
                return auction;
        }

        return null;
    }

    public static void addAuction(Auction auction) {
        allAuctions.add(auction);
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

    public void addAppliedProduct(Product product) {
        appliedProducts.add(product);
    }

    public void removeAppliedProduct(Product product) {
        appliedProducts.remove(product);
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

    @Override
    public String toString() {
        return "auctionId : " + auctionId + "\n" +
                "beginDate : " + beginDate + "\n" +
                "endDate : " + endDate + "\n" +
                "offPercentage : " + offPercentage + "\n" +
                "auctionStatus : " + auctionStatus;
    }

    public void setAppliedProducts(ArrayList<Product> newProducts) {
    this.appliedProducts = newProducts;
    }
}
