package model.Requests;

import model.People.Seller;
import model.Product;
import model.Status;
import model.StatusStates;

public class AddItemRequest extends Request {

    private Product product ;
    private Seller seller ;

    public AddItemRequest(String requestId, Product product, Seller seller) {
        super(requestId, "add");
        this.product = product ;
        this.seller = seller ;
    }

    @Override
    public String toString() {
        return "Add Item Request :" +
                "Product : " + product.getName() +
                "\nSeller : " + seller.getUsername() + " | " + seller.getBrandName()
                ;
    }

    @Override
    public void accept() {

        var product = Product.getProductById(this.product.getProductId()) ;

        if (product != null)
            this.product.addSellerForThisProduct(seller);

        seller.addAvailableProduct(this.product);
        status.setState(StatusStates.APPROVED);
        this.product.setStatus(new Status(StatusStates.APPROVED));

    }
}