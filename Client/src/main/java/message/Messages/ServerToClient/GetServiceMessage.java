package message.Messages.ServerToClient;

import model.People.Service;

public class GetServiceMessage {

    private Service service;

    public GetServiceMessage(Service service) {
        this.service = service;
    }

    public Service getService() {
        return service;
    }
}
