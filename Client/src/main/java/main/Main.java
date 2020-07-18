package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Scene scene;
    private static Stage primaryStage; // **Declare static Stage**

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static void setRoot(Parent fxml) throws IOException {
        scene.setRoot(fxml);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        System.out.println(Main.class.getClassLoader().getName());
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) throws IOException {
//        Thread buildThread = new Thread(new Build());
//        Thread statusUpdaterThread = new Thread(new StatusUpdater());
//        buildThread.start();
//        statusUpdaterThread.start();
//        new Thread(() -> playAudio("Hunter.mp3")).start();
        launch();

    }

    static public Stage getPrimaryStage() {
        return Main.primaryStage;
    }

    private void setPrimaryStage(Stage stage) {
        Main.primaryStage = stage;
    }

    private static void playAudio(String musicFile) {
//        AudioClip audioClip = new AudioClip(String.valueOf(Main.class.getResource(musicFile)));
//        audioClip.setCycleCount(Integer.MAX_VALUE);
//        audioClip.play();
    }

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("main"));
        stage.setScene(scene);
        stage.setTitle("  JShop");
        stage.getIcons().add(new Image(String.valueOf(Main.class.getResource("JShop.png"))));
        setPrimaryStage(stage);
        scene.getStylesheets().add(Main.class.getResource("main.css").toExternalForm());
        stage.show();
    }

}
