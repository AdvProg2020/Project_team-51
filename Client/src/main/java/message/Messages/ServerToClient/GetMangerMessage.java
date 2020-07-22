package message.Messages.ServerToClient;

import model.People.Manager;

public class GetMangerMessage {

    private Manager manager;

    public GetMangerMessage(Manager manager) {
        this.manager = manager;
    }

    public Manager getManager() {
        return manager;
    }
}
