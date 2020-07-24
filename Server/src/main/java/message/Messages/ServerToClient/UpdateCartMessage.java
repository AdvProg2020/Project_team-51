package message.Messages.ServerToClient;

import model.ItemOfOrder;

import java.util.ArrayList;
import java.util.List;

public class UpdateCartMessage {

    private List<ItemOfOrder> itemOfOrders = new ArrayList<>();

    public UpdateCartMessage(List<ItemOfOrder> itemOfOrders) {
        this.itemOfOrders = itemOfOrders;
    }

    public List<ItemOfOrder> getItemOfOrders() {
        return itemOfOrders;
    }
}
