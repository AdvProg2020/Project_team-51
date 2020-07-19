package message.requests;

import model.ItemOfOrder;

import java.util.ArrayList;
import java.util.List;

public class PayCartMessage {

    private List<ItemOfOrder> cart = new ArrayList<>();

    public PayCartMessage(List<ItemOfOrder> cart) {
        this.cart = cart;
    }

    public List<ItemOfOrder> getCart() {
        return cart;
    }
}
