package message.Messages;

import model.Requests.AddItemRequest;

public class AcceptAddItemRequestMessage {

    private AddItemRequest addItemRequest;

    public AcceptAddItemRequestMessage(AddItemRequest addItemRequest) {
        this.addItemRequest = addItemRequest;
    }

    public AddItemRequest getAddItemRequest() {
        return addItemRequest;
    }
}
