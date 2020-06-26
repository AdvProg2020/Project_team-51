package main.Dashboard.Seller;

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
import main.Main;
import model.People.Seller;
import view.Profile.SellerMenu.SellerMenuPanes;

import java.io.IOException;

public class SellerDashboard {

    @FXML
    private StackPane stackPane;

    @FXML
    private BorderPane mainPane;

    @FXML
    private JFXButton homeButton;

    @FXML
    private JFXButton logoutButton;

    @FXML
    private Label usernameLabel;

    @FXML
    private JFXButton personalInfoButton;

    @FXML
    private JFXButton salesButton;

    @FXML
    private JFXButton manageProductsButton;

    @FXML
    private JFXButton createProductButton;

    @FXML
    private JFXButton viewCategoriesButton;

    @FXML
    private JFXButton createAuctionButton;

    @FXML
    private JFXButton viewAuctionsButton;

    private static void playAudio(String musicFile) {
//        AudioClip audioClip = new AudioClip(String.valueOf(Main.class.getResource(musicFile)));
//        audioClip.setCycleCount(Integer.MAX_VALUE);
//        audioClip.play();
    }

    @FXML
    private void initialize() {
        stackPane.setOpacity(0);
        fadeIn();
        SellerMenuPanes dashboard = new SellerMenuPanes();
        Seller seller = (Seller) Controller.getCurrentAccount();

        usernameLabel.setText(seller.getUsername());

        personalInfoButton.setOnMouseClicked(event -> {
            new Thread(() -> playAudio("button2.wav")).start();
            Stage newStage = new Stage();
            newStage.setScene(new Scene(SellerMenuPanes.getPersonalInfoPane()));
            newStage.setTitle("  Personal Info");
            newStage.getIcons().add(new Image(String.valueOf(Main.class.getResource("JShop.png"))));
            newStage.show();
        });

        salesButton.setOnMouseClicked(event -> {
            new Thread(() -> playAudio("button2.wav")).start();
            Stage newStage = new Stage();
            newStage.setScene(new Scene(dashboard.getSalesHistoryPane()));
            newStage.setTitle("  Sales");
            newStage.getIcons().add(new Image(String.valueOf(Main.class.getResource("JShop.png"))));
            newStage.show();
        });

        manageProductsButton.setOnMouseClicked(event -> {
            new Thread(() -> playAudio("button2.wav")).start();
            Stage newStage = new Stage();
            newStage.setScene(new Scene(dashboard.getManageProductsPane()));
            newStage.setTitle("  Manage Products");
            newStage.getIcons().add(new Image(String.valueOf(Main.class.getResource("JShop.png"))));
            newStage.show();
        });

        createProductButton.setOnMouseClicked(event -> {
            new Thread(() -> playAudio("button2.wav")).start();
            Stage newStage = new Stage();
            newStage.setScene(new Scene(dashboard.getCreateProductPane()));
            newStage.setTitle("  Create Product");
            newStage.getIcons().add(new Image(String.valueOf(Main.class.getResource("JShop.png"))));
            newStage.show();
        });

        viewCategoriesButton.setOnMouseClicked(event -> {
            new Thread(() -> playAudio("button2.wav")).start();
            Stage newStage = new Stage();
            newStage.setScene(new Scene(dashboard.getViewCategoriesPane()));
            newStage.setTitle("  View Categories");
            newStage.getIcons().add(new Image(String.valueOf(Main.class.getResource("JShop.png"))));
            newStage.show();
        });

        createAuctionButton.setOnMouseClicked(event -> {
            new Thread(() -> playAudio("button2.wav")).start();
            Stage newStage = new Stage();
            newStage.setScene(new Scene(dashboard.getCreateAuctionPane()));
            newStage.setTitle("  Create Auction");
            newStage.getIcons().add(new Image(String.valueOf(Main.class.getResource("JShop.png"))));
            newStage.show();
        });

        viewAuctionsButton.setOnMouseClicked(event -> {
//            new Thread(() -> playAudio("button2.wav")).start();
//            Stage newStage = new Stage();
//            newStage.setScene(new Scene(dashboard.get()));
//            newStage.setTitle("  Create Category");
//            newStage.getIcons().add(new Image(String.valueOf(Main.class.getResource("JShop.png"))));
//            newStage.show();
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
            new Thread(() -> playAudio("button2.wav")).start();
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

    }

    private void showError(String message) {
        showErrorWithColor(message, "#fe615a");
    }

    @FXML
    private void showErrorWithColor(String message, String color) {
        new Thread(() -> playAudio("dialog.wav")).start();
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

    public JFXButton getSalesButton() {
        return salesButton;
    }

    public void setSalesButton(JFXButton salesButton) {
        this.salesButton = salesButton;
    }

    public JFXButton getManageProductsButton() {
        return manageProductsButton;
    }

    public void setManageProductsButton(JFXButton manageProductsButton) {
        this.manageProductsButton = manageProductsButton;
    }

    public JFXButton getCreateProductButton() {
        return createProductButton;
    }

    public void setCreateProductButton(JFXButton createProductButton) {
        this.createProductButton = createProductButton;
    }

    public JFXButton getViewCategoriesButton() {
        return viewCategoriesButton;
    }

    public void setViewCategoriesButton(JFXButton viewCategoriesButton) {
        this.viewCategoriesButton = viewCategoriesButton;
    }

    public JFXButton getCreateAuctionButton() {
        return createAuctionButton;
    }

    public void setCreateAuctionButton(JFXButton createAuctionButton) {
        this.createAuctionButton = createAuctionButton;
    }

    public JFXButton getViewAuctionsButton() {
        return viewAuctionsButton;
    }

    public void setViewAuctionsButton(JFXButton viewAuctionsButton) {
        this.viewAuctionsButton = viewAuctionsButton;
    }
}
