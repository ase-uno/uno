package de.dhbwka.uno.adapters.mapper;

import de.dhbwka.uno.adapters.json.JsonElement;
import de.dhbwka.uno.adapters.json.JsonNull;
import de.dhbwka.uno.adapters.json.JsonNumber;
import de.dhbwka.uno.adapters.json.JsonObject;
import de.dhbwka.uno.domain.HighScore;
import de.dhbwka.uno.domain.SimplePlayer;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HighScoreMapper {

    private HighScoreMapper() {
    }

    public static JsonObject highScoreToJson(HighScore highScore) {
        Map<String, JsonElement> elements = highScore.getElements()
                .entrySet()
                .stream()
                .map(e -> new AbstractMap.SimpleEntry<>(e.getKey().getName(), new JsonNumber(e.getValue())))
                .collect(Collectors.toMap(
                        AbstractMap.SimpleEntry::getKey,
                        AbstractMap.SimpleEntry::getValue
                ));

        return new JsonObject(elements);
    }

    public static HighScore highScoreFromJson(JsonElement jsonElement) {
        if (jsonElement instanceof JsonNull) return new HighScore();

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

}
