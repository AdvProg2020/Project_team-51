package message.requests;

import model.Requests.AddItemRequest;

public class CreateAddItemRequestMessage {

    private AddItemRequest addItemRequest;

    public CreateAddItemRequestMessage(AddItemRequest addItemRequest) {
        this.addItemRequest = addItemRequest;
    }

    public AddItemRequest getAddItemRequest() {
        return addItemRequest;
    }
}
