package message.Messages.ServerToClient;

import model.TextMessage;

public class GetTextMessageMessage {

    private TextMessage textMessage;

    public GetTextMessageMessage(TextMessage textMessage) {
        this.textMessage = textMessage;
    }

    public TextMessage getTextMessage() {
        return textMessage;
    }

}
