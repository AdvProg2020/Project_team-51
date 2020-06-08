package model.People;

import model.Category;
import model.OffCode;
import model.Requests.Request;

import java.util.ArrayList;
import java.util.List;

public class Manager extends Account {

    private static List<Request> allRequests = new ArrayList<Request>();
    private static List<OffCode> availableOffCodes = new ArrayList<OffCode>();
    private static List<Category> allCategories = new ArrayList<Category>();


    public Manager(String username, String password, String firstName, String lastName, String email, String phoneNumber) {
        super(username, password, firstName, lastName, null, email, phoneNumber);
    }

    public static List<Request> getAllRequests() {
        return allRequests;
    }

    public void setAllRequests(List<Request> allRequests) {
        Manager.allRequests = allRequests;
    }

    public static void addManager(Manager manager) {
        allAccounts.add(manager);
    }

    public static void addCategory(Category category) {
        allCategories.add(category);
    }

    public List<OffCode> getAvailableOffCodes() {
        return availableOffCodes;
    }

    public void setAvailableOffCodes(List<OffCode> availableOffCodes) {
        Manager.availableOffCodes = availableOffCodes;
    }

    public List<Category> getAllCategories() {
        return allCategories;
    }

    public void setAllCategories(List<Category> allCategories) {
        Manager.allCategories = allCategories;
    }

}
