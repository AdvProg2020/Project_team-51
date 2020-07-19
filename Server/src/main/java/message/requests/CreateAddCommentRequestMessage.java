package message.requests;

import model.Comment;

public class CreateAddCommentRequestMessage {

    private Comment comment;

    public CreateAddCommentRequestMessage(Comment comment) {
        this.comment = comment;
    }

    public Comment getComment() {
        return comment;
    }
}
