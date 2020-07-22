package message.Messages.ServerToClient;

import model.Rate;

public class GetRateMessage {

    private Rate rate;

    public GetRateMessage(Rate rate) {
        this.rate = rate;
    }

    public Rate getRate() {
        return rate;
    }
}
