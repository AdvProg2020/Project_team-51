package model.Requests;

import model.People.Account;
import model.People.Seller;
import model.Status;

public class AddSellerRequest extends Request {

    private Seller seller;

    public AddSellerRequest(String requestId, Seller seller) {
        super(requestId, "add");
        this.seller = seller;
    }

    @Override
    public String toString() {
        return "Seller Register Request{" +
                "Name : " + seller.getFirstName() + " " + seller.getLastName() + "\n" +
                "Username : " + seller.getUsername() + "\n" +
                "Brand : " + seller.getBrandName();
    }

    @Override
    public void accept() {
        Account.addSeller(seller);
        status = (Status.APPROVED);
        seller.setStatus(Status.APPROVED);
    }
}
