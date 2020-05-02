package model.People;

import model.Category;
import model.OffCode;
import model.Requests.Request;

import java.util.ArrayList;

public class Manager extends Account {

    private static ArrayList<Request> allRequests = new ArrayList<Request>();
    private static ArrayList<OffCode> availableOffCodes = new ArrayList<OffCode>();
    private static ArrayList<Category> allCategories = new ArrayList<Category>();


    public Manager(String username,String password, String firstName, String lastName, Double balance, String email, String phoneNumber) {
        super(username,password, firstName, lastName, balance, email, phoneNumber);
    }

    public static ArrayList<Request> getAllRequests() {
        return allRequests;
    }

    public void setAllRequests(ArrayList<Request> allRequests) {
        this.allRequests = allRequests;
    }

    public ArrayList<OffCode> getAvailableOffCodes() {
        return availableOffCodes;
    }

    public void setAvailableOffCodes(ArrayList<OffCode> availableOffCodes) {
        this.availableOffCodes = availableOffCodes;
    }

    public ArrayList<Category> getAllCategories() {
        return allCategories;
    }

    public void setAllCategories(ArrayList<Category> allCategories) {
        this.allCategories = allCategories;
    }


}
