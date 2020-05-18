package model.Requests;

import control.Exceptions.InvalidProductIdException;
import model.Status;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public abstract class Request {

    public static ArrayList<Request> allRequests = new ArrayList<>();
    public Status status;
    private String requestId;
    private Date date;

    public Request(String requestId, String kind) {
        this.requestId = requestId;
        if (kind.equals("add"))
            status = Status.PENDING_CREATE;
        else if (kind.equals("edit")) {
            status = Status.PENDING_EDIT;
        }
        this.date = new Date();
    }


    public static ArrayList<Request> getAllRequests() {
        return allRequests;
    }

    public static Request getId(String id) {
        for (Request request : allRequests) {
            if (request.requestId.equals(id))
                return request;
        }
        return null;
    }

    public static void addRequest(Request request) {
        allRequests.add(request);
    }

    public Status getStatus() {
        return status;
    }

    public String getRequestId() {
        return requestId;
    }

    public String digest() {
        return "Request ID : " + requestId;
    }

    public void accept() throws InvalidProductIdException, ParseException {

    }
}
