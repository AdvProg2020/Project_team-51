package message.Messages;

import model.Auction;
import model.People.Seller;

public class CreateAddAuctionRequestMessage {

    private Auction auction;
    private Seller seller;

    public CreateAddAuctionRequestMessage(Auction auction, Seller seller) {
        this.auction = auction;
        this.seller = seller;
    }

    public Auction getAuction() {
        return auction;
    }

    public Seller getSeller() {
        return seller;
    }
}
