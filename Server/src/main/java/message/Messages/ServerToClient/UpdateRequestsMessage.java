package message.Messages.ServerToClient;

import model.Requests.Request;

import java.util.ArrayList;
import java.util.List;

public class UpdateRequestsMessage {

    List<Request> requests = new ArrayList<>();

    public UpdateRequestsMessage(List<Request> requests) {
        this.requests = requests;
    }

    public List<Request> getRequests() {
        return requests;
    }
}
