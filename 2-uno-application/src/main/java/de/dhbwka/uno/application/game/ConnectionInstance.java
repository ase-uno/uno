package de.dhbwka.uno.application.game;

public class ConnectionInstance {
    private final String name;

    public ConnectionInstance(String name) {
        this.name = name;
    }

    public String getLocalName() {
        return name;
    }

}
