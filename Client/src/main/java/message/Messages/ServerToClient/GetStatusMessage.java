package message.Messages.ServerToClient;

import model.Status;

public class GetStatusMessage {

    private Status status;

    public GetStatusMessage(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
}
