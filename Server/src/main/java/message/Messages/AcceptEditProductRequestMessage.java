package message.Messages;

import model.Requests.EditProductRequest;

public class AcceptEditProductRequestMessage {

    private EditProductRequest editProductRequest;

    public AcceptEditProductRequestMessage(EditProductRequest editProductRequest) {
        this.editProductRequest = editProductRequest;
    }

    public EditProductRequest getEditProductRequest() {
        return editProductRequest;
    }
}
