package model.People;

import model.Auction;
import model.OrderLog.SellerLog;
import model.Product;
import model.Status;
import model.Wallet;

import java.util.ArrayList;
import java.util.List;

public class Seller extends Account {

    private List<SellerLog> historyOfSells = new ArrayList<>();
    private List<Product> availableProducts = new ArrayList<>();
    private List<Auction> allAuctions = new ArrayList<>();
    private String brandName;
    private Status status;
    private Wallet wallet;
    public Seller(String username, String password, String firstName, String lastName, Double balance,
                  String email, String phoneNumber, String brandName) {
        super(username, password, firstName, lastName, balance, email, phoneNumber);
        this.brandName = brandName;
        this.status = Status.PENDING_CREATE;
        this.wallet = new Wallet(balance,this);
    }

    public static void addSeller(Seller seller) {
        allAccounts.add(seller);
    }

    public static ArrayList<Seller> getAllSellers() {
        ArrayList<Seller> allSellers = new ArrayList<>();
        for (Account account : allAccounts) {
            if (account instanceof Seller)
                allSellers.add((Seller) account);
        }

        return allSellers;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public List<SellerLog> getHistoryOfSells() {
        return historyOfSells;
    }

    public List<Product> getAvailableProducts() {
        return availableProducts;
    }

    public void addAvailableProduct(Product product) {
        getAvailableProducts().add(product);
    }

    public List<Auction> getAllAuctions() {
        return allAuctions;
    }

    public void addAuction(Auction auction) {
        this.allAuctions.add(auction);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Wallet getWallet() {
        return wallet;
    }
}
