package message.Messages.ServerToClient;

import model.ItemOfOrder;

public class GetItemOfOrderMessage {

    private ItemOfOrder itemOfOrder;

    public GetItemOfOrderMessage(ItemOfOrder itemOfOrder) {
        this.itemOfOrder = itemOfOrder;
    }

    public ItemOfOrder getItemOfOrder() {
        return itemOfOrder;
    }
}
