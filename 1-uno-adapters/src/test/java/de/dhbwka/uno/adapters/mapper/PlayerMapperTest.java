package de.dhbwka.uno.adapters.mapper;

import de.dhbwka.uno.domain.SimplePlayer;
import de.dhbwka.uno.adapters.json.JsonElement;
import de.dhbwka.uno.adapters.json.JsonObject;
import de.dhbwka.uno.adapters.json.JsonString;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerMapperTest {

    @Test
    void playerDTOToJson() {
        SimplePlayer player = new SimplePlayer("Klaus");

        JsonElement json = PlayerMapper.playerDTOToJson(player);

        assertEquals("{\"name\":\"Klaus\"}", json.toJsonString());
    }

    @Test
    void playerDTOFromJson() {
        JsonObject json = new JsonObject();
        json.set("name", new JsonString("Klaus"));

        SimplePlayer player = PlayerMapper.playerDTOFromJson(json);

        assertEquals("Klaus", player.getName());
    }

}
