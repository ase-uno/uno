package de.dhbwka.uno.adapters.persistence;

import de.dhbwka.uno.adapters.json.JsonElement;

public interface AbstractStorageRepository {

    JsonElement load(String identifier) throws PersistenceException;

    void save(String identifier, JsonElement content) throws PersistenceException;

}
