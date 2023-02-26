package de.dhbwka.uno.application.client;

import de.dhbwka.uno.application.game.ConnectionInstance;
import de.dhbwka.uno.application.game.PlayerConnectionFactory;
import de.dhbwka.uno.application.io.ConsoleOut;

public class Client extends ConnectionInstance {
    private final ConsoleOut console;
    private final PlayerConnectionFactory playerIoConnectionBuilder;
    private final ConnectionInitializer connectionInitializer;

    public Client(String name, ConsoleOut console,
                  PlayerConnectionFactory playerIoConnectionBuilder,
                  ConnectionInitializer connectionInitializer) {
        super(name);

        this.console = console;
        this.playerIoConnectionBuilder = playerIoConnectionBuilder;
        this.connectionInitializer = connectionInitializer;

        connectionInitializer.connect(getLocalName(), playerIoConnectionBuilder.localConnection(), console);
    }
}
