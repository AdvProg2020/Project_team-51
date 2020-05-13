package model.Requests;

import model.People.Seller;
import model.Product;
import model.StatusStates;

public class EditProductRequest extends Request {

    private Product product ;
    private Seller seller;
    private String field;
    private String value;

    public EditProductRequest(String requestId ,Product product, Seller seller, String field , String value ) {
        super(requestId,"edit");
        this.product = product ;
        this.seller = seller ;
        this.field = field ;
        this.value = value ;
    }

    @Override
    public String toString() {
        return "Edit Product Request : " +
                "Product : " + product.getName() +
                "\nSeller : " + seller.getUsername() +
                "\nField : " + field + '\'' +
                "\nEdited Value : " + value
                ;
    }

    @Override
    public void accept() {

        if (field.equals("name")){
            product.setName(value);
        } else if (field.equals("brand")){
            product.setBrandName(value);
        } else if (field.equals("description")){
            product.setDescription(value);
        } else if (field.equals("price")){
            product.setPrice(Double.parseDouble(value));
        } else if (field.equals("quantity")) {
            product.setQuantity(Integer.parseInt(value));
        }

        status.setState(StatusStates.APPROVED);
    }
}
