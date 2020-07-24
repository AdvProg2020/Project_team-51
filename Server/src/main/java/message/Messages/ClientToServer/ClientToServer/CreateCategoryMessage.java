package message.Messages.ClientToServer.ClientToServer;

import model.Category;

public class CreateCategoryMessage {

    private Category category;

    public CreateCategoryMessage(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }
}
