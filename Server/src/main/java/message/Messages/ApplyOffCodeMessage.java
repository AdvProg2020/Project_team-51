package message.Messages;

import model.ItemOfOrder;
import model.OffCode;

import java.util.ArrayList;
import java.util.List;

public class ApplyOffCodeMessage {

    private OffCode offCode;
    private List<ItemOfOrder> cart = new ArrayList<>();

    public ApplyOffCodeMessage(OffCode offCode, List<ItemOfOrder> cart) {
        this.offCode = offCode;
        this.cart = cart;
    }

    public OffCode getOffCode() {
        return offCode;
    }

    public List<ItemOfOrder> getCart() {
        return cart;
    }
}
