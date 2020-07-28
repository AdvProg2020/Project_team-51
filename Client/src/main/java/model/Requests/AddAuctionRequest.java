package model.Requests;

import model.Auction;
import model.People.Seller;
import model.Status;

public class AddAuctionRequest extends Request {

    private Auction auction;
    private Seller seller;

    public AddAuctionRequest(Auction auction, Seller seller) {
        super("add");
        this.auction = auction;
        this.seller = seller;
    }

    @Override
    public String toString() {
        return "" + getRequestId() + " Add Auction Request :\n" +
                auction.getAuctionId() + " | " + auction.getOffPercentage() + "% | from : " + auction.getBeginDate()
                + " to : " + auction.getEndDate();

    }

    @Override
    public void accept() {
        seller.addAuction(auction);
        status = Status.APPROVED;
        auction.setAuctionStatus(Status.APPROVED);
    }

    public Auction getAuction() {
        return auction;
    }

    public Seller getSeller() {
        return seller;
    }
}
