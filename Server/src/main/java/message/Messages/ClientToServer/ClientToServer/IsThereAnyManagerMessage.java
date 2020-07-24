package message.Messages.ClientToServer.ClientToServer;

public class IsThereAnyManagerMessage {

    private boolean isThereAnyManager;

    public IsThereAnyManagerMessage(boolean isThere) {
        this.isThereAnyManager = isThere;
    }

    public boolean isThereAnyManager() {
        return isThereAnyManager;
    }

    public void setThereAnyManager(boolean thereAnyManager) {
        isThereAnyManager = thereAnyManager;
    }
}
