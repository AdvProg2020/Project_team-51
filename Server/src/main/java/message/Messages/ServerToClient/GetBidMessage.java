package message.Messages.ServerToClient;

import model.Bid;

public class GetBidMessage {

    private Bid bid;

    public GetBidMessage(Bid bid) {
        this.bid = bid;
    }

    public Bid getBid() {
        return bid;
    }
}
