package de.dhbwka.uno.plugins.game;

import de.dhbwka.uno.application.game.PlayerConnection;
import de.dhbwka.uno.application.game.PlayerConnectionFactory;
import de.dhbwka.uno.application.io.Console;
import de.dhbwka.uno.plugins.server.ConsolePlayerConnection;

public class PlayerConnectionFactoryImpl implements PlayerConnectionFactory {

    private final Console console;

    public PlayerConnectionFactoryImpl(Console console) {
        this.console = console;
    }

    @Override
    public PlayerConnection localConnection() {
        return new ConsolePlayerConnection(console);
    }

}
