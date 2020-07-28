package model.Requests;

import javafx.scene.control.ComboBox;
import model.Comment;
import model.Status;

public class AddCommentRequest extends Request {

    private Comment comment;

    public AddCommentRequest(Comment comment) {
        super("add");
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "" + getRequestId() + " Add Comment Request : " +
                "\nSender : " + comment.getAccount().getUserName() + "\n" +
                "comment :\n" + comment.getContext()
                ;
    }

    @Override
    public void accept() {
        comment.getProduct().addComment(comment);
        this.status = Status.APPROVED;
        comment.setCommentStatus(Status.APPROVED);
    }

    public Comment getComment() {
        return comment;
    }
}
