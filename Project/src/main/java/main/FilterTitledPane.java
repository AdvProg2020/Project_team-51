package main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class FilterTitledPane extends AnchorPane {

    @FXML
    private TitledPane titledPane;

    @FXML
    private VBox vBox;


    public FilterTitledPane(String name) {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("filter-titled-pane.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        titledPane.setText(name);
    }


    public TitledPane getTitledPane() {
        return titledPane;
    }

    public void setTitledPane(TitledPane titledPane) {
        this.titledPane = titledPane;
    }

    public VBox getVBox() {
        return vBox;
    }

    public void setVBox(VBox vBox) {
        this.vBox = vBox;
    }
}
