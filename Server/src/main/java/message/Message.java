package message;

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


}
