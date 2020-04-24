package model;

import model.People.Account;

import java.util.ArrayList;
import java.util.Date;

public class OffCode {

    private static ArrayList<OffCode> allOffCodes = new ArrayList<OffCode>();
    private String offCode ;
    private Date beginDate;
    private Date endDate;
    private Double maxDiscount ;
    private ArrayList<Account> appliedAccounts = new ArrayList<Account>();
    private int offPercentage;

    public OffCode(String offCode, Date beginDate, Date endDate, ArrayList<Account> appliedAccounts, int offPercentage , Double maxDiscount) {
        this.offCode = offCode;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.appliedAccounts = appliedAccounts;
        this.offPercentage = offPercentage;
        this.maxDiscount = maxDiscount ;
        allOffCodes.add(this);
    }

    public static ArrayList<OffCode> getAllOffCodes() {
        return allOffCodes;
    }

    public static void setAllOffCodes(ArrayList<OffCode> allOffCodes) {
        OffCode.allOffCodes = allOffCodes;
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

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public ArrayList<Account> getAppliedAccounts() {
        return appliedAccounts;
    }

    public void setAppliedAccounts(ArrayList<Account> appliedAccounts) {
        this.appliedAccounts = appliedAccounts;
    }

    public int getOffPercentage() {
        return offPercentage;
    }

    public void setOffPercentage(int offPercentage) {
        this.offPercentage = offPercentage;
    }
}
