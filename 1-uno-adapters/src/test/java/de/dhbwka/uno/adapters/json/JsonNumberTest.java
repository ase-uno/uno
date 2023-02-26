package de.dhbwka.uno.adapters.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonNumberTest {

    @Test
    public void getValue() {
        int value = 1;

        JsonNumber number = new JsonNumber(value);

        assertEquals(number.getValue(), value);
    }

    @Test
    public void toJson() {
        int value = 1;

        JsonNumber number = new JsonNumber(value);

        assertEquals(number.toJson(), value + "");
    }

}
