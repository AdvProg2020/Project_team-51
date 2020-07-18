package main.Dashboard.Manager;

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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.Main;
import model.People.Manager;
import view.Profile.ManagerMenuPanes;

import java.io.IOException;

public class ManagerDashboard {

    @FXML
    private StackPane stackPane;

    @FXML
    private BorderPane mainPane;

    @FXML
    private JFXButton homeButton;

    @FXML
    private JFXButton logoutButton;

    @FXML
    private ImageView avatar;

    @FXML
    private Label usernameLabel;

    @FXML
    private JFXButton personalInfoButton;

    @FXML
    private JFXButton createDiscountButton;

    @FXML
    private JFXButton viewDiscountButton;

    @FXML
    private JFXButton manageProducts;

    @FXML
    private JFXButton createCategoryButton;

    @FXML
    private JFXButton viewCategoriesButton;

    @FXML
    private JFXButton manageRequestsButton;

    @FXML
    private JFXButton viewOffCodesButton;

    @FXML
    private JFXButton manageUsersButton;

    private static void playAudio(String musicFile) {
//        AudioClip audioClip = new AudioClip(String.valueOf(Main.class.getResource(musicFile)));
//        audioClip.setCycleCount(Integer.MAX_VALUE);
//        audioClip.play();
    }

    @FXML
    private void initialize() {
        stackPane.setOpacity(0);
        fadeIn();
        ManagerMenuPanes dashboard = new ManagerMenuPanes();
        Stage stage = Main.getPrimaryStage();
        Manager manager = (Manager) Controller.getCurrentAccount();

        usernameLabel.setText(manager.getUserName());

        personalInfoButton.setOnMouseClicked(event -> {
            new Thread(() -> playAudio("button2.wav")).start();
            Stage newStage = new Stage();
            newStage.setScene(new Scene(dashboard.getPersonalInfoPane()));
            newStage.setTitle("  Personal Info");
            newStage.getIcons().add(new Image(String.valueOf(Main.class.getResource("JShop.png"))));
            newStage.show();
        });

        createDiscountButton.setOnMouseClicked(event -> {
            new Thread(() -> playAudio("button2.wav")).start();
            Stage newStage = new Stage();
            newStage.setScene(new Scene(dashboard.getCreateDiscountCodePane()));
            newStage.setTitle("  Create Discount");
            newStage.getIcons().add(new Image(String.valueOf(Main.class.getResource("JShop.png"))));
            newStage.show();
        });

        viewDiscountButton.setOnMouseClicked(event -> {
            new Thread(() -> playAudio("button2.wav")).start();
            Stage newStage = new Stage();
            newStage.setScene(new Scene(dashboard.getViewOffcodesPane()));
            newStage.setTitle("  View Discounts");
            newStage.getIcons().add(new Image(String.valueOf(Main.class.getResource("JShop.png"))));
            newStage.show();
        });

        manageProducts.setOnMouseClicked(event -> {
            new Thread(() -> playAudio("button2.wav")).start();
            Stage newStage = new Stage();
            newStage.setScene(new Scene(dashboard.getManageAllProductsPane()));
            newStage.setTitle("  Manage Products");
            newStage.getIcons().add(new Image(String.valueOf(Main.class.getResource("JShop.png"))));
            newStage.show();

        });

        createCategoryButton.setOnMouseClicked(event -> {
            new Thread(() -> playAudio("button2.wav")).start();
            Stage newStage = new Stage();
            newStage.setScene(new Scene(dashboard.getCreateCategoryPane()));
            newStage.setTitle("  Create Category");
            newStage.getIcons().add(new Image(String.valueOf(Main.class.getResource("JShop.png"))));
            newStage.show();
        });

        viewCategoriesButton.setOnMouseClicked(event -> {
            new Thread(() -> playAudio("button2.wav")).start();
            Stage newStage = new Stage();
            newStage.setScene(new Scene(dashboard.getAllCategoriesPane()));
            newStage.setTitle("  View Categories");
            newStage.getIcons().add(new Image(String.valueOf(Main.class.getResource("JShop.png"))));
            newStage.show();
        });

        manageRequestsButton.setOnMouseClicked(event -> {
            new Thread(() -> playAudio("button2.wav")).start();
            Stage newStage = new Stage();
            newStage.setScene(new Scene(dashboard.getManageRequestsPane()));
            newStage.setTitle("  Manage Requests");
            newStage.getIcons().add(new Image(String.valueOf(Main.class.getResource("JShop.png"))));
            newStage.show();
        });


        manageUsersButton.setOnMouseClicked(event -> {
            new Thread(() -> playAudio("button2.wav")).start();
            Stage newStage = new Stage();
            newStage.setScene(new Scene(dashboard.getManageUsersPane()));
            newStage.setTitle("  Manage Users");
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
            Main.getPrimaryStage().setScene(new Scene(node));
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

    public ImageView getAvatar() {
        return avatar;
    }

    public void setAvatar(ImageView avatar) {
        this.avatar = avatar;
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

    public JFXButton getCreateDiscountButton() {
        return createDiscountButton;
    }

    public void setCreateDiscountButton(JFXButton createDiscountButton) {
        this.createDiscountButton = createDiscountButton;
    }

    public JFXButton getViewDiscountButton() {
        return viewDiscountButton;
    }

    public void setViewDiscountButton(JFXButton viewDiscountButton) {
        this.viewDiscountButton = viewDiscountButton;
    }

    public JFXButton getManageProducts() {
        return manageProducts;
    }

    public void setManageProducts(JFXButton manageProducts) {
        this.manageProducts = manageProducts;
    }

    public JFXButton getCreateCategoryButton() {
        return createCategoryButton;
    }

    public void setCreateCategoryButton(JFXButton createCategoryButton) {
        this.createCategoryButton = createCategoryButton;
    }

    public JFXButton getViewCategoriesButton() {
        return viewCategoriesButton;
    }

    public void setViewCategoriesButton(JFXButton viewCategoriesButton) {
        this.viewCategoriesButton = viewCategoriesButton;
    }

    public JFXButton getManageRequestsButton() {
        return manageRequestsButton;
    }

    public void setManageRequestsButton(JFXButton manageRequestsButton) {
        this.manageRequestsButton = manageRequestsButton;
    }

    public JFXButton getViewOffCodesButton() {
        return viewOffCodesButton;
    }

    public void setViewOffCodesButton(JFXButton viewOffCodesButton) {
        this.viewOffCodesButton = viewOffCodesButton;
    }

    public JFXButton getManageUsersButton() {
        return manageUsersButton;
    }

    public void setManageUsersButton(JFXButton manageUsersButton) {
        this.manageUsersButton = manageUsersButton;
    }
}
