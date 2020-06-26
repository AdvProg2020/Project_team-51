package main.Products;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import main.Main;

import java.io.IOException;

public class SingleProduct extends AnchorPane {

    @FXML
    private VBox productsBox;

    @FXML
    private GridPane productBox;

    @FXML
    private Rectangle productRectangle;

    @FXML
    private HBox productHBox;

    @FXML
    private ImageView productImage;

    @FXML
    private JFXButton productName;

    @FXML
    private Label productDescription;

    @FXML
    private HBox offIcon;

    @FXML
    private Label productPrice;

    @FXML
    private Label offPercent;

    @FXML
    private ImageView rateImage;

    @FXML
    private ImageView price;

    @FXML
    private ImageView off;


    public SingleProduct() {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("singleproduct.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        rateImage.setImage(new Image(String.valueOf(Main.class.getResource("star.png"))));
        off.setImage(new Image(String.valueOf(Main.class.getResource("discount.png"))));
        price.setImage(new Image(String.valueOf(Main.class.getResource("price.png"))));

    }

    private static void playAudio(String musicFile) {
//        AudioClip audioClip = new AudioClip(String.valueOf(Main.class.getResource(musicFile)));
//        audioClip.setCycleCount(Integer.MAX_VALUE);
//        audioClip.play();
    }

    public VBox getProductsBox() {
        return productsBox;
    }

    public void setProductsBox(VBox productsBox) {
        this.productsBox = productsBox;
    }

    public GridPane getProductBox() {
        return productBox;
    }

    public void setProductBox(GridPane productBox) {
        this.productBox = productBox;
    }

    public Rectangle getProductRectangle() {
        return productRectangle;
    }

    public void setProductRectangle(Rectangle productRectangle) {
        this.productRectangle = productRectangle;
    }

    public HBox getProductHBox() {
        return productHBox;
    }

    public void setProductHBox(HBox productHBox) {
        this.productHBox = productHBox;
    }

    public ImageView getProductImage() {
        return productImage;
    }

    public void setProductImage(ImageView productImage) {
        this.productImage = productImage;
    }

    public JFXButton getProductName() {
        return productName;
    }

    public void setProductName(JFXButton productName) {
        this.productName = productName;
    }

    public ImageView getRateImage() {
        return rateImage;
    }

    public void setRateImage(ImageView rateImage) {
        this.rateImage = rateImage;
    }

    public ImageView getPrice() {
        return price;
    }

    public void setPrice(ImageView price) {
        this.price = price;
    }

    public ImageView getOff() {
        return off;
    }

    public void setOff(ImageView off) {
        this.off = off;
    }

    public Label getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(Label productDescription) {
        this.productDescription = productDescription;
    }

    public HBox getOffIcon() {
        return offIcon;
    }

    public void setOffIcon(HBox offIcon) {
        this.offIcon = offIcon;
    }

    public Label getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Label productPrice) {
        this.productPrice = productPrice;
    }

    public Label getOffPercent() {
        return offPercent;
    }

    public void setOffPercent(Label offPercent) {
        this.offPercent = offPercent;
    }
}
