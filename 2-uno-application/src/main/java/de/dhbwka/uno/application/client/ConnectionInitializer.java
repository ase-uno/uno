package de.dhbwka.uno.application.client;

import de.dhbwka.uno.application.game.PlayerConnection;
import de.dhbwka.uno.application.io.Console;

public interface ConnectionInitializer {

    void connect(String localPlayerName, PlayerConnection playerConnection, Console console);

}
