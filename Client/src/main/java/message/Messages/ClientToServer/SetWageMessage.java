package message.Messages.ClientToServer;

public class SetWageMessage {

    private int wage;

    public SetWageMessage(int wage) {
        this.wage = wage;
    }

    public int getWage() {
        return wage;
    }

}
