package model;

import control.TokenGenerator;
import model.People.Account;

import java.util.Date;

public class TextMessage {
    String content;
    Date sendDate;
    Account sender;
    String id;
    //Chat chat;  //is this necessary?

    public TextMessage(Account sender, String content) {
        this.id = TokenGenerator.generateMessageId();
        this.content = content;
        this.sendDate = new Date();
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public Account getSender() {
        return sender;
    }

    public String getId() {
        return id;
    }
}
