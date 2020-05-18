package model.OrderLog;

import control.TokenGenerator;
import model.ItemOfOrder;

import java.util.ArrayList;
import java.util.List;

public abstract class Order {
    public static ArrayList<Order> allOrders = new ArrayList<>();
    private List<ItemOfOrder> items = new ArrayList<>();
    protected String orderID;
    protected ShippingStatus status;

    public Order() {
        this.orderID = TokenGenerator.generateOrderId();
        allOrders.add(this);
        status = ShippingStatus.SENT;
    }

    public static void addOrder(Order order) {
        allOrders.add(order);
    }

    public String getOrderID() {
        return orderID;
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

    public List<ItemOfOrder> getItems() {
        return items;
    }

    public ShippingStatus getStatus() {
        return status;
    }
}
