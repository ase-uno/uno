package de.dhbwka.uno.adapters.json;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class JsonObjectTest {

    @Test
    void constructor1() {
        JsonObject obj = new JsonObject();

        assertEquals(0, obj.getElements().size());
    }

    @Test
    void constructor2() {
        Map<String, JsonElement> elements = new HashMap<>();
        elements.put("name", new JsonString("Klaus"));

        JsonObject obj = new JsonObject(elements);

        assertEquals(1, obj.getElements().size());
        assertEquals("\"Klaus\"", obj.get("name").toJsonString());
    }

    @Test
    void getElements() {
        Map<String, JsonElement> elements = new HashMap<>();
        elements.put("name", new JsonNull());

        JsonObject obj = new JsonObject(elements);

        assertEquals(obj.getElements(), elements);
    }

    @Test
    void get() {
        Map<String, JsonElement> elements = new HashMap<>();
        JsonElement element = new JsonNumber(4711);
        elements.put("blub", element);

        JsonObject obj = new JsonObject(elements);

        assertEquals(obj.get("blub"), element);
    }

    @Test
    void set() {
        JsonObject obj = new JsonObject();

        assertNull(obj.get("hehe"));

        JsonElement element = new JsonNull();
        obj.set("hehe", element);

        assertEquals(obj.get("hehe"), element);
    }

    @Test
    void toJson() {
        JsonObject obj = new JsonObject();
        obj.set("peter", new JsonString("lustig"));
        obj.set("exit()", new JsonNull());

        assertEquals("{\"peter\":\"lustig\",\"exit()\":null}", obj.toJsonString());
    }

}
