package message.Messages.ServerToClient;

import model.Auction;

public class GetAuctionMessage {

    private Auction auction;

    public GetAuctionMessage(Auction auction) {
        this.auction = auction;
    }

    public Auction getAuction() {
        return auction;
    }
}
