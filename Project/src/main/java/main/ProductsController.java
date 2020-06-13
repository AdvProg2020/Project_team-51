package main;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ProductsController {

    @FXML
    private VBox productsBox;

    @FXML
    private VBox filterBox;


    @FXML
    public void initialize() {
//        GridPane sample = productBox;
//        productsBox.getChildren().add(sample);

        for (int i = 0; i < 10; i++) {
            SingleProduct singleProduct = new SingleProduct();
            singleProduct.getProductPrice().setText("777 $");
            productsBox.getChildren().add(singleProduct);
        }

        HBox pageNumberBox = new PageNumberHBox().getPageNumberBox();

        for (int i = 0; i < 4; i++) {
            PageNumber pageNumber = new PageNumber();
            pageNumber.getButton().setText(String.valueOf(i + 1));
            pageNumberBox.getChildren().add(pageNumber);
        }

        for (int i = 0; i < 3; i++) {
            FilterField filterField = new FilterField();
            filterField.accessibleTextProperty().setValue("this is it");
            filterBox.getChildren().add(filterField);
        }

        productsBox.getChildren().add(pageNumberBox);


    }


}
