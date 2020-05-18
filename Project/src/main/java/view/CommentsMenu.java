package view;

import control.Exceptions.NotAllowedActivityException;
import control.SingleProductController;
import view.Enums.AllCommands;
import view.Enums.MenusPattern;

public class CommentsMenu extends Menu {

    private SingleProductController singleProductController;

    public CommentsMenu(Menu parentMenu, SingleProductController singleProductController) {
        super("Comment Menu", parentMenu);
        this.singleProductController = singleProductController;
    }

    @Override
    public void showMenu() {
        System.out.println("1. Add Comment");
        System.out.println("2. Back");
    }

    @Override
    public void executeMenu() {
        String command = inputInFormat("Please Enter A valid Command", MenusPattern.ADD_COMMENT.getRegex());
        if (command.matches(AllCommands.ADD_COMMENT.getRegex())) {
            addComment();
        } else {
            back();
        }
    }

    private void addComment() {
        try {
            singleProductController.addComment(getCommentTitle(), getCommentContent());
        } catch (NotAllowedActivityException e) {
            System.out.println(e.getMessage());
        }
    }

    private String getCommentTitle() {
        return inputInFormat("Title : ", "w+");
    }

    private String getCommentContent() {
        return inputInFormat("Content : ", "w+");
    }
}
