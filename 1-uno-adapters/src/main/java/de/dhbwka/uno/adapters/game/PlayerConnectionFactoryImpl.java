package de.dhbwka.uno.adapters.game;

import de.dhbwka.uno.adapters.plugins.ConsolePlayerConnection;
import de.dhbwka.uno.adapters.plugins.SocketPlayerConnection;
import de.dhbwka.uno.application.game.PlayerConnection;
import de.dhbwka.uno.application.game.PlayerConnectionFactory;
import de.dhbwka.uno.application.io.ConsoleOut;
import de.dhbwka.uno.application.model.SocketNameCombination;

public class PlayerConnectionFactoryImpl implements PlayerConnectionFactory {

    private final ConsoleOut console;

    public PlayerConnectionFactoryImpl(ConsoleOut console) {
        this.console = console;
    }

    @Override
    public PlayerConnection localConnection() {
        return new ConsolePlayerConnection(console);
    }

    @Override
    public PlayerConnection remoteConnection(SocketNameCombination socketNameCombination) {
        return new SocketPlayerConnection(socketNameCombination.getSocket());
    }
}
