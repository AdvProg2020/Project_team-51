package view;

public class AuctionMenu extends Menu{

    public AuctionMenu(Menu parentMenu) {
        super("Auction Menu", parentMenu);
    }


    @Override
    public void showMenu() {
        System.out.println("1. Offs");
        System.out.println("1. Show Product [PID]");
    }

    @Override
    public void executeMenu() {

    }

    public void auctionPage() {

    }

    private void showOffs() {

    }

    private void showProductById(String id) {

    }
}
