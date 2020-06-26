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
import view.Profile.ManagerMenu.ManagerMenuPanes;

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

    @FXML
    private void initialize() {
        stackPane.setOpacity(0);
        fadeIn();
        ManagerMenuPanes dashboard = new ManagerMenuPanes();
        Stage stage = Main.getPrimaryStage();
        Manager manager = (Manager) Controller.getCurrentAccount();

        usernameLabel.setText(manager.getUsername());

        personalInfoButton.setOnMouseClicked(event -> {
            Stage newStage = new Stage();
            newStage.setScene(new Scene(dashboard.getPersonalInfoPane()));
            newStage.setTitle("  Personal Info");
            newStage.getIcons().add(new Image(String.valueOf(Main.class.getResource("JShop.png"))));
            newStage.show();
        });

        createDiscountButton.setOnMouseClicked(event -> {
            Stage newStage = new Stage();
            newStage.setScene(new Scene(dashboard.getCreateDiscountCodePane()));
            newStage.setTitle("  Create Discount");
            newStage.getIcons().add(new Image(String.valueOf(Main.class.getResource("JShop.png"))));
            newStage.show();
        });

        viewDiscountButton.setOnMouseClicked(event -> {
            Stage newStage = new Stage();
            newStage.setScene(new Scene(dashboard.getViewOffcodesPane()));
            newStage.setTitle("  View Discounts");
            newStage.getIcons().add(new Image(String.valueOf(Main.class.getResource("JShop.png"))));
            newStage.show();
        });

        manageProducts.setOnMouseClicked(event -> {
            Stage newStage = new Stage();
            newStage.setScene(new Scene(dashboard.getManageAllProductsPane()));
            newStage.setTitle("  Manage Products");
            newStage.getIcons().add(new Image(String.valueOf(Main.class.getResource("JShop.png"))));
            newStage.show();

        });

        createCategoryButton.setOnMouseClicked(event -> {
            Stage newStage = new Stage();
            newStage.setScene(new Scene(dashboard.getCreateCategoryPane()));
            newStage.setTitle("  Create Category");
            newStage.getIcons().add(new Image(String.valueOf(Main.class.getResource("JShop.png"))));
            newStage.show();
        });

        viewCategoriesButton.setOnMouseClicked(event -> {
            //    stage.setScene(new Scene(dashboard.));
        });

        manageRequestsButton.setOnMouseClicked(event -> {
            Stage newStage = new Stage();
            newStage.setScene(new Scene(dashboard.getManageRequestsPane()));
            newStage.setTitle("  Manage Requests");
            newStage.getIcons().add(new Image(String.valueOf(Main.class.getResource("JShop.png"))));
            newStage.show();
        });


        manageUsersButton.setOnMouseClicked(event -> {
            Stage newStage = new Stage();
            newStage.setScene(new Scene(dashboard.getManageUsersPane()));
            newStage.setTitle("  Manage Users");
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
            Main.getPrimaryStage().setScene(new Scene(node));
        });
        fadeTransition.play();
    }
}
