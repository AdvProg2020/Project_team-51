package main.Dashboard.Customer;

import com.jfoenix.controls.JFXButton;
import control.Controller;
import control.Exceptions.HaveNotLoggedInException;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import main.Main;
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
        CustomerMenuPanes dashboard = new CustomerMenuPanes();
        Stage stage = (Stage) stackPane.getScene().getWindow();
        Customer customer = (Customer) Controller.getCurrentAccount();


        usernameLabel.setText(customer.getUsername());

        personalInfoButton.setOnMouseClicked(event -> {
            stage.setScene(new Scene(dashboard.getPersonalInfoPane()));
        });

        discountCodesButton.setOnMouseClicked(event -> {
            stage.setScene(new Scene(dashboard.getCustomerDiscountCodes(customer)));
        });

        ordersButton.setOnMouseClicked(event -> {
            stage.setScene(new Scene(dashboard.getOrdersPane()));
        });

        homeButton.setOnMouseClicked(event -> {
            try {
                Main.setRoot("main");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        logoutButton.setOnMouseClicked(event -> {
            try {
                Controller.logout();
                Main.setRoot("main");
            } catch (HaveNotLoggedInException e) {

            } catch (IOException e) {

            }
        });
    }
}
