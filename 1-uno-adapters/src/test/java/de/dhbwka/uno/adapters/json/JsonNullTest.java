package de.dhbwka.uno.adapters.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonNullTest {

    @Test
    public void toJson() {
        JsonNull jsonNull = new JsonNull();

        assertEquals(jsonNull.toJson(), "null");
    }

}
