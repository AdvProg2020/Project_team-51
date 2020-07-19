package message.requests;

public class TakeFromWalletMessage {

    private double amount;

    public TakeFromWalletMessage(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }
}
