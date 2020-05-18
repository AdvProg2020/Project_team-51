package model.OrderLog;

import java.util.ArrayList;

public abstract class Order {
    public static ArrayList<Order> allOrders = new ArrayList<>();
    protected String orderID;
    protected ShippingStatus status;

    public Order(String orderID) {
        this.orderID = orderID;
        allOrders.add(this);
        status = ShippingStatus.SENT;
    }

    public Order() {

    }

    public static void addOrder(Order order) {
        allOrders.add(order);
    }

    public String getOrderID() {
        return orderID;
    }
}
