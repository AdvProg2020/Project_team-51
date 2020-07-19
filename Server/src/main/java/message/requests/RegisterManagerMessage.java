package message.requests;

import model.People.Manager;

public class RegisterManagerMessage {

    private Manager manager;

    public RegisterManagerMessage(Manager manager) {
        this.manager = manager;
    }

    public Manager getManager() {
        return manager;
    }
}
