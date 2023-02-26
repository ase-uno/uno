package de.dhbwka.uno.adapters.persistence;

import de.dhbwka.uno.adapters.json.JsonConverter;
import de.dhbwka.uno.adapters.json.JsonElement;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileStorage implements AbstractStorageRepository {
    @Override
    public JsonElement getFile(String fileName) {

        try {
            String content = new String(Files.readAllBytes(Path.of(fileName)));
            return new JsonConverter().fromJsonString(content);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void storeFile(String fileName, JsonElement content) {
        Path path = Path.of(fileName);
        try {
            if(!Files.exists(path)) {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
            }
        } catch (Exception e) {
            return;
        }
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(content.toJsonString());
        } catch (IOException ignored) {
            //if it does not work, we just ignore it :)
        }
    }
}
