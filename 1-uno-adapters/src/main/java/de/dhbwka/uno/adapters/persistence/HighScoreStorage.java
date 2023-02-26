package de.dhbwka.uno.adapters.persistence;

import de.dhbwka.uno.adapters.json.JsonNumber;
import de.dhbwka.uno.adapters.json.JsonObject;
import de.dhbwka.uno.adapters.mapper.HighScoreMapper;
import de.dhbwka.uno.application.persistance.HighScoreStorageRepository;
import de.dhbwka.uno.domain.HighScore;
import de.dhbwka.uno.domain.SimplePlayer;

import java.io.File;

public class HighScoreStorage implements HighScoreStorageRepository {

    private final AbstractStorageRepository abstractStorageRepository;
    private static final String FILE_PATH = "storage" + File.separator + "highscore.json";

    public HighScoreStorage(AbstractStorageRepository abstractStorageRepository) {
        this.abstractStorageRepository = abstractStorageRepository;
    }

    @Override
    public void addWin(SimplePlayer player) {

        JsonObject jsonObject = getHighScoreFile();
        System.out.println(jsonObject.toJsonString());
        int wins = getWins(jsonObject, player);
        System.out.println(wins);
        jsonObject.set(player.getName(), new JsonNumber(wins + 1));
        System.out.println(jsonObject.toJsonString());
        abstractStorageRepository.storeFile(FILE_PATH, jsonObject);

    }

    @Override
    public HighScore getHighScore() {

        JsonObject jsonObject = getHighScoreFile();
        return HighScoreMapper.highScoreFromJson(jsonObject);

    }

    private JsonObject getHighScoreFile() {
        JsonObject jsonObject = (JsonObject) abstractStorageRepository.getFile(FILE_PATH);
        return jsonObject != null ? jsonObject : new JsonObject();
    }

    private int getWins(JsonObject highScoreFile, SimplePlayer player) {
        JsonNumber winsJson = (JsonNumber) highScoreFile.get(player.getName());
        return winsJson != null ? winsJson.getValue().intValue() : 0;
    }
}
