package de.dhbwka.uno.adapters.mapper;

import de.dhbwka.uno.domain.SimplePlayer;
import de.dhbwka.uno.adapters.json.JsonElement;
import de.dhbwka.uno.adapters.json.JsonObject;
import de.dhbwka.uno.adapters.json.JsonString;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerMapperTest {

    @Test
    public void playerDTOToJson() {
        SimplePlayer player = new SimplePlayer("Klaus");

        JsonElement json = PlayerMapper.playerDTOToJson(player);

        assertEquals(json.toJson(), "{\"name\":\"Klaus\"}");
    }

    @Test
    public void playerDTOFromJson() {
        JsonObject json = new JsonObject();
        json.set("name", new JsonString("Klaus"));

        SimplePlayer player = PlayerMapper.playerDTOFromJson(json);

        assertEquals(player.getName(), "Klaus");
    }

}
