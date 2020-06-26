package main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class CommentController extends Pane {


    @FXML
    private VBox vBox;

    @FXML
    private Label title;

    @FXML
    private ImageView rate;

    @FXML
    private Label comment;

    public CommentController() {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("comment.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private static void playAudio(String musicFile) {
//        AudioClip audioClip = new AudioClip(String.valueOf(Main.class.getResource(musicFile)));
//        audioClip.setCycleCount(Integer.MAX_VALUE);
//        audioClip.play();
    }

    public VBox getVBox() {
        return vBox;
    }

    public void setVBox(VBox vBox) {
        this.vBox = vBox;
    }

    public Label getTitle() {
        return title;
    }

    public void setTitle(Label title) {
        this.title = title;
    }

    public ImageView getRate() {
        return rate;
    }

    public void setRate(ImageView rate) {
        this.rate = rate;
    }

    public Label getComment() {
        return comment;
    }

    public void setComment(Label comment) {
        this.comment = comment;
    }
}
