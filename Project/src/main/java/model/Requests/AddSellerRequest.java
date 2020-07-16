package model.Requests;

import model.People.Account;
import model.People.Seller;
import model.Status;

public class AddSellerRequest extends Request {

    private Seller seller;

    public AddSellerRequest(Seller seller) {
        super("add");
        this.seller = seller;
    }

    @Override
    public String toString() {
        return "" + getRequestId() + " Seller Register Request{" +
                "Name : " + seller.getFirstName() + " " + seller.getLastName() + "\n" +
                "Username : " + seller.getUserName() + "\n" +
                "Brand : " + seller.getBrandName();
    }

    @Override
    public void accept() {
        Account.addSeller(seller);
        status = (Status.APPROVED);
        seller.setStatus(Status.APPROVED);
    }
}
