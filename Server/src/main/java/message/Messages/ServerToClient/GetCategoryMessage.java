package message.Messages.ServerToClient;

import model.Category;

public class GetCategoryMessage {

    private Category category;

    public GetCategoryMessage(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

}
