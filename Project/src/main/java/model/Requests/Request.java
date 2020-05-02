package model.Requests;

import model.People.Account;
import model.People.Manager;
import model.Status;
import model.StatusStates;

import java.util.Date;

public class Request <T> {

    private T data ;
    private Account sender;
    private String description;
    private Status status;
    private String editedFieldValue;
    private Date time;

    public Request(T data, Account sender,String editedFieldValue, String description, Date time) {
        this.sender=sender;
        this.data = data;
        this.editedFieldValue=editedFieldValue;
        this.description = description;
        this.time = time;
        this.status = new Status(StatusStates.CREATE_PROCESSING);
        Manager.getAllRequests().add(this);
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
