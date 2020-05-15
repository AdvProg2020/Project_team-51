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

import java.io.IOException;

public class Serialize {

    private YaGson yaGson ;

    public Serialize(YaGson yaGson) {
        this.yaGson = yaGson;
    }


    public void serializeProduct(Product product) throws IOException {
        String filePath = "Resources\\Products" + product.getProductId() + ".json";
        WriteIntoFiles.writeIntoFile(yaGson.toJson(product, Product.class), filePath);
    }

    public void serializeAccount(Account account) throws IOException {
        if (account instanceof Customer) serializeCustomer((Customer) account);
        else if (account instanceof Manager) serializeManager((Manager) account);
        else if (account instanceof  Seller)  serializeSeller((Seller) account);
    }

    private void serializeManager(Manager manager) throws IOException {
        String filePath = "Resources\\People\\Managers\\" + manager.getUsername() + ".json";
        WriteIntoFiles.writeIntoFile(yaGson.toJson(manager, Manager.class), filePath);
    }

    private void serializeSeller(Seller seller) throws IOException {
        String filePath = "Resources\\People\\Sellers\\" + seller.getUsername() + ".json";
        WriteIntoFiles.writeIntoFile(yaGson.toJson(seller, Seller.class), filePath);
    }

    private void serializeCustomer(Customer customer) throws IOException {
        String filePath = "Resources\\People\\Customers\\" + customer.getUsername() + ".json";
        WriteIntoFiles.writeIntoFile(yaGson.toJson(customer, Customer.class), filePath);
    }

    public void serializeAuction(Auction auction) throws IOException {
        String filePath = "Resources\\Auctions\\" + auction.getAuctionId() + ".json";
        WriteIntoFiles.writeIntoFile(yaGson.toJson(auction, Auction.class), filePath);
    }

    public void serializeOffCode(OffCode offCode) throws IOException {
        String filePath = "Resources\\OffCodes\\" + offCode.getOffCode() + ".json";
        WriteIntoFiles.writeIntoFile(yaGson.toJson(offCode, OffCode.class), filePath);
    }

    public void serializeRate(Rate rate) throws IOException {
        String filePath = "Resources\\Rates\\" + "rt_" + rate.getAccount().getUsername() + "_"
                + rate.getProduct() + "_" + (int)(Math.random()*100) + ".json";
        WriteIntoFiles.writeIntoFile(yaGson.toJson(rate , Rate.class), filePath);
    }

    public void serializeComment(Comment comment) throws IOException {
        String filePath = "Resources\\Comments\\" + "cm_" +comment.getAccount().getUsername() + "_"
                + comment.getProduct() + "_" + (int)(Math.random()*100) + ".json";
        WriteIntoFiles.writeIntoFile(yaGson.toJson(comment, Comment.class), filePath);
    }

    public void serializeCategory(Category category) throws IOException {
        String filePath = "Resources\\Categories\\" + category.getName() + "_" + (int)(Math.random()*100)
                + (int)(Math.random()*100) + ".json";
        WriteIntoFiles.writeIntoFile(yaGson.toJson(category, Category.class), filePath);
    }

    public void serializeRequest(Request request) throws IOException {
        String filePath = "Resources\\Requests\\" + request.getRequestId()  + ".json";
        if (request instanceof AddAuctionRequest){
            serializeAddAuctionRequest((AddAuctionRequest) request,filePath);
        } else if (request instanceof AddCommentRequest){
            serializeAddCommentRequest((AddCommentRequest) request,filePath);
        } else if (request instanceof AddItemRequest){
            serializeAddItemRequest((AddItemRequest) request,filePath);
        } else if (request instanceof AddSellerRequest){
            serializeAddSellerRequest((AddSellerRequest) request,filePath);
        } else if (request instanceof EditAuctionRequest){
            serializeEditAuctionRequest((EditAuctionRequest) request,filePath);
        } else if (request instanceof EditProductRequest){
            serializeEditProductRequest((EditProductRequest) request,filePath);
        }
    }

    private void serializeAddAuctionRequest(AddAuctionRequest request , String path) throws IOException {
        WriteIntoFiles.writeIntoFile(yaGson.toJson(request, AddAuctionRequest.class), path);
    }

    private void serializeAddCommentRequest(AddCommentRequest request , String path) throws IOException {
        WriteIntoFiles.writeIntoFile(yaGson.toJson(request, AddCommentRequest.class), path);
    }

    private void serializeAddItemRequest(AddItemRequest request , String path) throws IOException {
        WriteIntoFiles.writeIntoFile(yaGson.toJson(request, AddItemRequest.class), path);
    }

    private void serializeAddSellerRequest(AddSellerRequest request , String path) throws IOException {
        WriteIntoFiles.writeIntoFile(yaGson.toJson(request, AddSellerRequest.class), path);
    }

    private void serializeEditAuctionRequest(EditAuctionRequest request , String path) throws IOException {
        WriteIntoFiles.writeIntoFile(yaGson.toJson(request, EditAuctionRequest.class), path);
    }

    private void serializeEditProductRequest(EditProductRequest request , String path) throws IOException {
        WriteIntoFiles.writeIntoFile(yaGson.toJson(request, EditProductRequest.class), path);
    }

    public void serializeOrder(Order order) throws IOException {
            String filePath = "Resources\\Orders\\" + "order_" + order.getOrderID() + ".json";
        if (order instanceof BuyerLog)
            WriteIntoFiles.writeIntoFile(yaGson.toJson(order, BuyerLog.class), filePath);
        else if (order instanceof SellerLog)
            WriteIntoFiles.writeIntoFile(yaGson.toJson(order, SellerLog.class), filePath);

    }

    public void serializeItemOfOrder(ItemOfOrder itemOfOrder) throws IOException {
        String filePath = "Resources\\ItemsOfOrders\\" + "item_" +itemOfOrder.getSeller().getUsername() + "_"
                + itemOfOrder.getProduct().getProductId() + "_" + itemOfOrder.getDate() + ".json";
        WriteIntoFiles.writeIntoFile(yaGson.toJson(itemOfOrder, ItemOfOrder.class), filePath);
    }


}
