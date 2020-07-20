package message.Messages.ServerToClient;

import model.OffCode;

public class GetOffCodeMessage {

    private OffCode offCode;

    public GetOffCodeMessage(OffCode offCode) {
        this.offCode = offCode;
    }

    public OffCode getOffCode() {
        return offCode;
    }

}
