package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteIntoFiles {

    public static void writeIntoFile(String yaGson, String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdir();
                file.createNewFile();
            } catch (IOException e) {
                throw new IOException();
            }
        }
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(yaGson);
        fileWriter.close();
    }
}
