package message.Messages.ClientToServer;

import model.Auction;

public class CreateEditAuctionRequestMessage {
    private Auction auction;
    private String field;
    private String value;

    public CreateEditAuctionRequestMessage(Auction auction, String field, String value) {
        this.auction = auction;
        this.field = field;
        this.value = value;
    }

    public Auction getAuction() {
        return auction;
    }

    public String getField() {
        return field;
    }

    public String getValue() {
        return value;
    }
}
