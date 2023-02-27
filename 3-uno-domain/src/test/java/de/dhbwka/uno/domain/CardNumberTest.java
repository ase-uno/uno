package de.dhbwka.uno.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CardNumberTest {

    @Test
    public void setNumberSuccess() {
        for (int i = 0; i <= 9; i++) {
            CardNumber number = new CardNumber(i);
            assertEquals(number.getValue(), i);
        }
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
