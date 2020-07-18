package model.OrderLog;

import model.ItemOfOrder;

import java.util.ArrayList;
import java.util.List;

public class BuyerLog extends Order {

    private List<ItemOfOrder> items = new ArrayList<>();

    public BuyerLog(List<ItemOfOrder> items) {
        super();
        this.items = items;
    }

    @Override
    public List<ItemOfOrder> getItems() {
        return items;
    }

    public void addItem(ItemOfOrder item) {
        this.items.add(item);

    }

    @Override
    public String toString() {
        return
                "orderID : " + orderID + '\'' +
                        "status : " + status +
                        "items : " + "\n" + items.stream().map(ItemOfOrder::toString)
                ;
    }
}
