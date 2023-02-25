package de.dhbwka.uno.adapters.json;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonObjectTest {

    @Test
    public void constructor1() {
        JsonObject obj = new JsonObject();

        assertEquals(obj.getElements().size(), 0);
    }

    @Test
    public void constructor2() {
        Map<String, JsonElement> elements = new HashMap<>();
        elements.put("name", new JsonString("Klaus"));

        JsonObject obj = new JsonObject(elements);

        assertEquals(obj.getElements().size(), 1);
        assertEquals(obj.get("name").toJson(), "\"Klaus\"");
    }

    @Test
    public void getElements() {
        Map<String, JsonElement> elements = new HashMap<>();
        elements.put("name", new JsonNull());

        JsonObject obj = new JsonObject(elements);

        assertEquals(obj.getElements(), elements);
    }

    @Test
    public void get() {
        Map<String, JsonElement> elements = new HashMap<>();
        JsonElement element = new JsonNumber(4711);
        elements.put("blub", element);

        JsonObject obj = new JsonObject(elements);

        assertEquals(obj.get("blub"), element);
    }

    @Test
    public void set() {
        JsonObject obj = new JsonObject();

        assertEquals(obj.get("hehe"), null);

        JsonElement element = new JsonNull();
        obj.set("hehe", element);

        assertEquals(obj.get("hehe"), element);
    }

    @Test
    public void toJson() {
        JsonObject obj = new JsonObject();
        obj.set("peter", new JsonString("lustig"));
        obj.set("exit()", new JsonNull());

        assertEquals(obj.toJson(), "{\"peter\":\"lustig\",\"exit()\":null}");
    }

}
