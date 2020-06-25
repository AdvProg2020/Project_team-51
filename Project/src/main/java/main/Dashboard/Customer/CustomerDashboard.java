package main.Dashboard.Customer;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

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

    }
}
