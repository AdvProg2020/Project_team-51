package model.Database;

import com.gilecode.yagson.YaGson;
import model.*;
import model.OrderLog.BuyerLog;
import model.OrderLog.Order;
import model.OrderLog.SellerLog;
import model.People.Account;
import model.People.Customer;
import model.People.Manager;
import model.People.Seller;
import model.Requests.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class Deserialize {
    private YaGson yaGson ;

    public Deserialize(YaGson yaGson) {
        this.yaGson = yaGson;
    }


    public void deserializeProduct() throws IOException {
        File[] files = ReadFromFiles.readFromFile("Resources\\Products");
        for (File file : files) {
            Product.addProduct(yaGson.fromJson(Files.readString(file.toPath()) , Product.class));
        }
    }


    public void deserializeManager() throws IOException {
        File[] files = ReadFromFiles.readFromFile("Resources\\People\\Mangers");
        for (File file : files) {
            Manager.addManager(yaGson.fromJson(Files.readString(file.toPath()) , Manager.class));
        }
    }

    public void deserializeSeller() throws IOException {
        File[] files = ReadFromFiles.readFromFile("Resources\\People\\Sellers");
        for (File file : files) {
            Seller.addSeller(yaGson.fromJson(Files.readString(file.toPath()) , Seller.class));
        }
    }

    public void deserializeCustomer() throws IOException {
        File[] files = ReadFromFiles.readFromFile("Resources\\People\\Customers");
        for (File file : files) {
            Customer.addCustomer(yaGson.fromJson(Files.readString(file.toPath()) , Customer.class));
        }
    }

    public void deserializeAuction() throws IOException {
        File[] files = ReadFromFiles.readFromFile("Resources\\Auctions");
        for (File file : files) {
            Auction.addAuction(yaGson.fromJson(Files.readString(file.toPath()) , Auction.class));
        }
    }

    public void deserializeOffCode() throws IOException {
        File[] files = ReadFromFiles.readFromFile("Resources\\OffCodes");
        for (File file : files) {
            OffCode.addOffCode(yaGson.fromJson(Files.readString(file.toPath()) , OffCode.class));
        }
    }

    public void deserializeRate() throws IOException {
        File[] files = ReadFromFiles.readFromFile("Resources\\Rates");
        for (File file : files) {
            Rate.addRate(yaGson.fromJson(Files.readString(file.toPath()) , Rate.class));
        }
    }

    public void deserializeComment() throws IOException {
        File[] files = ReadFromFiles.readFromFile("Resources\\Comments");
        for (File file : files) {
            Comment.addComment(yaGson.fromJson(Files.readString(file.toPath()) , Comment.class));
        }
    }

    public void deserializeCategory() throws IOException {
        File[] files = ReadFromFiles.readFromFile("Resources\\Categories");
        for (File file : files) {
            Category.addCategory(yaGson.fromJson(Files.readString(file.toPath()) , Category.class));
        }
    }

    public void deserializeRequest() throws IOException {
        File[] files = ReadFromFiles.readFromFile("Resources\\Requests");
        for (File file : files) {
            Request.addRequest(yaGson.fromJson(Files.readString(file.toPath()) , Request.class));
        }
    }

    public void deserializeOrder() throws IOException {
        File[] files = ReadFromFiles.readFromFile("Resources\\Orders");
        for (File file : files) {
            Order.addOrder(yaGson.fromJson(Files.readString(file.toPath()) , Order.class));
        }
    }

    public void deserializeItemOfOrder() throws IOException {
        File[] files = ReadFromFiles.readFromFile("Resources\\ItemsOfOrders");
        for (File file : files) {
            ItemOfOrder.addItemOfOrder(yaGson.fromJson(Files.readString(file.toPath()) , ItemOfOrder.class));
        }
    }

}
