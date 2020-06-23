package model;

import model.People.Account;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OffCode {

    private static List<OffCode> allOffCodes = new ArrayList<OffCode>();
    private String offCode;
    private Date beginDate;
    private Date endDate;
    private Double maxDiscount;
    private List<Account> appliedAccounts = new ArrayList<Account>();
    private int offPercentage;
    private Status status;

    public OffCode(String offCode, Date beginDate, Date endDate, List<Account> appliedAccounts, int offPercentage, Double maxDiscount) {
        this.offCode = offCode;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.appliedAccounts = appliedAccounts;
        this.offPercentage = offPercentage;
        this.maxDiscount = maxDiscount;
        allOffCodes.add(this);
        status = Status.APPROVED;


    }

    public static List<OffCode> getAllOffCodes() {
        return allOffCodes;
    }

    public static void setAllOffCodes(List<OffCode> allOffCodes) {
        OffCode.allOffCodes = allOffCodes;
    }

    public static OffCode getOffIdById(String offId) {
        for (OffCode offCode : allOffCodes) {
            if (offCode.offCode.equals(offId))
                return offCode;
        }

        return null;
    }

    public static void addOffCode(OffCode offCode) {
        allOffCodes.add(offCode);
    }

    public String getOffCode() {
        return offCode;
    }

    public void setOffCode(String offCode) {
        this.offCode = offCode;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public String getBeginDateString (){
        return beginDate.toString();
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getEndDateString(){
        return endDate.toString();
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<Account> getAppliedAccounts() {
        return appliedAccounts;
    }

    public void setAppliedAccounts(List<Account> appliedAccounts) {
        this.appliedAccounts = appliedAccounts;
    }

    public int getOffPercentage() {
        return offPercentage;
    }

    public void setOffPercentage(int offPercentage) {
        this.offPercentage = offPercentage;
    }

    public Double getMaxDiscount() {
        return maxDiscount;
    }

    public void setMaxDiscount(Double maxDiscount) {
        this.maxDiscount = maxDiscount;
    }

    public Status getStatus() {
        return status;
    }

    public String getStatusString (){
        if (status.equals(Status.APPROVED)) return "approved";
        else if (status.equals(Status.PENDING_CREATE)) return "pending create";
        else if (status.equals(Status.ENDED)) return "ended";
        else return "pending edit";
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getUselessString (){
        return "";
    }
}
