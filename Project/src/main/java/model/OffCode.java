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

    public OffCode(String offCode, Date beginDate, Date endDate, List<Account> appliedAccounts, int offPercentage, Double maxDiscount) {
        this.offCode = offCode;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.appliedAccounts = appliedAccounts;
        this.offPercentage = offPercentage;
        this.maxDiscount = maxDiscount;
        allOffCodes.add(this);
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

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
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

    public void setMaxDiscount(Double maxDiscount) {
        this.maxDiscount = maxDiscount;
    }
}
