package de.dhbwka.uno.adapters.client;

import de.dhbwka.uno.application.client.SocketConnectionInitializer;
import de.dhbwka.uno.application.game.PlayerConnection;
import de.dhbwka.uno.application.io.ConsoleOut;

import java.io.IOException;

public class SocketConnectionInitializerImpl implements SocketConnectionInitializer {
    @Override
    public void connect(String remoteIpAddress, String localPlayerName, PlayerConnection playerConnection, ConsoleOut console) throws IOException {
        new SocketConnection(remoteIpAddress, localPlayerName, playerConnection, console);
    }
}
