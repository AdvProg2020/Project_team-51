package main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class AddressController extends AnchorPane {

    @FXML
    private JFXTextArea addressField;

    @FXML
    private JFXButton nextButton;

    @FXML
    private Label registerError;


    private StackPane stackPane;


    public AddressController(StackPane stackPane) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("address.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        this.stackPane = stackPane;
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public JFXTextArea getAddressField() {
        return addressField;
    }

    public void setAddressField(JFXTextArea addressField) {
        this.addressField = addressField;
    }

    public JFXButton getNextButton() {
        return nextButton;
    }

    public void setNextButton(JFXButton nextButton) {
        this.nextButton = nextButton;
    }

    public Label getRegisterError() {
        return registerError;
    }

    public void setRegisterError(Label registerError) {
        this.registerError = registerError;
    }

    public StackPane getStackPane() {
        return stackPane;
    }

    public void setStackPane(StackPane stackPane) {
        this.stackPane = stackPane;
    }
}
