package message.Messages.ServerToClient;

import model.Chat;

public class GetChatMessage {

    private Chat chat;

    public GetChatMessage(Chat chat) {
        this.chat = chat;
    }

    public Chat getChat() {
        return chat;
    }
}
