package model.People;

import model.ItemOfOrder;
import model.OrderLog.Order;

import java.util.ArrayList;

public class Customer extends Account{

    private ArrayList<ItemOfOrder> cart = new ArrayList<ItemOfOrder>();
    private ArrayList<Order> historyOfOrders = new ArrayList<Order>();


    public Customer(String username, String firstName, String lastName, Double balance, String email, String phoneNumber) {
        super(username, firstName, lastName, balance, email, phoneNumber);
    }
}
