package main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import control.ManagerController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class LoginDialog extends AnchorPane {

    @FXML
    private ComboBox<String> accountType;

    @FXML
    private JFXTextField firstName;

    @FXML
    private JFXTextField lastName;

    @FXML
    private JFXTextField usernameRegister;

    @FXML
    private JFXTextField passwordRegister;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXTextField phoneNumber;

    @FXML
    private JFXTextField balance;

    @FXML
    private JFXTextField company;

    @FXML
    private JFXButton registerButton;

    @FXML
    private Label registerError;

    @FXML
    private JFXTextField usernameLogin;

    @FXML
    private JFXPasswordField passwordLogin;

    @FXML
    private Label loginError;

    @FXML
    private JFXButton loginButton;

    public LoginDialog() {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login_dialog.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.getStylesheets().add(Main.class.getResource("login_dialog.css").toExternalForm());
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll("Customer", "Seller");
        if (!ManagerController.isThereAnyManager())
            items.add("Manager");

        accountType.setItems(items);
        accountType.getSelectionModel().select("Customer");
        accountType.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println(newValue);
                if (newValue.equals("Seller"))
                    company.setVisible(true);
                else
                    company.setVisible(false);
            }
        });
    }
}
