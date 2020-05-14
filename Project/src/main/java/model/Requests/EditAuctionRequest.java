package model.Requests;

import control.Exceptions.InvalidProductIdException;
import model.Auction;
import model.Product;
import model.StatusStates;

import java.sql.Date;
import java.time.LocalDate;

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

        if (field.equals("begin date")){
            auction.setBeginDate(Date.valueOf(value));
        } else if (field.equals("end date")){
            auction.setEndDate(Date.valueOf(value));
        } else if (field.equals("off percentage")){
            auction.setOffPercentage(Integer.parseInt(value));
        } else if (field.equals("add product")){
            auction.addAppliedProduct(Product.getProductById(value));
        } else if (field.equals("remove product")) {
            auction.removeAppliedProduct(Product.getProductById(value));
        }

        status.setState(StatusStates.APPROVED);
    }
}
