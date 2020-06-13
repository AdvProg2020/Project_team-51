package main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class PageNumberHBox extends AnchorPane {

    @FXML
    private HBox pageNumberBox;

    public PageNumberHBox() {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("page_number_hbox.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public HBox getPageNumberBox() {
        return pageNumberBox;
    }

    public void setPageNumberBox(HBox pageNumberBox) {
        this.pageNumberBox = pageNumberBox;
    }
}
