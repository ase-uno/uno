package de.dhbwka.uno.plugins.client;

import de.dhbwka.uno.application.client.ConnectionInitializer;
import de.dhbwka.uno.application.game.PlayerConnection;
import de.dhbwka.uno.application.io.ConsoleOut;

import java.io.IOException;
import java.util.Scanner;

public class ConnectionInitializerImpl implements ConnectionInitializer {
    private final int port;

    public ConnectionInitializerImpl(int port) {
        this.port = port;
    }

    @Override
    public void connect(String localPlayerName, PlayerConnection playerConnection, ConsoleOut console) {
        console.println("Ziel-IP: ");

        Scanner scanner = new Scanner(System.in);
        String ip = scanner.nextLine();

        try {
            new SocketConnection(ip, port, localPlayerName, playerConnection, console);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
