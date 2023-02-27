package de.dhbwka.uno.plugins.client;

import de.dhbwka.uno.application.client.ConnectionInitializer;
import de.dhbwka.uno.application.game.PlayerConnection;
import de.dhbwka.uno.application.io.Console;

import java.io.IOException;

public class ConnectionInitializerImpl implements ConnectionInitializer {
    private final int port;

    public ConnectionInitializerImpl(int port) {
        this.port = port;
    }

    @Override
    public void connect(String localPlayerName, PlayerConnection playerConnection, Console console) {
        console.println("Ziel-IP: ");

        String ip = console.readLine();

        try {
            new SocketConnection(ip, port, localPlayerName, playerConnection, console);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
