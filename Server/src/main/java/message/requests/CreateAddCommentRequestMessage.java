package message.requests;

import model.Requests.AddCommentRequest;

public class CreateAddCommentRequestMessage {

    private AddCommentRequest addCommentRequest;

    public CreateAddCommentRequestMessage(AddCommentRequest addCommentRequest) {
        this.addCommentRequest = addCommentRequest;
    }

    public AddCommentRequest getAddCommentRequest() {
        return addCommentRequest;
    }
}
