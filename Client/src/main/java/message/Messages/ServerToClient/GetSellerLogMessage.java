package message.Messages.ServerToClient;

import model.OrderLog.SellerLog;

public class GetSellerLogMessage {

    private SellerLog sellerLog;

    public GetSellerLogMessage(SellerLog sellerLog) {
        this.sellerLog = sellerLog;
    }

    public SellerLog getSellerLog() {
        return sellerLog;
    }
}
