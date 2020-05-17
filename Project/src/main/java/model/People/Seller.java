package model.People;

import model.Auction;
import model.OrderLog.SellerLog;
import model.Product;
import model.Status;
import model.StatusStates;

import java.util.ArrayList;

public class Seller extends Account{

    private String brandName ;
    private ArrayList<SellerLog> historyOfSells = new ArrayList<>();
    private ArrayList<Product> availableProducts = new ArrayList<Product>();
    private ArrayList<Auction> allAuctions = new ArrayList<Auction>() ;
    private Status status;

    public Seller(String username, String password ,String firstName, String lastName, Double balance,
                  String email, String phoneNumber , String brandName) {
        super(username,password, firstName, lastName, balance, email, phoneNumber);
        this.brandName = brandName ;
        this.status.setState(StatusStates.PENDING_CREATE);
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public ArrayList<SellerLog> getHistoryOfSells() {
        return historyOfSells;
    }

    public void setHistoryOfSells(ArrayList<SellerLog> historyOfSells) {
        this.historyOfSells = historyOfSells;
    }

    public ArrayList<Product> getAvailableProducts() {
        return availableProducts;
    }

    public void addAvailableProduct(Product product) {
        getAvailableProducts().add(product);
    }

    public ArrayList<Auction> getAllAuctions() {
        return allAuctions;
    }

    public void addAuction(Auction auction) {
        this.allAuctions.add(auction);
    }

    public static void  addSeller(Seller seller){
        allAccounts.add(seller);
    }

    public static ArrayList<Seller> getAllSellers (){
        ArrayList<Seller> allSellers = new ArrayList<>();
        for (Account account : allAccounts) {
            if (account instanceof Seller)
                allSellers.add((Seller) account);
        }

        return allSellers;
    }

}
