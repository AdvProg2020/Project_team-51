package control;

import model.Auction;
import model.People.Account;
import model.Product;

import java.util.ArrayList;

public class AuctionController extends Controller {

    public AuctionController(Account currentAccount) {
        super(currentAccount);
    }

    // returns auction id s
    public static ArrayList<String> showOffs() {
        ArrayList<String> auctions = new ArrayList<>();
        for (Auction auction : Auction.getAllAuctions()) {
            auctions.add(auction.getAuctionId());
        }
        return auctions;
    }

    public static Product goToProduct(String productId) {

        for (Product product : Product.getAllProducts()) {
            if (product.getProductId().equals(productId)) return product;
        }
        return null;
    }
}