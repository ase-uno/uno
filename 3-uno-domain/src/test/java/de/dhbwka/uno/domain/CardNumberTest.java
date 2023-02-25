package de.dhbwka.uno.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

public class CardNumberTest {

    @Test
    public void setNumberSuccess() {
        CardNumber number = new CardNumber(2);

        assertEquals(number.getValue(), 2);
    }

    @Test
    public void setNumberFailNeg() {
        assertThrows(IllegalArgumentException.class, () -> new CardNumber(-1));
    }

    @Test
    public void setNumberFailPos() {
        assertThrows(IllegalArgumentException.class, () -> new CardNumber(10));
    }

}
