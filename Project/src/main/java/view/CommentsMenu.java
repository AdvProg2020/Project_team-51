package view;

import control.Controller;
import control.Exceptions.NotAllowedActivityException;
import control.SingleProductController;

public class CommentsMenu extends Menu {

    private SingleProductController singleProductController;

    public CommentsMenu(Menu parentMenu, SingleProductController singleProductController) {
        super("Comment Menu", parentMenu);
        subMenus.put(1, new Menu("Add Comment Menu", this) {
            @Override
            public void executeMenu() {
                addComment();
            }
        });
        this.singleProductController = singleProductController;
    }

    @Override
    public void showMenu() {
        System.out.println("1. Add Comment");
        System.out.println("2. Back");
        if (Controller.getCurrentAccount() == null)
            System.out.println("3. Login");
        else
            System.out.println("3. Logout");
    }

    @Override
    public void executeMenu() {
        int size = subMenus.size();
        int option = getOptionWithRange(1, size);

        if (option <= size) {
            var nextMenu = subMenus.get(option);
            nextMenu.showMenu();
            nextMenu.executeMenu();
        } else if (option == size + 1) {
            back();
        } else if (option == size + 2) {
            if (Controller.getCurrentAccount() == null) {
                var login = subMenus.get(option);
                login.showMenu();
                login.executeMenu();
            } else {
                logout();
                var mainMenu = new MainMenu();
                mainMenu.showMenu();
                mainMenu.executeMenu();
            }
        }
    }

    private void addComment() {
        if (Controller.getCurrentAccount() == null) {
            var login = new LoginMenu(this);
            login.showMenu();
            login.executeMenu();
            return;
        }
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
