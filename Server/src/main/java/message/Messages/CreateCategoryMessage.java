package message.Messages;

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
