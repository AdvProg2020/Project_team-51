package model.OrderLog;

import java.util.ArrayList;

public abstract class Order {
  public static ArrayList<Order> allOrders = new ArrayList<>();
  protected String orderID ;
  protected ShippingStatus status ;

    public Order(String orderID) {
        this.orderID = orderID;
        allOrders.add(this);
        status = ShippingStatus.SENT;
    }

    public Order() {

    }

  public String getOrderID() {
    return orderID;
  }

  public static void addOrder(Order order){
      allOrders.add(order);
  }
}
