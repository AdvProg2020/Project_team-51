package model.Database;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import model.*;
import model.OrderLog.BuyerLog;
import model.OrderLog.Order;
import model.OrderLog.SellerLog;
import model.People.Customer;
import model.People.Manager;
import model.People.Seller;
import model.Requests.*;

import java.io.IOException;

public class Deserialize {
    private YaGson yaGson = new YaGsonBuilder().serializeNulls().enableComplexMapKeySerialization().setPrettyPrinting().create();

    public Deserialize() {
    }


    public void deserializeProducts(String serializedObject) throws IOException {
        Product.addProduct(yaGson.fromJson(serializedObject, Product.class));
    }

    public void deserializeManagers(String serializedObject) throws IOException {
        Manager.addManager(yaGson.fromJson(serializedObject, Manager.class));
    }

    public void deserializeSellers(String serializedObject) throws IOException {
        Seller.addSeller(yaGson.fromJson(serializedObject, Seller.class));
    }

    public void deserializeCustomers(String serializedObject) throws IOException {
        Customer.addCustomer(yaGson.fromJson(serializedObject, Customer.class));
    }

    public void deserializeAuctions(String serializedObject) throws IOException {
        Auction.addAuction(yaGson.fromJson(serializedObject, Auction.class));
    }

    public void deserializeAttributes(String serializedObject) throws IOException {
        Attributes.addAttribute(yaGson.fromJson(serializedObject, Attributes.class));
    }


    public void deserializeOffCodes(String serializedObject) throws IOException {
        OffCode.addOffCode(yaGson.fromJson(serializedObject, OffCode.class));
    }

    public void deserializeRates(String serializedObject) throws IOException {
        Rate.addRate(yaGson.fromJson(serializedObject, Rate.class));
    }

    public void deserializeComments(String serializedObject) throws IOException {
        Comment.addComment(yaGson.fromJson(serializedObject, Comment.class));
    }

    public void deserializeCategories(String serializedObject) throws IOException {
        Category.addCategory(yaGson.fromJson(serializedObject, Category.class));
    }

    public void deserializeAddAuctionRequests(String serializedObject) throws IOException {
        Request.addRequest(yaGson.fromJson(serializedObject, AddAuctionRequest.class));
    }

    public void deserializeAddCommentRequests(String serializedObject) throws IOException {
        Request.addRequest(yaGson.fromJson(serializedObject, AddCommentRequest.class));
    }

    public void deserializeAddItemRequests(String serializedObject) throws IOException {
        Request.addRequest(yaGson.fromJson(serializedObject, AddItemRequest.class));
    }

    public void deserializeAddSellerRequests(String serializedObject) throws IOException {
        Request.addRequest(yaGson.fromJson(serializedObject, AddSellerRequest.class));
    }

    public void deserializeEditAuctionRequests(String serializedObject) throws IOException {
        Request.addRequest(yaGson.fromJson(serializedObject, EditAuctionRequest.class));
    }

    public void deserializeEditProductRequests(String serializedObject) throws IOException {
        Request.addRequest(yaGson.fromJson(serializedObject, EditProductRequest.class));
    }

    public void deserializeAddSellerForItemRequests(String serializedObject) throws IOException {
        Request.addRequest(yaGson.fromJson(serializedObject, AddSellerForItemRequest.class));
    }

    public void deserializeBuyerLog(String serializedObject) throws IOException {
        Order.addOrder(yaGson.fromJson(serializedObject, BuyerLog.class));
    }

    public void deserializeSellerLog(String serializedObject) throws IOException {
        Order.addOrder(yaGson.fromJson(serializedObject, SellerLog.class));
    }

    public void deserializeItemsOfOrders(String serializedObject) throws IOException {
        ItemOfOrder.addItemOfOrder(yaGson.fromJson(serializedObject, ItemOfOrder.class));
    }

}
