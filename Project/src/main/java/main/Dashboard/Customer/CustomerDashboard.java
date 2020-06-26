package main.Dashboard.Customer;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.events.JFXDialogEvent;
import control.Controller;
import control.Exceptions.HaveNotLoggedInException;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.*;
import model.People.Customer;
import view.Profile.CustomerMenu.CustomerMenuPanes;

import java.io.IOException;

public class CustomerDashboard {

    @FXML
    private StackPane stackPane;

    @FXML
    private BorderPane mainPane;

    @FXML
    private JFXButton homeButton;

    @FXML
    private JFXButton cartButton;

    @FXML
    private JFXButton logoutButton;

    @FXML
    private Label usernameLabel;

    @FXML
    private JFXButton personalInfoButton;

    @FXML
    private JFXButton discountCodesButton;

    @FXML
    private JFXButton ordersButton;

    @FXML
    private void initialize() {
        stackPane.setOpacity(0);
        fadeIn();
        var cartDialog = new CartDialogController(stackPane);
        var addressDialog = new AddressController(stackPane);
        var offCodeDialog = new TakeOffCodeController(stackPane);
        var paymentDialog = new PaymentDialogController(stackPane);

        CustomerMenuPanes dashboard = new CustomerMenuPanes();
        Stage stage = Main.getPrimaryStage();
        Customer customer = (Customer) Controller.getCurrentAccount();

        usernameLabel.setText(customer.getUsername());

        personalInfoButton.setOnMouseClicked(event -> {
            Stage newStage = new Stage();
            newStage.setScene(new Scene(dashboard.getPersonalInfoPane()));
            newStage.setTitle("  Personal Info");
            newStage.getIcons().add(new Image(String.valueOf(Main.class.getResource("JShop.png"))));
            newStage.show();
        });

        discountCodesButton.setOnMouseClicked(event -> {
            Stage newStage = new Stage();
            newStage.setScene(new Scene(dashboard.getCustomerDiscountCodes(customer)));
            newStage.setTitle("  Personal Info");
            newStage.getIcons().add(new Image(String.valueOf(Main.class.getResource("JShop.png"))));
            newStage.show();
        });

        ordersButton.setOnMouseClicked(event -> {
            Stage newStage = new Stage();
            newStage.setScene(new Scene(dashboard.getOrdersPane()));
            newStage.setTitle("  Personal Info");
            newStage.getIcons().add(new Image(String.valueOf(Main.class.getResource("JShop.png"))));
            newStage.show();
        });

        homeButton.setOnMouseClicked(event -> {
            try {
                fadeOut(FXMLLoader.load(Main.class.getResource("main.fxml")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        logoutButton.setOnMouseClicked(event -> {
            BoxBlur boxBlur = new BoxBlur(4, 4, 4);
            JFXButton button = new JFXButton("  Yes  ");
            button.setStyle("-fx-background-color:#fe615a; -fx-background-radius:  18; -fx-text-fill: white");
            button.setPadding(new Insets(3, 16, 3, 16));
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            dialogLayout.setHeading(new Label(" Logout "));
            dialogLayout.setStyle("-fx-background-color: rgba(255,104,110,0.64)");
            JFXDialog dialog = new JFXDialog(stackPane, dialogLayout, JFXDialog.DialogTransition.CENTER);
            button.setOnMouseClicked((MouseEvent event1) -> {
                dialog.close();
            });
            dialogLayout.setActions(button);
            dialog.show();
            mainPane.setEffect(boxBlur);
            dialog.setOnDialogClosed((JFXDialogEvent event1) -> {
                        mainPane.setEffect(null);
                        try {
                            Controller.logout();
                            try {
                                fadeOut(FXMLLoader.load(Main.class.getResource("main.fxml")));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } catch (HaveNotLoggedInException e) {
                            e.printStackTrace();
                        }
                    }
            );
        });

        cartButton.setOnMouseClicked(event -> {
            BoxBlur boxBlur = new BoxBlur(6, 6, 6);
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            JFXDialog dialog = new JFXDialog(stackPane, dialogLayout, JFXDialog.DialogTransition.CENTER);
            dialogLayout.setActions(cartDialog);
            dialogLayout.setStyle("-fx-background-color:  #db5e5c");
            dialog.show();
            mainPane.setEffect(boxBlur);
            dialog.setOnDialogClosed((JFXDialogEvent e) -> mainPane.setEffect(null));
        });

        cartDialog.getPayButton().setOnMouseClicked(event -> {
            BoxBlur boxBlur = new BoxBlur(6, 6, 6);
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            JFXDialog dialog = new JFXDialog(stackPane, dialogLayout, JFXDialog.DialogTransition.CENTER);
            dialogLayout.setActions(addressDialog);
            dialogLayout.setStyle("-fx-background-color:   #886488");
            dialog.show();
            cartDialog.setEffect(boxBlur);
            dialog.setOnDialogClosed((JFXDialogEvent e) -> cartDialog.setEffect(null));
        });

        addressDialog.getNextButton().setOnMouseClicked(event -> {
            BoxBlur boxBlur = new BoxBlur(6, 6, 6);
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            JFXDialog dialog = new JFXDialog(stackPane, dialogLayout, JFXDialog.DialogTransition.CENTER);
            dialogLayout.setActions(offCodeDialog);
            dialogLayout.setStyle("-fx-background-color:   #f3c669");
            dialog.show();
            addressDialog.setEffect(boxBlur);
            dialog.setOnDialogClosed((JFXDialogEvent e) -> addressDialog.setEffect(null));
        });

        offCodeDialog.getNextButton().setOnMouseClicked(event -> {
            BoxBlur boxBlur = new BoxBlur(6, 6, 6);
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            JFXDialog dialog = new JFXDialog(stackPane, dialogLayout, JFXDialog.DialogTransition.CENTER);
            dialogLayout.setActions(paymentDialog);
            dialogLayout.setStyle("-fx-background-color:    #b2aa72");
            dialog.show();
            offCodeDialog.setEffect(boxBlur);
            dialog.setOnDialogClosed((JFXDialogEvent e) -> offCodeDialog.setEffect(null));
        });

        paymentDialog.getPayButton().setOnMouseClicked(event -> {
            try {
                Main.setRoot("customer-dashboard");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    private void fadeIn() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setNode(stackPane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    private void fadeOut(StackPane node) {
        node.getStylesheets().add("main.css");
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setNode(stackPane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished(event -> {
            stackPane.getScene().setRoot(node);
        });
        fadeTransition.play();
    }

    private void showError(String message) {
        showErrorWithColor(message, "#fe615a");
    }

    @FXML
    private void showErrorWithColor(String message, String color) {
        BoxBlur boxBlur = new BoxBlur(4, 4, 4);
        JFXButton button = new JFXButton("  Yes  ");
        button.setStyle("-fx-background-color:" + color + "; -fx-background-radius:  18; -fx-text-fill: white");
        button.setPadding(new Insets(3, 16, 3, 16));
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        dialogLayout.setHeading(new Label(message));
        dialogLayout.setStyle("-fx-background-color: rgba(255,104,110,0.64)");
        JFXDialog dialog = new JFXDialog(stackPane, dialogLayout, JFXDialog.DialogTransition.CENTER);
        button.setOnMouseClicked((MouseEvent event) -> {
            dialog.close();
        });
        dialogLayout.setActions(button);
        dialog.show();
        mainPane.setEffect(boxBlur);
        dialog.setOnDialogClosed((JFXDialogEvent event) -> mainPane.setEffect(null));
    }

    private static void playAudio(String musicFile) {
//        AudioClip audioClip = new AudioClip(String.valueOf(Main.class.getResource(musicFile)));
//        audioClip.setCycleCount(Integer.MAX_VALUE);
//        audioClip.play();
    }
}
