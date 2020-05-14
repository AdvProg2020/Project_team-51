package model.Requests;

import control.Exceptions.InvalidProductIdException;
import model.Auction;
import model.Product;
import model.StatusStates;

import java.sql.Date;

public class EditAuctionRequest extends Request{
    private Auction auction;
    private String field;
    private String value;

    public EditAuctionRequest(String requestId , Auction auction , String field , String value) {
        super(requestId, "edit");
        this.auction = auction;
        this.field = field;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Edit Auction Request : " +
                "\nAuctionId" + auction.getAuctionId() +
                "\nField : " + field +
                "\nValue : " + value ;
    }

    @Override
    public void accept() throws InvalidProductIdException {

        switch (field) {
            case "begin date":
                auction.setBeginDate(Date.valueOf(value));
                break;
            case "end date":
                auction.setEndDate(Date.valueOf(value));
                break;
            case "off percentage":
                auction.setOffPercentage(Integer.parseInt(value));
                break;
            case "add product":
                auction.addAppliedProduct(Product.getProductById(value));
                break;
            case "remove product":
                auction.removeAppliedProduct(Product.getProductById(value));
                break;
        }

        status.setState(StatusStates.APPROVED);
    }
}
