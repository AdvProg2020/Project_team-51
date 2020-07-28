package message.Messages.ServerToClient;

import model.People.Service;

import java.util.LinkedList;
import java.util.List;

public class OnlineServicesMessage {

    List<Service> services = new LinkedList<>();

    public OnlineServicesMessage(List<Service> services) {
        this.services = services;
    }

    public List<Service> getServices() {
        return services;
    }
}
