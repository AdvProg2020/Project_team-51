package message.Messages.ClientToServer.ClientToServer;

import model.ItemOfOrder;

import java.util.ArrayList;
import java.util.List;

public class PayCartViaWalletMessage {

    private List<ItemOfOrder> cart = new ArrayList<>();

    public PayCartViaWalletMessage(List<ItemOfOrder> cart) {
        this.cart = cart;
    }

    public List<ItemOfOrder> getCart() {
        return cart;
    }
}
