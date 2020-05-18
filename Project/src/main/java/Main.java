import model.Database.Build;
import model.Database.StatusUpdater;
import view.MainMenu;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Thread buildThread = new Thread(new Build());
        Thread statusUpdaterThread = new Thread(new StatusUpdater());
        buildThread.start();
        statusUpdaterThread.start();
        new MainMenu();
    }
}
