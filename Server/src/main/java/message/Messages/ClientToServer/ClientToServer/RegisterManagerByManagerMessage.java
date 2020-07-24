package message.Messages.ClientToServer.ClientToServer;

import model.People.Manager;

public class RegisterManagerByManagerMessage {

    private Manager manager;

    public RegisterManagerByManagerMessage(Manager manager) {
        this.manager = manager;
    }

    public Manager getManager() {
        return manager;
    }
}
