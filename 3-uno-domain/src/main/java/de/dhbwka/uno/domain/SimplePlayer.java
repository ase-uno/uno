package de.dhbwka.uno.domain;

import java.util.Objects;

/**
 * Repräsentiert einen Spieler bevor das Spiel begonnen hat
 */
public class SimplePlayer {
    private final String name;

    public SimplePlayer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(!(o instanceof SimplePlayer that)) return false;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
