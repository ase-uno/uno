package de.dhbwka.uno.domain;

import java.util.Objects;

public class CardNumber {
    private Integer value;

    public CardNumber(Integer value) {
        setValue(value);
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        if(value < 0 || value > 9) {
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
