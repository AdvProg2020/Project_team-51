package main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.events.JFXDialogEvent;
import control.Controller;
import control.Exceptions.InvalidUsernameException;
import control.Exceptions.LackOfProductException;
import control.Exceptions.NotAllowedActivityException;
import control.SingleProductController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
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

    @FXML
    private StackPane rootPane;

    @FXML
    private BorderPane mainPane;


    public void initialize() {
        controller = new SingleProductController(Controller.getCurrentAccount(), null);
        product = controller.getProduct();
        allComments = controller.getComments();

        initializeProduct();
        initializeComments();

        addButton.setOnMouseClicked(e -> {
            try {
                controller.addToCart(sellerComboBox.getSelectionModel().getSelectedItem().getUsername());
            } catch (LackOfProductException ex) {
                showError("Lack Of Products");
            } catch (NotAllowedActivityException ex) {
                showError("You are not allowed to do that!");
            } catch (InvalidUsernameException ex) {
            }
        });
        addComment.setOnMouseClicked(e -> {
            BoxBlur boxBlur = new BoxBlur(6, 6, 6);
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            JFXDialog dialog = new JFXDialog(rootPane, dialogLayout, JFXDialog.DialogTransition.CENTER);
            dialogLayout.setActions(new NewCommentDialog(rootPane, controller));
            dialog.show();
            mainPane.setEffect(boxBlur);
            dialog.setOnDialogClosed((JFXDialogEvent event) -> mainPane.setEffect(null));
            dialog.overlayCloseProperty().bindBidirectional(new SimpleBooleanProperty(!Controller.isLoggedIn()));
        });
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

    private void showError(String message) {
        showErrorWithColor(message, "#fe615a");
    }

    @FXML
    private void showErrorWithColor(String message, String color) {
        BoxBlur boxBlur = new BoxBlur(4, 4, 4);
        JFXButton button = new JFXButton(" OK ");
        button.setStyle("-fx-background-color:" + color + "; -fx-background-radius:  18; -fx-text-fill: white");
        button.setPadding(new Insets(3, 16, 3, 16));
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        dialogLayout.setHeading(new Label(message));
        dialogLayout.setStyle("-fx-background-color: rgba(255,104,110,0.64)");
        JFXDialog dialog = new JFXDialog(rootPane, dialogLayout, JFXDialog.DialogTransition.CENTER);
        button.setOnMouseClicked((MouseEvent event) -> {
            dialog.close();
        });
        dialogLayout.setActions(button);
        dialog.show();
        mainPane.setEffect(boxBlur);
        dialog.setOnDialogClosed((JFXDialogEvent event) -> mainPane.setEffect(null));
    }


    @FXML
    private void showConfirmation(String message) {
        BoxBlur boxBlur = new BoxBlur(4, 4, 4);
        JFXButton button = new JFXButton("OK");
        button.setStyle("-fx-background-color: #6d8040; -fx-background-radius:  18; -fx-text-fill: white");
        button.setPadding(new Insets(3, 16, 3, 16));
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        dialogLayout.setHeading(new Label(message));
        dialogLayout.setStyle("-fx-background-color: rgba(118,160,93,0.64)");
        JFXDialog dialog = new JFXDialog(rootPane, dialogLayout, JFXDialog.DialogTransition.CENTER);
        button.setOnMouseClicked((MouseEvent event) -> {
            dialog.close();
        });
        dialogLayout.setActions(button);
        dialog.show();
        mainPane.setEffect(boxBlur);
        dialog.setOnDialogClosed((JFXDialogEvent event) -> mainPane.setEffect(null));
    }
}

