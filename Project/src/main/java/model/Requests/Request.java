package model.Requests;

import model.People.Account;

import java.util.Date;

public class Request <T> {
    private T data ;
    private Account sender;
    private String description;
    private Date time;

    public Request(T data, Account sender, String description, Date time) {
        this.sender=sender;
        this.data = data;
        this.description = description;
        this.time = time;
    }

    public T getData() {
        return data;
    }

    public String getDescription() {
        return description;
    }

    public Date getTime() {
        return time;
    }

}
