package de.dhbwka.uno.adapters.persistence;

import de.dhbwka.uno.adapters.json.JsonElement;

public interface AbstractStorageRepository {

    JsonElement getFile(String fileName);
    void storeFile(String fileName, JsonElement content);

}
