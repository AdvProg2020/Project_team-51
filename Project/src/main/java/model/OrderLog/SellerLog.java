package model.OrderLog;

import model.ItemOfOrder;
import model.People.Customer;

import java.util.ArrayList;

public class SellerLog extends Order {
    private Customer buyer;
    private ArrayList<ItemOfOrder> items = new ArrayList<ItemOfOrder>();

    public SellerLog(Customer buyer) {
        this.buyer = buyer;
    }

    public ArrayList<ItemOfOrder> getItems() {
        return items;
    }

    public void addItem(ItemOfOrder item) {
        this.items.add(item);

    }


    public Customer getBuyer() {
        return buyer;
    }
}
