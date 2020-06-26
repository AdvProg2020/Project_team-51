package main.ProductPage;

import com.jfoenix.controls.*;
import com.jfoenix.controls.events.JFXDialogEvent;
import control.Exceptions.NotAllowedActivityException;
import control.SingleProductController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import main.Main;

import java.io.IOException;

public class NewCommentDialog extends AnchorPane {

    @FXML
    private JFXTextField title;

    @FXML
    private JFXTextArea comment;

    @FXML
    private JFXButton add;

    @FXML
    private JFXSlider scoreSlider;


    private StackPane rootPane;

    private String titleText;
    private String commentText;
    private SingleProductController singleProductController;


    public NewCommentDialog(StackPane stackPane, SingleProductController singleProductController) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("newComment.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (
                IOException exception) {
            throw new RuntimeException(exception);
        }
        this.rootPane = stackPane;
        this.singleProductController = singleProductController;

        add.setOnMouseClicked(event -> {
            new Thread(() -> playAudio("button.wav")).start();
        });

    }

    private static void playAudio(String musicFile) {
//        AudioClip audioClip = new AudioClip(String.valueOf(Main.class.getResource(musicFile)));
//        audioClip.setCycleCount(Integer.MAX_VALUE);
//        audioClip.play();
    }

    private void initialize() {
        title.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                titleText = newValue;
            }
        });
        comment.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                commentText = newValue;
            }
        });
        add.setOnMouseClicked(e -> {
            try {
                singleProductController.addComment(titleText, commentText);
                showConfirmation("Comment Request Sent Successfully!");
            } catch (NotAllowedActivityException ex) {
                showError("You are not allowed!");
            }
        });
    }

    private void showError(String message) {
        showErrorWithColor(message, "#fe615a");
    }

    @FXML
    private void showErrorWithColor(String message, String color) {
        BoxBlur boxBlur = new BoxBlur(4, 4, 4);
        JFXButton button = new JFXButton(" OK ");
        button.setStyle("-fx-background-color:" + color + "; -fx-background-radius:  18; -fx-text-fill: white");
        button.setPadding(new Insets(3, 16, 3, 16));
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        dialogLayout.setHeading(new Label(message));
        dialogLayout.setStyle("-fx-background-color: rgba(255,104,110,0.64)");
        JFXDialog dialog = new JFXDialog(rootPane, dialogLayout, JFXDialog.DialogTransition.CENTER);
        button.setOnMouseClicked((MouseEvent event) -> {
            dialog.close();
        });
        dialogLayout.setActions(button);
        dialog.show();
        this.setEffect(boxBlur);
        dialog.setOnDialogClosed((JFXDialogEvent event) -> this.setEffect(null));
    }

    @FXML
    private void showConfirmation(String message) {
        BoxBlur boxBlur = new BoxBlur(4, 4, 4);
        JFXButton button = new JFXButton("OK");
        button.setStyle("-fx-background-color: #6d8040; -fx-background-radius:  18; -fx-text-fill: white");
        button.setPadding(new Insets(3, 16, 3, 16));
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        dialogLayout.setHeading(new Label(message));
        dialogLayout.setStyle("-fx-background-color: rgba(118,160,93,0.64)");
        JFXDialog dialog = new JFXDialog(rootPane, dialogLayout, JFXDialog.DialogTransition.CENTER);
        button.setOnMouseClicked((MouseEvent event) -> {
            dialog.close();
        });
        dialogLayout.setActions(button);
        dialog.show();
        this.setEffect(boxBlur);
        dialog.setOnDialogClosed((JFXDialogEvent event) -> this.setEffect(null));
    }

    public JFXTextField getTitle() {
        return title;
    }

    public void setTitle(JFXTextField title) {
        this.title = title;
    }

    public JFXTextArea getComment() {
        return comment;
    }

    public void setComment(JFXTextArea comment) {
        this.comment = comment;
    }

    public JFXButton getAdd() {
        return add;
    }

    public void setAdd(JFXButton add) {
        this.add = add;
    }

    public JFXSlider getScoreSlider() {
        return scoreSlider;
    }

    public void setScoreSlider(JFXSlider scoreSlider) {
        this.scoreSlider = scoreSlider;
    }

    public StackPane getRootPane() {
        return rootPane;
    }

    public void setRootPane(StackPane rootPane) {
        this.rootPane = rootPane;
    }

    public String getTitleText() {
        return titleText;
    }

    public void setTitleText(String titleText) {
        this.titleText = titleText;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public SingleProductController getSingleProductController() {
        return singleProductController;
    }

    public void setSingleProductController(SingleProductController singleProductController) {
        this.singleProductController = singleProductController;
    }
}
