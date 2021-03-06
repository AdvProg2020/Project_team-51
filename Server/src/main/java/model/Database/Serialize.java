package model.Database;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
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

    private YaGson yaGson = new YaGsonBuilder().serializeNulls().enableComplexMapKeySerialization().setPrettyPrinting().create();

    public Serialize() {
    }

    public void serializeProduct(Product product) throws IOException {
        String filePath = "Server\\src\\main\\resources\\Products\\" + product.getProductId() + ".json";
        WriteIntoFiles.writeIntoFile(yaGson.toJson(product, Product.class), filePath);
    }

    public void serializeAccount(Account account) throws IOException {
        if (account instanceof Customer) serializeCustomer((Customer) account);
        else if (account instanceof Manager) serializeManager((Manager) account);
        else if (account instanceof Seller) serializeSeller((Seller) account);
    }

    private void serializeManager(Manager manager) throws IOException {
        String filePath = "Server\\src\\main\\resources\\People\\Managers\\" + "Manager_" + manager.getUserName() + ".json";
        WriteIntoFiles.writeIntoFile(yaGson.toJson(manager, Manager.class), filePath);
    }

    private void serializeSeller(Seller seller) throws IOException {
        String filePath = "Server\\src\\main\\resources\\People\\Sellers\\" + "Seller_" + seller.getUserName() + ".json";
        WriteIntoFiles.writeIntoFile(yaGson.toJson(seller, Seller.class), filePath);
    }

    private void serializeCustomer(Customer customer) throws IOException {
        String filePath = "Server\\src\\main\\resources\\People\\Customers\\" + "Customer_" + customer.getUserName() + ".json";
        WriteIntoFiles.writeIntoFile(yaGson.toJson(customer, Customer.class), filePath);
    }

    public void serializeAuction(Auction auction) throws IOException {
        String filePath = "Server\\src\\main\\resources\\Auctions\\" + auction.getAuctionId() + ".json";
        WriteIntoFiles.writeIntoFile(yaGson.toJson(auction, Auction.class), filePath);
    }

    public void serializeOffCode(OffCode offCode) throws IOException {
        String filePath = "Server\\src\\main\\resources\\OffCodes\\" + offCode.getOffCode() + ".json";
        WriteIntoFiles.writeIntoFile(yaGson.toJson(offCode, OffCode.class), filePath);
    }

    public void serializeRate(Rate rate) throws IOException {
        String filePath = "Server\\src\\resources\\Rates\\" + "RT_" + rate.getRateId() + ".json";
        WriteIntoFiles.writeIntoFile(yaGson.toJson(rate, Rate.class), filePath);
    }

    public void serializeComment(Comment comment) throws IOException {
        String filePath = "Server\\src\\main\\resources\\Comments\\" + "CM_" + comment.getCommentId() + ".json";
        WriteIntoFiles.writeIntoFile(yaGson.toJson(comment, Comment.class), filePath);
    }

    public void serializeCategory(Category category) throws IOException {
        String filePath = "Server\\src\\main\\resources\\Categories\\" + "CTG_" + category.getName() + "_" +
                category.getCategoryId() + ".json";
        WriteIntoFiles.writeIntoFile(yaGson.toJson(category, Category.class), filePath);
    }

    public void serializeRequest(Request request) throws IOException {
        String filePath = "Server\\src\\main\\resources\\Requests\\";
        if (request instanceof AddAuctionRequest) {
            serializeAddAuctionRequest((AddAuctionRequest) request, filePath + "AddAuctionRequests\\"
                    + "AA_" + request.getRequestId() + ".json");
        } else if (request instanceof AddCommentRequest) {
            serializeAddCommentRequest((AddCommentRequest) request, filePath + "AddCommentRequests\\"
                    + "AC_" + request.getRequestId() + ".json");
        } else if (request instanceof AddItemRequest) {
            serializeAddItemRequest((AddItemRequest) request, filePath + "AddItemRequests\\"
                    + "AI_" + request.getRequestId() + ".json");
        } else if (request instanceof AddSellerRequest) {
            serializeAddSellerRequest((AddSellerRequest) request, filePath + "AddSellerRequests\\"
                    + "AS_" + request.getRequestId() + ".json");
        } else if (request instanceof EditAuctionRequest) {
            serializeEditAuctionRequest((EditAuctionRequest) request, filePath + "EditAuctionRequest\\"
                    + "EA_" + request.getRequestId() + ".json");
        } else if (request instanceof EditProductRequest) {
            serializeEditProductRequest((EditProductRequest) request, filePath + "EditProductRequests\\"
                    + "EP_" + request.getRequestId() + ".json");
        } else if (request instanceof AddSellerForItemRequest) {
            serializeAddSellerForItemRequest((AddSellerForItemRequest) request, filePath + "AddSellerForItemRequests\\"
                    + "ASI_" + request.getRequestId() + ".json");
        }
    }

    private void serializeAddSellerForItemRequest(AddSellerForItemRequest request, String path) throws IOException {
        WriteIntoFiles.writeIntoFile(yaGson.toJson(request, AddSellerForItemRequest.class), path);
    }


    private void serializeAddAuctionRequest(AddAuctionRequest request, String path) throws IOException {
        WriteIntoFiles.writeIntoFile(yaGson.toJson(request, AddAuctionRequest.class), path);
    }

    private void serializeAddCommentRequest(AddCommentRequest request, String path) throws IOException {
        WriteIntoFiles.writeIntoFile(yaGson.toJson(request, AddCommentRequest.class), path);
    }

    private void serializeAddItemRequest(AddItemRequest request, String path) throws IOException {
        WriteIntoFiles.writeIntoFile(yaGson.toJson(request, AddItemRequest.class), path);
    }

    private void serializeAddSellerRequest(AddSellerRequest request, String path) throws IOException {
        WriteIntoFiles.writeIntoFile(yaGson.toJson(request, AddSellerRequest.class), path);
    }

    private void serializeEditAuctionRequest(EditAuctionRequest request, String path) throws IOException {
        WriteIntoFiles.writeIntoFile(yaGson.toJson(request, EditAuctionRequest.class), path);
    }

    private void serializeEditProductRequest(EditProductRequest request, String path) throws IOException {
        WriteIntoFiles.writeIntoFile(yaGson.toJson(request, EditProductRequest.class), path);
    }

    public void serializeOrder(Order order) throws IOException {
        if (order instanceof BuyerLog)
            serializeBuyerLog((BuyerLog) order);
        else if (order instanceof SellerLog)
            serializeSellerLog((SellerLog) order);

    }

    private void serializeBuyerLog(BuyerLog buyerLog) throws IOException {
        String filePath = "Server\\src\\main\\resources\\Orders\\" + "BL_" + buyerLog.getOrderID() + ".json";
        WriteIntoFiles.writeIntoFile(yaGson.toJson(buyerLog, BuyerLog.class), filePath);
    }

    private void serializeSellerLog(SellerLog sellerLog) throws IOException {
        String filePath = "Server\\src\\main\\resources\\Orders\\" + "SL_" + sellerLog.getOrderID() + ".json";
        WriteIntoFiles.writeIntoFile(yaGson.toJson(sellerLog, BuyerLog.class), filePath);
    }

    public void serializeItemOfOrder(ItemOfOrder itemOfOrder) throws IOException {
        String filePath = "Server\\src\\main\\resources\\ItemsOfOrders\\" + "IOO_" + itemOfOrder.getSeller().getUserName() + "_"
                + itemOfOrder.getProduct().getProductId() + "_" + itemOfOrder.getDate() + ".json";
        WriteIntoFiles.writeIntoFile(yaGson.toJson(itemOfOrder, ItemOfOrder.class), filePath);
    }

    public void serializeAttributes(Attributes attribute) throws IOException {
        String filePath = "Server\\src\\main\\resources\\Attributes\\" + "ATB_" + attribute.getField() + ".json";
        WriteIntoFiles.writeIntoFile(yaGson.toJson(attribute, Attributes.class), filePath);
    }


}
