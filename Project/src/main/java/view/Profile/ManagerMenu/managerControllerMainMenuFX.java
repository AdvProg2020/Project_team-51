package view.Profile.ManagerMenu;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.paint.Paint;
import java.awt.*;
import java.util.Collections;
import javafx.scene.control.Button;
import static java.awt.Color.cyan;
import static java.awt.Color.red;

public class managerControllerMainMenuFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(new Scene(getManagerControllerPane()));
        primaryStage.show();
    }

    public Pane getManagerControllerPane (){
        Pane pane = new Pane();
        pane.setPrefSize(1540 , 800);
        Rectangle sideBar = new Rectangle(30 , 800 , javafx.scene.paint.Color.RED);
        Button manageUsers = buttonMaker(300,300,"manage users");
        Button manageCategories = buttonMaker(500 , 300 , "manage categories");
        pane.getChildren().addAll(sideBar , manageCategories , manageUsers);
        return pane;

    }


    public static Button buttonMaker (int x , int y , String text){

        Button b = new Button(text);
        b.setLayoutX(x);
        b.setLayoutY(y);
        return b;
    }
}
