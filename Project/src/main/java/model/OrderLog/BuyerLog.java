package model.OrderLog;

import model.ItemOfOrder;
import model.People.Customer;

import java.util.ArrayList;

public class BuyerLog extends Order{
    private Customer seller;
    private ArrayList<ItemOfOrder> items = new ArrayList<ItemOfOrder>();

    public BuyerLog(Customer seller) {
        super();
        this.seller = seller;
    }

    public ArrayList<ItemOfOrder> getItems() {
        return items;
    }

    public void addItem(ItemOfOrder item){
        this.items.add(item);

    }

    public Customer getSeller() {
        return seller;
    }
}
