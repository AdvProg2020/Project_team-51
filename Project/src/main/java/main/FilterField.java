package main;

import com.jfoenix.controls.JFXToggleButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class FilterField extends Pane {


    @FXML
    private JFXToggleButton toggleButton;


    public FilterField() {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("filter_field.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }


    public JFXToggleButton getToggleButton() {
        return toggleButton;
    }

    public void setToggleButton(JFXToggleButton toggleButton) {
        this.toggleButton = toggleButton;
    }
}
