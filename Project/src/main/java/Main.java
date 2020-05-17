import model.Database.Build;
import view.MainMenu;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Thread thread = new Thread(new Build());
        thread.start();
        new MainMenu();
    }
}
