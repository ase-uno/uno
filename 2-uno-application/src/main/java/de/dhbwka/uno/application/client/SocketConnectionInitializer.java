package de.dhbwka.uno.application.client;

import de.dhbwka.uno.application.game.PlayerConnection;
import de.dhbwka.uno.application.io.ConsoleOut;

import java.io.IOException;

public interface SocketConnectionInitializer {

    void connect(String remoteIpAddress, String localPlayerName, PlayerConnection playerConnection, ConsoleOut console) throws IOException;

}
