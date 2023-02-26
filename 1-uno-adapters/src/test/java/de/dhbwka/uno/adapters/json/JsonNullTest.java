package de.dhbwka.uno.adapters.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonNullTest {

    @Test
    void toJson() {
        JsonNull jsonNull = new JsonNull();

        assertEquals("null", jsonNull.toJsonString());
    }

}
