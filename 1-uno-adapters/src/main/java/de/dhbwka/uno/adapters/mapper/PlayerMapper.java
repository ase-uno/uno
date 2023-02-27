package de.dhbwka.uno.adapters.mapper;

import de.dhbwka.uno.adapters.json.JsonElement;
import de.dhbwka.uno.adapters.json.JsonObject;
import de.dhbwka.uno.adapters.json.JsonString;
import de.dhbwka.uno.domain.SimplePlayer;

import java.util.HashMap;

public class PlayerMapper {

    private static final String PLAYERDTO_NAME = "name";


    private PlayerMapper() {
    }

    public static JsonElement playerDTOToJson(SimplePlayer player) {
        HashMap<String, JsonElement> props = new HashMap<>();
        props.put(PLAYERDTO_NAME, new JsonString(player.getName()));

        return new JsonObject(props);
    }

    public static SimplePlayer playerDTOFromJson(JsonElement jsonElement) {
        JsonObject jsonObject = (JsonObject) jsonElement;
        String name = ((JsonString) jsonObject.get(PLAYERDTO_NAME)).getValue();

        return new SimplePlayer(name);
    }

}
