package de.dhbwka.uno.domain;

import java.util.Objects;

public class CardNumber {
    private int value;

    public CardNumber(int value) {
        setValue(value);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        if (value < 0 || value > 9) {
            throw new IllegalArgumentException("Numbers only allowed in Interval [0, 9]");
        }
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardNumber that = (CardNumber) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "CardNumber{" +
                "value=" + value +
                '}';
    }

}
