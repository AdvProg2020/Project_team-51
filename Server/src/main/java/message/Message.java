package message;

import Server.JsonConverter;
import Server.Server;

public class Message {

    private MessageType messageType;

    //serverName || clientName
    private String sender;
    private String receiver;


    private Message(String receiver) {
        this.sender = Server.getInstance().serverName;
        this.receiver = receiver;
    }

    public static Message convertJsonToMessage(String messageJson) {
        return JsonConverter.fromJson(messageJson, Message.class);
    }


    public String toJson() {
        return JsonConverter.toJson(this);
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }
}
