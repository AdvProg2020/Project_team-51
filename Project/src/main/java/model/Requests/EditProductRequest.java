package model.Requests;

import model.People.Seller;
import model.Product;
import model.Status;

public class EditProductRequest extends Request {

    private Product product;
    private Seller seller;
    private String field;
    private String value;

    public EditProductRequest(Product product, Seller seller, String field, String value) {
        super("edit");
        this.product = product;
        this.seller = seller;
        this.field = field;
        this.value = value;
    }

    @Override
    public String toString() {
        return "" + getRequestId() + " Edit Product Request : " +
                "Product : " + product.getName() +
                "\nSeller : " + seller.getUsername() +
                "\nField : " + field + '\'' +
                "\nEdited Value : " + value
                ;
    }

    @Override
    public void accept() {

        switch (field) {
            case "name":
                product.setName(value);
                break;
            case "brand":
                product.setBrandName(value);
                break;
            case "description":
                product.setDescription(value);
                break;
            case "price":
                product.setPrice(Double.parseDouble(value), seller);
                break;
            case "quantity":
                product.setQuantity(Integer.parseInt(value), seller);
                break;
        }

        status = Status.APPROVED;
    }
}
