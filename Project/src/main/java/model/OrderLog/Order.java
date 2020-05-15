package model.OrderLog;

import model.Status;

import java.util.Date;

public abstract class Order {
  protected String orderID ;
  protected Status status ;

    public Order(String orderID) {
        this.orderID = orderID;
    }

    public Order() {

    }

  public String getOrderID() {
    return orderID;
  }
}
