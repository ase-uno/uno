package de.dhbwka.uno.adapters.json;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonArrayTest {

    @Test
    void getElements() {
        List<JsonElement> elements = new ArrayList<>();
        elements.add(new JsonNull());

        JsonArray arr = new JsonArray(elements);

        assertEquals(arr.getElements(), elements);
    }

    @Test
    void toJson() {
        List<JsonElement> elements = new ArrayList<>();
        elements.add(new JsonNull());
        elements.add(new JsonNumber(4711));
        elements.add(new JsonString("hehe"));

        JsonArray arr = new JsonArray(elements);

        assertEquals("[null,4711,\"hehe\"]", arr.toJsonString());
    }

}
