package message.Messages.ClientToServer;

public class BuyFileMessage {

    private double price;

    public BuyFileMessage(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}
