package main;

import javafx.scene.image.Image;

public class RandomPicture {
    public static Image getRandomImage() {
        int random = (int) (Math.random() * 1000);
        if (random % 3 == 0) {
            return new Image(String.valueOf(Main.class.getResource("camera.png")));
        } else if (random % 3 == 1) {
            return new Image(String.valueOf(Main.class.getResource("unnamed.png")));
        } else {
            return new Image(String.valueOf(Main.class.getResource("laundry_p-v2-500x500.png")));
        }
    }
}
