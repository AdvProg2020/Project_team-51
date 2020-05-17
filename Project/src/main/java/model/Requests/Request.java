package model.Requests;

import control.Exceptions.InvalidProductIdException;
import model.StatusStates;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public abstract class Request  {

    public static ArrayList<Request> allRequests = new ArrayList<>();
    private String requestId;
    public StatusStates status ;
    private Date date;

    public Request(String requestId , String kind) {
        this.requestId = requestId;
        if (kind.equals("add"))
            status=StatusStates.PENDING_CREATE;
        else if (kind.equals("edit")){
            status=StatusStates.PENDING_EDIT;
        }
        this.date = new Date();
    }


    public static ArrayList<Request> getAllRequests() {
        return allRequests;
    }

    public StatusStates getStatus() {
        return status;
    }

    public String getRequestId() {
        return requestId;
    }

    public String digest(){
        return "Request ID : " + requestId ;
    }

    public void accept() throws InvalidProductIdException, ParseException {

    }

    public static Request getId(String id) {
        for (Request request : allRequests) {
            if (request.requestId.equals(id))
                return request;
        }
        return null;
    }

    public static void addRequest(Request request){
        allRequests.add(request);
    }
}
