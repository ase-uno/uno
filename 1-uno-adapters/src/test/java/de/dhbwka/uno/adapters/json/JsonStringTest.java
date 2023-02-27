package de.dhbwka.uno.adapters.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonStringTest {

    @Test
    void getValue() {
        String s = "Wird hier gebaut?";

        JsonString string = new JsonString(s);

        assertEquals(string.value(), s);
    }

    @Test
    void toJson() {
        JsonString string = new JsonString("I bims 1 String");

        assertEquals("\"I bims 1 String\"", string.toJsonString());
    }

    @Test
    void toJsonEscape1() {
        JsonString string = new JsonString("\\");

        assertEquals("\"\\\\\"", string.toJsonString());
    }

    @Test
    void toJsonEscape2() {
        JsonString string = new JsonString("\\\\");

        assertEquals("\"\\\\\\\\\"", string.toJsonString());
    }

}
