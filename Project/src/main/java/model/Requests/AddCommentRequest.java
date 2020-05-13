package model.Requests;

import model.Comment;
import model.Status;
import model.StatusStates;

public class AddCommentRequest extends Request{

    private Comment comment ;

    public AddCommentRequest(String requestId , Comment comment) {
        super(requestId, "add");
        this.comment = comment ;
    }

    @Override
    public String toString() {
        return "Add Comment Request : " +
                "\nSender : " + comment.getAccount().getUsername() + "\n" +
                "comment :\n" + comment.getContext()
                ;
    }

    @Override
    public void accept() {
        comment.getProduct().addComment(comment);
        this.status.setState(StatusStates.APPROVED);
        comment.setCommentStatus(new Status(StatusStates.APPROVED));
    }
}