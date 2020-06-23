package main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class TakeOffCodeController extends AnchorPane {


    @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXToggleButton toggleButton;

    @FXML
    private JFXTextField codeField;

    @FXML
    private JFXButton nextButton;

    private StackPane stackPane;

    public TakeOffCodeController(StackPane stackPane) {
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

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    public void setAnchorPane(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
    }

    public JFXToggleButton getToggleButton() {
        return toggleButton;
    }

    public void setToggleButton(JFXToggleButton toggleButton) {
        this.toggleButton = toggleButton;
    }

    public JFXTextField getCodeField() {
        return codeField;
    }

    public void setCodeField(JFXTextField codeField) {
        this.codeField = codeField;
    }

    public JFXButton getNextButton() {
        return nextButton;
    }

    public void setNextButton(JFXButton nextButton) {
        this.nextButton = nextButton;
    }

    public StackPane getStackPane() {
        return stackPane;
    }

    public void setStackPane(StackPane stackPane) {
        this.stackPane = stackPane;
    }
}
