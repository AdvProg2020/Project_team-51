package message.Messages.ServerToClient;

import model.Category;

import java.util.ArrayList;
import java.util.List;

public class UpdateCategoriesMessage {

    List<Category> categories = new ArrayList<>();

    public UpdateCategoriesMessage(List<Category> categories) {
        this.categories = categories;
    }

    public List<Category> getCategories() {
        return categories;
    }
}
