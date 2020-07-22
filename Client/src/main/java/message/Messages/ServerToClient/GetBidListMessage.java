package message.Messages.ServerToClient;

import model.Bid;

import java.util.ArrayList;
import java.util.List;

public class GetBidListMessage {

    private List<Bid> bids = new ArrayList<>();

    public GetBidListMessage(List<Bid> bids) {
        this.bids = bids;
    }

    public List<Bid> getBids() {
        return bids;
    }

}
