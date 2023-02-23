package de.dhbwka.uno.adapters.persistence;

import de.dhbwka.uno.adapters.json.JsonElement;
import de.dhbwka.uno.adapters.json.JsonNull;
import de.dhbwka.uno.adapters.json.JsonNumber;
import de.dhbwka.uno.adapters.json.JsonObject;
import de.dhbwka.uno.application.persistance.HighScoreStorageRepository;
import de.dhbwka.uno.domain.HighScore;
import de.dhbwka.uno.domain.SimplePlayer;

import java.io.File;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.stream.Collectors;

public class HighScoreStorage implements HighScoreStorageRepository {

    private final AbstractStorageRepository abstractStorageRepository;
    private static final String FILE_PATH = "storage" + File.separator + "highscore.json";

    public HighScoreStorage(AbstractStorageRepository abstractStorageRepository) {
        this.abstractStorageRepository = abstractStorageRepository;
    }

    @Override
    public void addWin(SimplePlayer player) {

        JsonObject jsonObject = getHighScoreFile();
        System.out.println(jsonObject.toJson());
        int wins = getWins(jsonObject, player);
        System.out.println(wins);
        jsonObject.set(player.getName(), new JsonNumber(wins + 1));
        System.out.println(jsonObject.toJson());
        abstractStorageRepository.storeFile(FILE_PATH, jsonObject);

    }

    @Override
    public HighScore getHighScore() {

        JsonObject jsonObject = getHighScoreFile();
        return highScoreFromJson(jsonObject);

    }

    private HighScore highScoreFromJson(JsonElement jsonElement) {
        if(jsonElement instanceof JsonNull) return new HighScore();

        JsonObject jsonObject = (JsonObject) jsonElement;

        HashMap<SimplePlayer, Integer> data = jsonObject.getElements()
                .entrySet()
                .stream()
                .map(e -> new AbstractMap.SimpleEntry<>(new SimplePlayer(e.getKey()), ((JsonNumber) e.getValue()).getValue().intValue()))
                .collect(Collectors.toMap(
                        AbstractMap.SimpleEntry::getKey,
                        AbstractMap.SimpleEntry::getValue,
                        Integer::sum,
                        HashMap::new
                ));

        return new HighScore(data);
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
