package message.Messages.ClientToServer;

import model.People.Service;

public class RegisterServiceByManagerMessage {

    private Service service;

    public RegisterServiceByManagerMessage(Service service) {
        this.service = service;
    }

    public Service getService() {
        return service;
    }
}
