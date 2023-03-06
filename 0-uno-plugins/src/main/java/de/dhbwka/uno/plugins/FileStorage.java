package de.dhbwka.uno.plugins;

import de.dhbwka.uno.adapters.json.JsonConvertException;
import de.dhbwka.uno.adapters.json.JsonConverter;
import de.dhbwka.uno.adapters.json.JsonElement;
import de.dhbwka.uno.adapters.persistence.AbstractStorageRepository;
import de.dhbwka.uno.adapters.persistence.PersistenceException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileStorage implements AbstractStorageRepository {

    @Override
    public JsonElement load(String identifier) throws PersistenceException {
        String content;

        try {
            content = new String(Files.readAllBytes(pathFrom(identifier)));
        } catch (IOException ex) {
            throw new PersistenceException("Could not load file \"" + identifier + "\"", ex);
        }

        try {
            return new JsonConverter().fromJsonString(content);
        } catch (JsonConvertException ex) {
            throw new PersistenceException("File content is not JSON formatted", ex);
        }
    }

    @Override
    public void save(String identifier, JsonElement content) throws PersistenceException {
        Path path = pathFrom(identifier);

        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
            }
        } catch (IOException ex) {
            throw new PersistenceException("Could not save file \"" + identifier + "\"", ex);
        }

        try (FileWriter fileWriter = new FileWriter(identifier)) {
            fileWriter.write(content.toJsonString());
        } catch (IOException ex) {
            throw new PersistenceException("Could not write to file \"" + identifier + "\"", ex);
        }
    }

    private Path pathFrom(String identifier) {
        return Path.of("storage" + File.separator + identifier + ".json");
    }

}
