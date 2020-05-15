package model.Database;

import com.gilecode.yagson.YaGson;
import model.*;
import model.OrderLog.Order;
import model.People.Customer;
import model.People.Manager;
import model.People.Seller;
import model.Requests.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Deserialize {
    private YaGson yaGson ;

    public Deserialize(YaGson yaGson) {
        this.yaGson = yaGson;
    }


    public void deserializeProducts() throws IOException {
        File[] files = ReadFromFiles.readFromFile("Project\\src\\resources\\Products");
        for (File file : files) {
            Product.addProduct(yaGson.fromJson(Files.readString(file.toPath()) , Product.class));
        }
    }


    public void deserializeManagers() throws IOException {
        File[] files = ReadFromFiles.readFromFile("Project\\src\\resources\\People\\Mangers");
        for (File file : files) {
            Manager.addManager(yaGson.fromJson(Files.readString(file.toPath()) , Manager.class));
        }
    }

    public void deserializeSellers() throws IOException {
        File[] files = ReadFromFiles.readFromFile("Project\\src\\resources\\People\\Sellers");
        for (File file : files) {
            Seller.addSeller(yaGson.fromJson(Files.readString(file.toPath()) , Seller.class));
        }
    }

    public void deserializeCustomers() throws IOException {
        File[] files = ReadFromFiles.readFromFile("Project\\src\\resources\\People\\Customers");
        for (File file : files) {
            Customer.addCustomer(yaGson.fromJson(Files.readString(file.toPath()) , Customer.class));
        }
    }

    public void deserializeAuctions() throws IOException {
        File[] files = ReadFromFiles.readFromFile("Project\\src\\resources\\Auctions");
        for (File file : files) {
            Auction.addAuction(yaGson.fromJson(Files.readString(file.toPath()) , Auction.class));
        }
    }

    public void deserializeOffCodes() throws IOException {
        File[] files = ReadFromFiles.readFromFile("Project\\src\\resources\\OffCodes");
        for (File file : files) {
            OffCode.addOffCode(yaGson.fromJson(Files.readString(file.toPath()) , OffCode.class));
        }
    }

    public void deserializeRates() throws IOException {
        File[] files = ReadFromFiles.readFromFile("Project\\src\\resources\\Rates");
        for (File file : files) {
            Rate.addRate(yaGson.fromJson(Files.readString(file.toPath()) , Rate.class));
        }
    }

    public void deserializeComments() throws IOException {
        File[] files = ReadFromFiles.readFromFile("Project\\src\\resources\\Comments");
        for (File file : files) {
            Comment.addComment(yaGson.fromJson(Files.readString(file.toPath()) , Comment.class));
        }
    }

    public void deserializeCategories() throws IOException {
        File[] files = ReadFromFiles.readFromFile("Project\\src\\resources\\Categories");
        for (File file : files) {
            Category.addCategory(yaGson.fromJson(Files.readString(file.toPath()) , Category.class));
        }
    }

    public void deserializeAddAuctionRequests() throws IOException {
        File[] files = ReadFromFiles.readFromFile("Project\\src\\resources\\Requests\\AddAuctionRequests");
        for (File file : files) {
            Request.addRequest(yaGson.fromJson(Files.readString(file.toPath()) , AddAuctionRequest.class));
        }
    }

    public void deserializeAddCommentRequests() throws IOException {
        File[] files = ReadFromFiles.readFromFile("Project\\src\\resources\\Requests\\AddCommentRequests");
        for (File file : files) {
            Request.addRequest(yaGson.fromJson(Files.readString(file.toPath()) , AddCommentRequest.class));
        }
    }

    public void deserializeAddItemRequests() throws IOException {
        File[] files = ReadFromFiles.readFromFile("Project\\src\\resources\\Requests\\AddItemRequests");
        for (File file : files) {
            Request.addRequest(yaGson.fromJson(Files.readString(file.toPath()) , AddItemRequest.class));
        }
    }

    public void deserializeAddSellerRequests() throws IOException {
        File[] files = ReadFromFiles.readFromFile("Project\\src\\resources\\Requests\\AddSellerRequests");
        for (File file : files) {
            Request.addRequest(yaGson.fromJson(Files.readString(file.toPath()) , AddSellerRequest.class));
        }
    }

    public void deserializeEditAuctionRequests() throws IOException {
        File[] files = ReadFromFiles.readFromFile("Project\\src\\resources\\Requests\\EditAuctionRequests");
        for (File file : files) {
            Request.addRequest(yaGson.fromJson(Files.readString(file.toPath()) , EditAuctionRequest.class));
        }
    }

    public void deserializeEditProductRequests() throws IOException {
        File[] files = ReadFromFiles.readFromFile("Project\\src\\resources\\Requests\\EditProductRequests");
        for (File file : files) {
            Request.addRequest(yaGson.fromJson(Files.readString(file.toPath()) , EditProductRequest.class));
        }
    }

    public void deserializeOrders() throws IOException {
        File[] files = ReadFromFiles.readFromFile("Project\\src\\resources\\Orders");
        for (File file : files) {
            Order.addOrder(yaGson.fromJson(Files.readString(file.toPath()) , Order.class));
        }
    }

    public void deserializeItemsOfOrders() throws IOException {
        File[] files = ReadFromFiles.readFromFile("Project\\src\\resources\\ItemsOfOrders");
        for (File file : files) {
            ItemOfOrder.addItemOfOrder(yaGson.fromJson(Files.readString(file.toPath()) , ItemOfOrder.class));
        }
    }

}
