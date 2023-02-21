package de.dhbwka.uno.application.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionInstanceTest {

    @Test
    void getLocalName() {
        final String name = "Heinrich Schwarz";

        ConnectionInstance con = new ConnectionInstance(name);

        assertEquals(con.getLocalName(), name);
    }
}
