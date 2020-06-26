package main.Dashboard.Customer;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.events.JFXDialogEvent;
import control.Controller;
import control.CustomerController;
import control.Exceptions.HaveNotLoggedInException;
import control.Exceptions.InsufficientBalanceException;
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

    private static void playAudio(String musicFile) {
//        AudioClip audioClip = new AudioClip(String.valueOf(Main.class.getResource(musicFile)));
//        audioClip.setCycleCount(Integer.MAX_VALUE);
//        audioClip.play();
    }

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
            new Thread(() -> playAudio("button2.wav")).start();

            Stage newStage = new Stage();
            newStage.setScene(new Scene(dashboard.getPersonalInfoPane()));
            newStage.setTitle("  Personal Info");
            newStage.getIcons().add(new Image(String.valueOf(Main.class.getResource("JShop.png"))));
            newStage.show();
        });

        discountCodesButton.setOnMouseClicked(event -> {
            new Thread(() -> playAudio("button2.wav")).start();

            Stage newStage = new Stage();
            newStage.setScene(new Scene(dashboard.getCustomerDiscountCodes()));
            newStage.setTitle("  Personal Info");
            newStage.getIcons().add(new Image(String.valueOf(Main.class.getResource("JShop.png"))));
            newStage.show();
        });

        ordersButton.setOnMouseClicked(event -> {
            new Thread(() -> playAudio("button2.wav")).start();

            Stage newStage = new Stage();
            newStage.setScene(new Scene(dashboard.getOrdersPane()));
            newStage.setTitle("  Personal Info");
            newStage.getIcons().add(new Image(String.valueOf(Main.class.getResource("JShop.png"))));
            newStage.show();
        });

        homeButton.setOnMouseClicked(event -> {
            new Thread(() -> playAudio("button2.wav")).start();

            try {
                fadeOut(FXMLLoader.load(Main.class.getResource("main.fxml")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        logoutButton.setOnMouseClicked(event -> {
            new Thread(() -> playAudio("dialog.wav")).start();
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
            var account = Controller.getCurrentAccount();
            if (account == null || account instanceof Customer) {
                new Thread(() -> playAudio("button.wav")).start();
                BoxBlur boxBlur = new BoxBlur(6, 6, 6);
                JFXDialogLayout dialogLayout = new JFXDialogLayout();
                JFXDialog dialog = new JFXDialog(stackPane, dialogLayout, JFXDialog.DialogTransition.CENTER);
                dialogLayout.setActions(cartDialog);
                dialogLayout.setStyle("-fx-background-color:  #db5e5c");
                dialog.show();
                mainPane.setEffect(boxBlur);
                dialog.setOnDialogClosed((JFXDialogEvent e) -> mainPane.setEffect(null));
            } else {
                showError("Not allowed activity");
            }
        });
        cartDialog.getPayButton().setOnMouseClicked(event -> {
            var account = Controller.getCurrentAccount();
            if (account instanceof Customer) {
                new Thread(() -> playAudio("button.wav")).start();
                BoxBlur boxBlur = new BoxBlur(6, 6, 6);
                JFXDialogLayout dialogLayout = new JFXDialogLayout();
                JFXDialog dialog = new JFXDialog(stackPane, dialogLayout, JFXDialog.DialogTransition.CENTER);
                dialogLayout.setActions(addressDialog);
                dialogLayout.setStyle("-fx-background-color:   #886488");
                dialog.show();
                cartDialog.setEffect(boxBlur);
                dialog.setOnDialogClosed((JFXDialogEvent e) -> cartDialog.setEffect(null));
            } else {
                showError("You have to login first!");
            }
        });
        addressDialog.getNextButton().setOnMouseClicked(event -> {
            new Thread(() -> playAudio("button.wav")).start();
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
            new Thread(() -> playAudio("button.wav")).start();
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
            new Thread(() -> playAudio("button.wav")).start();
            try {
                new CustomerController(Controller.getCurrentAccount()).purchase();
                Main.setRoot("main");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InsufficientBalanceException e) {
                showError("Insufficient Money");
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
            new Thread(() -> playAudio("dialog.wav")).start();

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

    public StackPane getStackPane() {
        return stackPane;
    }

    public void setStackPane(StackPane stackPane) {
        this.stackPane = stackPane;
    }

    public BorderPane getMainPane() {
        return mainPane;
    }

    public void setMainPane(BorderPane mainPane) {
        this.mainPane = mainPane;
    }

    public JFXButton getHomeButton() {
        return homeButton;
    }

    public void setHomeButton(JFXButton homeButton) {
        this.homeButton = homeButton;
    }

    public JFXButton getCartButton() {
        return cartButton;
    }

    public void setCartButton(JFXButton cartButton) {
        this.cartButton = cartButton;
    }

    public JFXButton getLogoutButton() {
        return logoutButton;
    }

    public void setLogoutButton(JFXButton logoutButton) {
        this.logoutButton = logoutButton;
    }

    public Label getUsernameLabel() {
        return usernameLabel;
    }

    public void setUsernameLabel(Label usernameLabel) {
        this.usernameLabel = usernameLabel;
    }

    public JFXButton getPersonalInfoButton() {
        return personalInfoButton;
    }

    public void setPersonalInfoButton(JFXButton personalInfoButton) {
        this.personalInfoButton = personalInfoButton;
    }

    public JFXButton getDiscountCodesButton() {
        return discountCodesButton;
    }

    public void setDiscountCodesButton(JFXButton discountCodesButton) {
        this.discountCodesButton = discountCodesButton;
    }

    public JFXButton getOrdersButton() {
        return ordersButton;
    }

    public void setOrdersButton(JFXButton ordersButton) {
        this.ordersButton = ordersButton;
    }
}
