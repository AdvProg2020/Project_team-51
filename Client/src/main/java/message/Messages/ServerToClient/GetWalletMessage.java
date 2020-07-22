package message.Messages.ServerToClient;

import model.Wallet;

public class GetWalletMessage {

    private Wallet wallet;

    public GetWalletMessage(Wallet wallet) {
        this.wallet = wallet;
    }

    public Wallet getWallet() {
        return wallet;
    }
}
