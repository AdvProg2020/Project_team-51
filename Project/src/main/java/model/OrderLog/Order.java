package model.OrderLog;

import model.Status;

import java.util.ArrayList;
import java.util.Date;

public abstract class Order {
  public static ArrayList<Order> allOrders = new ArrayList<>();
  protected String orderID ;
  protected Status status ;

    public Order(String orderID) {
        this.orderID = orderID;
        allOrders.add(this);
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
