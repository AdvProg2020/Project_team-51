package message.Messages.ClientToServer;

public class GetProductsListByCategoryNameMessage {

    String categoryName;

    public GetProductsListByCategoryNameMessage(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
