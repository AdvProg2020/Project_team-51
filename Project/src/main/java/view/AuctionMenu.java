package view;

import control.Exceptions.InvalidProductIdException;
import model.Attributes;
import model.Auction;
import model.People.Manager;
import model.Product;
import model.Rate;

import java.util.HashMap;

public class AuctionMenu extends Menu{

    public AuctionMenu(Menu parentMenu) {
        super("Auction Menu", parentMenu);
    }

    @Override
    public void executeMenu() {



    }

    public void auctionPage() {

    }

    private void showOffs() {
    }
// attributes are not shown
    private void showProductById(String id) throws InvalidProductIdException {
        try {
            Product product = Product.getProductById(id);
            float score=0;
            for (Rate rate : product.getRating()){
                score += rate.getScore();
            }
            score /= product.getRating().size();
            System.out.println("PRODUCT NAME : "+product.getName()+"\n"+
                    "BRAND : "+product.getBrandName()+"\n"+
                    "DESCRIPTION : " + product.getDescription()+"\n"+
                    "IN STOCK : " +product.getQuantity()+"\n"+
                    "PRICE : " +product.getPrice()+"\n"+
                    "RATE : " + score + "\n"+
             "COMMENTS : " +product.getComments());
            HashMap <Attributes,String> attributes = product.getAttributes();

        }catch (Exception InvalidProductIdException){
            System.out.println("THIS PRODUCT DOESN'T EXIST !");
        }
    }
}
