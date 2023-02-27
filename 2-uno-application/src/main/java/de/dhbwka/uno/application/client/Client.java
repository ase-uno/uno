package de.dhbwka.uno.application.client;

import de.dhbwka.uno.application.game.ConnectionInstance;
import de.dhbwka.uno.application.game.PlayerConnection;
import de.dhbwka.uno.application.io.Console;

public class Client extends ConnectionInstance {

    public Client(String name, Console console,
                  PlayerConnection playerConnection,
                  ConnectionInitializer connectionInitializer) {
        super(name);

        connectionInitializer.connect(getLocalName(), playerConnection, console);
    }

}
