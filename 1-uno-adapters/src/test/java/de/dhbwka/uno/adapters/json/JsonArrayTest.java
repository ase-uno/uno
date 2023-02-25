package de.dhbwka.uno.adapters.json;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonArrayTest {

    @Test
    public void getElements() {
        List<JsonElement> elements = new ArrayList<>();
        elements.add(new JsonNull());

        JsonArray arr = new JsonArray(elements);

        assertEquals(arr.getElements(), elements);
    }

    @Test
    public void toJson() {
        List<JsonElement> elements = new ArrayList<>();
        elements.add(new JsonNull());
        elements.add(new JsonNumber(4711));
        elements.add(new JsonString("hehe"));

        JsonArray arr = new JsonArray(elements);

        assertEquals(arr.toJson(), "[null,4711,\"hehe\"]");
    }

}
