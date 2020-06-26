package model.OrderLog;

import model.ItemOfOrder;
import model.People.Customer;

import java.util.ArrayList;
import java.util.List;

public class SellerLog extends Order {
    private Customer buyer;
    private List<ItemOfOrder> items = new ArrayList<>();

    public SellerLog(Customer buyer) {
        super();
        this.buyer = buyer;
    }

    @Override
    public List<ItemOfOrder> getItems() {
        return items;
    }

    public void addItem(ItemOfOrder item) {
        this.items.add(item);

    }

    public String getBuyerName() {
        return buyer.getFirstName() + buyer.getLastName();
    }

    public Customer getBuyer() {
        return buyer;
    }
}
