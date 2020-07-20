package message.Messages;

public class ResponseToClientMessage {
    private String message;

    public ResponseToClientMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
