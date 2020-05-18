package model.People;

import model.ItemOfOrder;
import model.OrderLog.Order;

import java.util.ArrayList;
import java.util.List;

public class Customer extends Account {

    private List<ItemOfOrder> cart = new ArrayList<ItemOfOrder>();
    private List<Order> historyOfOrders = new ArrayList<Order>();


    public Customer(String username, String password, String firstName, String lastName, Double balance, String email, String phoneNumber) {
        super(username, password, firstName, lastName, balance, email, phoneNumber);
    }

    public static void addCustomer(Customer customer) {
        allAccounts.add(customer);
    }

    public List<Order> getHistoryOfOrders() {
        return historyOfOrders;
    }

    public List<ItemOfOrder> getCart() {
        return cart;
    }

    public void setCart(List<ItemOfOrder> cart) {
        this.cart = cart;
    }
}
