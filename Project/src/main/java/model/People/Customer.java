package model.People;

import model.ItemOfOrder;
import model.OrderLog.Order;

import java.util.ArrayList;

public class Customer extends Account{

    private ArrayList<ItemOfOrder> cart = new ArrayList<ItemOfOrder>();
    private ArrayList<Order> historyOfOrders = new ArrayList<Order>();


    public Customer(String username,String password , String firstName, String lastName, Double balance, String email, String phoneNumber) {
        super(username,password, firstName, lastName, balance, email, phoneNumber);
    }

    public ArrayList<Order> getHistoryOfOrders() {
        return historyOfOrders;
    }

    public ArrayList<ItemOfOrder> getCart() {
        return cart;
    }

    public void setCart(ArrayList<ItemOfOrder> cart) {
        this.cart = cart;
    }

    public static void addCustomer(Customer customer){
        allAccounts.add(customer);
    }
}
