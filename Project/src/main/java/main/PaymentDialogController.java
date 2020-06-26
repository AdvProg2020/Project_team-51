package main;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class PaymentDialogController extends AnchorPane {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXButton payButton;


    private StackPane stackPane;

    public PaymentDialogController(StackPane stackPane) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("payment.fxml"));
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

    public JFXButton getPayButton() {
        return payButton;
    }

    public void setPayButton(JFXButton payButton) {
        this.payButton = payButton;
    }

    public StackPane getStackPane() {
        return stackPane;
    }

    public void setStackPane(StackPane stackPane) {
        this.stackPane = stackPane;
    }

    private static void playAudio(String musicFile) {
//        AudioClip audioClip = new AudioClip(String.valueOf(Main.class.getResource(musicFile)));
//        audioClip.setCycleCount(Integer.MAX_VALUE);
//        audioClip.play();
    }
}
