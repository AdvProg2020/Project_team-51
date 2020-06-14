package main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import control.Controller;
import control.Exceptions.InvalidUsernameException;
import control.SingleProductController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model.Comment;
import model.People.Seller;
import model.Product;

import java.util.List;
import java.util.Map;

public class ProductPageController {


    @FXML
    private JFXButton dashboard;

    @FXML
    private JFXButton logout;

    @FXML
    private JFXButton homeButton;

    @FXML
    private MenuButton categoriesButton;

    @FXML
    private JFXButton cartButton;

    @FXML
    private ImageView productImage;

    @FXML
    private Label productName;

    @FXML
    private Label productDescription;

    @FXML
    private ComboBox<Seller> sellerComboBox;

    @FXML
    private Label productPrice;

    @FXML
    private JFXTextField quantity;

    @FXML
    private JFXButton addButton;

    @FXML
    private VBox commentsBox;

    @FXML
    private Label comment;

    private Product product;
    private List<Comment> allComments;
    private JFXButton addComment;
    private SingleProductController controller;
    private String quantityText;


    public void initialize() {
        controller = new SingleProductController(Controller.getCurrentAccount(), null);
        product = controller.getProduct();
        allComments = controller.getComments();

        initializeProduct();
        initializeComments();

    }


    private void initializeProduct() {
        productImage.setImage(product.getImage());
        productName.setText(product.getName());
        try {
            productDescription.setText(controller.digest());
        } catch (InvalidUsernameException e) {
        }
        productPrice.setText(product.getAveragePrice() + " $");

        var sellers = product.getPrice();
        for (Map.Entry<Seller, Double> seller : sellers.entrySet()) {
            sellerComboBox.getItems().add(seller.getKey());
        }

        sellerComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Seller>() {
            @Override
            public void changed(ObservableValue<? extends Seller> observable, Seller oldValue, Seller newValue) {
                productPrice.setText(newValue.getBrandName());
            }
        });
        quantity.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                quantityText = newValue;
            }
        });
    }

    private void initializeComments() {

        for (Comment comment : allComments) {
            CommentController commentController = new CommentController();
            commentsBox.getChildren().add(commentController);
            commentController.getTitle().setText(comment.getTitle());
            commentController.getComment().setText(comment.getContext());
//            commentController.getRate().setImage("STH");
        }

        commentsBox.getChildren().add(addButton);
        addButton.setText("Add");
        addButton.setStyle("-fx-background-color: #4a804a; -fx-background-radius: 15");

    }
}
