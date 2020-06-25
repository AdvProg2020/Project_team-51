package main.Dashboard.Manager;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.events.JFXDialogEvent;
import control.Controller;
import control.Exceptions.HaveNotLoggedInException;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
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
        ManagerMenuPanes dashboard = new ManagerMenuPanes();
        Stage stage = (Stage) stackPane.getScene().getWindow();
        Manager manager = (Manager) Controller.getCurrentAccount();

        usernameLabel.setText(manager.getUsername());

        personalInfoButton.setOnMouseClicked(event -> {
            stage.setScene(new Scene(dashboard.getPersonalInfoPane()));
        });

        createDiscountButton.setOnMouseClicked(event -> {
            stage.setScene(new Scene(dashboard.getCreateDiscountCodePane()));
        });

        viewDiscountButton.setOnMouseClicked(event -> {
            stage.setScene(new Scene(dashboard.getViewOffcodesPane()));
        });

        manageProducts.setOnMouseClicked(event -> {
            stage.setScene(new Scene(dashboard.getManageAllProductsPane()));
        });

        createCategoryButton.setOnMouseClicked(event -> {
            stage.setScene(new Scene(dashboard.getCreateCategoryPane()));
        });

        viewCategoriesButton.setOnMouseClicked(event -> {
            //    stage.setScene(new Scene(dashboard.));
        });

        manageRequestsButton.setOnMouseClicked(event -> {
            stage.setScene(new Scene(dashboard.getManageRequestsPane()));
        });


        manageUsersButton.setOnMouseClicked(event -> {
            stage.setScene(new Scene(dashboard.getManageUsersPane()));
        });

        homeButton.setOnMouseClicked(event -> {
            try {
                Main.setRoot("main");
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
                                Main.setRoot("main");
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
}
