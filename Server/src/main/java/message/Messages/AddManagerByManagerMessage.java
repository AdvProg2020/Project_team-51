package message.Messages;

import model.People.Manager;

public class AddManagerByManagerMessage {

    private Manager manager;

    public AddManagerByManagerMessage(Manager manager) {
        this.manager = manager;
    }

    public Manager getManager() {
        return manager;
    }
}
