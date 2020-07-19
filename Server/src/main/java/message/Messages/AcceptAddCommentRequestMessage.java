package message.Messages;

import model.Requests.AddCommentRequest;

public class AcceptAddCommentRequestMessage {

    private AddCommentRequest addCommentRequest;

    public AcceptAddCommentRequestMessage(AddCommentRequest addCommentRequest) {
        this.addCommentRequest = addCommentRequest;
    }

    public AddCommentRequest getAddCommentRequest() {
        return addCommentRequest;
    }

}
