package message.Messages.ClientToServer;

public class GetProductsListBySearchMessage {

    String search;

    public GetProductsListBySearchMessage(String search) {
        this.search = search;
    }

    public String getSearch() {
        return search;
    }
}
