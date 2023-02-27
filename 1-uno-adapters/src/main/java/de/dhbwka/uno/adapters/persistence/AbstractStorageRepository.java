package de.dhbwka.uno.adapters.persistence;

import de.dhbwka.uno.adapters.json.JsonElement;

public interface AbstractStorageRepository {

    JsonElement load(String identifier);

    void save(String identifier, JsonElement content);

}
