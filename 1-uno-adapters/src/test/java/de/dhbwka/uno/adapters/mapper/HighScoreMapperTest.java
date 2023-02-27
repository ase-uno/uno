package de.dhbwka.uno.adapters.mapper;

import de.dhbwka.uno.adapters.json.JsonElement;
import de.dhbwka.uno.adapters.json.JsonNull;
import de.dhbwka.uno.adapters.json.JsonNumber;
import de.dhbwka.uno.adapters.json.JsonObject;
import de.dhbwka.uno.domain.HighScore;
import de.dhbwka.uno.domain.SimplePlayer;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HighScoreMapperTest {

    @Test
    void highScoreToJson() {
        Map<SimplePlayer, Integer> scores = new HashMap<>();
        scores.put(new SimplePlayer("Noah"), 123);
        scores.put(new SimplePlayer("Jens"), 456);
        scores.put(new SimplePlayer("Mathe Mann"), 789);

        HighScore highScore = new HighScore(scores);

        JsonObject json = HighScoreMapper.highScoreToJson(highScore);

        assertEquals("{\"Mathe Mann\":789,\"Jens\":456,\"Noah\":123}", json.toJsonString());
    }

    @Test
    void highScoreFromJsonNull() {
        JsonElement json = new JsonNull();

        HighScore highScore = HighScoreMapper.highScoreFromJson(json);

        assertEquals(0, highScore.getElements().size());
    }

    @Test
    void highScoreFromJson() {
        JsonObject json = new JsonObject();
        json.set("Noah", new JsonNumber(123));
        json.set("Jens", new JsonNumber(456));
        json.set("Mathe Mann", new JsonNumber(789));

        HighScore highScore = HighScoreMapper.highScoreFromJson(json);

        assertEquals(3, highScore.getElements().size());
        assertTrue(highScore.getElements().containsValue(123));
        assertTrue(highScore.getElements().containsValue(456));
        assertTrue(highScore.getElements().containsValue(789));
    }

}
