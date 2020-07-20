package message.Messages.ServerToClient;

import model.ItemOfOrder;

import java.util.ArrayList;
import java.util.List;

public class GetCartMessage {

    private List<ItemOfOrder> itemsOfOrder = new ArrayList<>();

    public GetCartMessage(List<ItemOfOrder> itemsOfOrder) {
        this.itemsOfOrder = itemsOfOrder;
    }

    public List<ItemOfOrder> getItemsOfOrder() {
        return itemsOfOrder;
    }

}
