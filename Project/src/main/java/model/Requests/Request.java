package model.Requests;

import model.People.Account;
import model.People.Manager;
import model.Status;
import model.StatusStates;

import java.util.ArrayList;
import java.util.Date;

public abstract class Request  {

    public static ArrayList<Request> allRequests = new ArrayList<>();
    private String requestId;
    public Status status ;
    private Date date;

    public Request(String requestId , String kind) {
        this.requestId = requestId;
        if (kind.equals("add"))
            status.setState(StatusStates.CREATE_PROCESSING);
        else if (kind.equals("edit")){
            status.setState(StatusStates.EDIT_PROCESSING);
        }
        this.date = new Date();
    }


    public static ArrayList<Request> getAllRequests() {
        return allRequests;
    }

    public Status getStatus() {
        return status;
    }

    public String getRequestId() {
        return requestId;
    }

    public String digest(){
        return "Request ID : " + requestId ;
    }

    public void accept(){

    }
}
