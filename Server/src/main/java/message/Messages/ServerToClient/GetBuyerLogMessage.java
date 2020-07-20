package message.Messages.ServerToClient;

import model.OrderLog.BuyerLog;

public class GetBuyerLogMessage {

    private BuyerLog buyerLog;

    public GetBuyerLogMessage(BuyerLog buyerLog) {
        this.buyerLog = buyerLog;
    }

    public BuyerLog getBuyerLog() {
        return buyerLog;
    }
}
