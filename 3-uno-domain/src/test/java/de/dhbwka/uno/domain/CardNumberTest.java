package de.dhbwka.uno.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

public class CardNumberTest {

    @Test
    public void setNumberSuccess() {
        CardNumber number = new CardNumber(1);

        number.setValue(2);

        assertEquals(number.getValue(), 2);
    }

    @Test
    public void setNumberFailNeg() {
        CardNumber number = new CardNumber(1);

        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                number.setValue(-1);
            }
        });
    }

    @Test
    public void setNumberFailPos() {
        CardNumber number = new CardNumber(1);

        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                number.setValue(10);
            }
        });
    }

}
