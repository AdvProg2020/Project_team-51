package message.Messages.ClientToServer;

import model.OffCode;

public class CreateOffCodeMessage {

    private OffCode offCode;

    public CreateOffCodeMessage(OffCode offCode) {
        this.offCode = offCode;
    }

    public OffCode getOffCode() {
        return offCode;
    }
}
