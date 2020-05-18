package model.Database;

import model.*;
import model.OrderLog.Order;
import model.People.Account;
import model.Requests.Request;

import java.io.IOException;

public class RuntimeDaraSaver implements Runnable {
    Serialize serialize = new Serialize();

    @Override
    public void run() {
        saveAll();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            System.out.println("Ah shit!");
        }
    }

    private void saveAll() {
        saveAllAccount();
        saveAllProducts();
        saveAllAuctions();
        saveAllCategories();
        saveAllItemOfOrder();
        saveAllComments();
        saveAllAttributes();
        saveAllOrders();
        saveAllRates();
        saveAllRequests();
        saveAllOffCodes();
    }

    private void saveAllAccount() {
        var allAccounts = Account.getAllAccounts();
        for (Account account : allAccounts) {
            try {
                serialize.serializeAccount(account);
            } catch (IOException e) {
                System.out.println("OOPS");
            }
        }

    }

    private void saveAllProducts() {
        var allProducts = Product.getAllProducts();
        for (Product product : allProducts) {
            try {
                serialize.serializeProduct(product);
            } catch (IOException e) {
                System.out.println("OOPS");
            }
        }
    }

    private void saveAllAuctions() {
        var allAuctions = Auction.getAllAuctions();
        for (Auction auction : allAuctions) {
            try {
                serialize.serializeAuction(auction);
            } catch (IOException e) {
                System.out.println("OOPS");
            }
        }

    }

    private void saveAllOffCodes() {
        var allOffCodes = OffCode.getAllOffCodes();
        for (OffCode offCode : allOffCodes) {
            try {
                serialize.serializeOffCode(offCode);
            } catch (IOException e) {
                System.out.println("OOPS");
            }
        }
    }

    private void saveAllItemOfOrder() {
        var allItemOfOrders = ItemOfOrder.allItemOfOrders;
        for (ItemOfOrder itemOfOrder : allItemOfOrders) {
            try {
                serialize.serializeItemOfOrder(itemOfOrder);
            } catch (IOException e) {
                System.out.println("OOPS");
            }
        }
    }

    private void saveAllOrders() {
        var allOrders = Order.getAllOrders();
        for (Order order : allOrders) {
            try {
                serialize.serializeOrder(order);
            } catch (IOException e) {
                System.out.println("OOPS");
            }
        }
    }

    private void saveAllComments() {
        var allComments = Comment.allComments;
        for (Comment comment : allComments) {
            try {
                serialize.serializeComment(comment);
            } catch (IOException e) {
                System.out.println("OOPS");
            }
        }
    }

    private void saveAllRates() {
        var allRates = Rate.allRates;
        for (Rate rate : allRates) {
            try {
                serialize.serializeRate(rate);
            } catch (IOException e) {
                System.out.println("OOPS");
            }
        }
    }

    private void saveAllCategories() {
        var allCategories = Category.getAllCategories();
        for (Category category : allCategories) {
            try {
                serialize.serializeCategory(category);
            } catch (IOException e) {
                System.out.println("OOPS");
            }
        }
    }

    private void saveAllAttributes() {
        var allAttributes = Attributes.allAttributes;
        for (Attributes attribute : allAttributes) {
            try {
                serialize.serializeAttributes(attribute);
            } catch (IOException e) {
                System.out.println("OOPS");
            }
        }
    }

    private void saveAllRequests() {
        var allRequests = Request.getAllRequests();
        for (Request request : allRequests) {
            try {
                serialize.serializeRequest(request);
            } catch (IOException e) {
                System.out.println("OOPS");
            }
        }
    }

}
