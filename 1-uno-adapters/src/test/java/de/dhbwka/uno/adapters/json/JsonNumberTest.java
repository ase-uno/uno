package de.dhbwka.uno.adapters.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonNumberTest {

    @Test
    void getValue() {
        int value = 1;

        JsonNumber number = new JsonNumber(value);

        assertEquals(number.getValue(), value);
    }

    @Test
    void toJson() {
        int value = 1;

        JsonNumber number = new JsonNumber(value);

        assertEquals(value + "", number.toJsonString());
    }

}
