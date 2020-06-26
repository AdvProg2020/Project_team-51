package model.OrderLog;

import control.TokenGenerator;
import model.ItemOfOrder;
import model.Status;

import java.util.ArrayList;
import java.util.List;

public abstract class Order {
    public static ArrayList<Order> allOrders = new ArrayList<>();
    protected String orderID;
    protected ShippingStatus status;
    private List<ItemOfOrder> items = new ArrayList<>();

    public Order() {
        this.orderID = TokenGenerator.generateOrderId();
        allOrders.add(this);
        status = ShippingStatus.SENT;
    }

    public static void addOrder(Order order) {
        allOrders.add(order);
    }

    public static ArrayList<Order> getAllOrders() {
        return allOrders;
    }

    public static Order getOrderById(String orderID) {
        for (Order order : allOrders) {
            if (order.getOrderID().equals(orderID))
                return order;
        }

        return null;
    }

    public String getOrderID() {
        return orderID;
    }

    public List<ItemOfOrder> getItems() {
        return items;
    }

    public ShippingStatus getStatus() {
        return status;
    }

    public String getShippingStatusString(){
        if (status.equals( Status.APPROVED))return "approved";
        else if (status.equals(Status.ENDED)) return "ended";
        else if (status.equals(Status.PENDING_CREATE)) return "pending create";
        else return "peinding edit";
    }
}
