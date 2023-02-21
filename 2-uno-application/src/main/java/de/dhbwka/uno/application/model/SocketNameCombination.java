package de.dhbwka.uno.application.model;

import java.net.Socket;

public class SocketNameCombination {

    private final Socket socket;
    private final String name;

    public SocketNameCombination(Socket socket, String name) {
        this.socket = socket;
        this.name = name;
    }

    public Socket getSocket() {
        return socket;
    }

    public String getName() {
        return name;
    }
}
