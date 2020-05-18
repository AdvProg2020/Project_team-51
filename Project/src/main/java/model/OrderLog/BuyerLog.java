package model.OrderLog;

import model.ItemOfOrder;
import model.People.Seller;

import java.util.ArrayList;

public class BuyerLog extends Order {
    private Seller seller;
    private ArrayList<ItemOfOrder> items = new ArrayList<ItemOfOrder>();

    public BuyerLog(Seller seller) {
        super();
        this.seller = seller;
    }

    public ArrayList<ItemOfOrder> getItems() {
        return items;
    }

    public void addItem(ItemOfOrder item) {
        this.items.add(item);

    }

    public Seller getSeller() {
        return seller;
    }
}
