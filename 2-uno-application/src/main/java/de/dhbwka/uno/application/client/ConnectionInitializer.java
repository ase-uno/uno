package de.dhbwka.uno.application.client;

import de.dhbwka.uno.application.game.PlayerConnection;
import de.dhbwka.uno.application.io.ConsoleOut;

public interface ConnectionInitializer {

    void connect(String localPlayerName, PlayerConnection playerConnection, ConsoleOut console);

}
