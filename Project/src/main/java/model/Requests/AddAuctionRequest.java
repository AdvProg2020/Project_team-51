package model.Requests;

import model.Auction;
import model.People.Seller;
import model.StatusStates;

public class AddAuctionRequest extends Request {

    private Auction auction;
    private Seller seller;

    public AddAuctionRequest(String requestId , Auction auction , Seller seller) {
        super(requestId, "add");
        this.auction = auction ;
        this.seller = seller ;
    }

    @Override
    public String toString() {
        return "Add Auction Request :\n" +
                auction.getAuctionId() + " | " + auction.getOffPercentage() +"% | from : " + auction.getBeginDate()
                + " to : " + auction.getEndDate() ;

    }

    @Override
    public void accept() {
        seller.addAuction(auction);
        status=StatusStates.APPROVED;
        auction.setAuctionStatus(StatusStates.APPROVED);
    }
}
