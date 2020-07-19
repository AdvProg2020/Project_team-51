package message.Messages;

import model.ItemOfOrder;

public class IncrementProductQuantityMessage {

    private ItemOfOrder itemOfOrder;
    private int number;

    public IncrementProductQuantityMessage(ItemOfOrder itemOfOrder, int number) {
        this.itemOfOrder = itemOfOrder;
        this.number = number;
    }

    public ItemOfOrder getItemOfOrder() {
        return itemOfOrder;
    }

    public int getNumber() {
        return number;
    }
}
