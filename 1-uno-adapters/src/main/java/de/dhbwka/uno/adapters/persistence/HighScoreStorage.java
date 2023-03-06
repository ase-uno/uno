package de.dhbwka.uno.adapters.persistence;

import de.dhbwka.uno.adapters.json.JsonNumber;
import de.dhbwka.uno.adapters.json.JsonObject;
import de.dhbwka.uno.adapters.mapper.HighScoreMapper;
import de.dhbwka.uno.application.persistence.HighScoreStorageRepository;
import de.dhbwka.uno.domain.HighScore;
import de.dhbwka.uno.domain.SimplePlayer;

public class HighScoreStorage implements HighScoreStorageRepository {

    private final AbstractStorageRepository abstractStorageRepository;
    private static final String STORAGE_IDENTIFIER = "highscore";

    public HighScoreStorage(AbstractStorageRepository abstractStorageRepository) {
        this.abstractStorageRepository = abstractStorageRepository;
    }

    @Override
    public void addWin(SimplePlayer player) {
        JsonObject jsonObject = getHighScoreFile();

        int wins = getWins(jsonObject, player);

        jsonObject.set(player.getName(), new JsonNumber(wins + 1));

        try {
            abstractStorageRepository.save(STORAGE_IDENTIFIER, jsonObject);
        } catch (PersistenceException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public HighScore getHighScore() {
        JsonObject jsonObject = getHighScoreFile();
        return HighScoreMapper.highScoreFromJson(jsonObject);
    }

    private JsonObject getHighScoreFile() {
        JsonObject jsonObject;

        try {
            jsonObject = (JsonObject) abstractStorageRepository.load(STORAGE_IDENTIFIER);
        } catch (PersistenceException ex) {
            ex.printStackTrace();
            return new JsonObject();
        }

        return jsonObject;
    }

    private int getWins(JsonObject highScoreFile, SimplePlayer player) {
        JsonNumber winsJson = (JsonNumber) highScoreFile.get(player.getName());
        return winsJson != null ? winsJson.getValue().intValue() : 0;
    }

}
