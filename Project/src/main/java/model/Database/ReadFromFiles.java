package model.Database;

import java.io.File;
import java.io.IOException;

public class ReadFromFiles {

    public static File[] readFromFile (String path) throws IOException {
        File file = new File(path);
        if (!file.exists())
            file.mkdir();
        return file.listFiles();
    }
}
