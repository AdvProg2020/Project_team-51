package message.Messages;

import model.ItemOfOrder;

import java.util.ArrayList;
import java.util.List;

public class PayCartViaBankMessage {

    private List<ItemOfOrder> cart = new ArrayList<>();

    public PayCartViaBankMessage(List<ItemOfOrder> cart) {
        this.cart = cart;
    }

    public List<ItemOfOrder> getCart() {
        return cart;
    }


}
