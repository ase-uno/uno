package de.dhbwka.uno.application.client;

import de.dhbwka.uno.application.game.ConnectionInstance;
import de.dhbwka.uno.application.game.PlayerConnectionFactory;
import de.dhbwka.uno.application.io.ConsoleOut;

import java.io.IOException;
import java.util.Scanner;

public class Client extends ConnectionInstance {

    private final ConsoleOut console;
    private final PlayerConnectionFactory playerIoConnectionBuilder;
    private final SocketConnectionInitializer socketConnectionInitializer;

    public Client(String name, ConsoleOut console,
                  PlayerConnectionFactory playerIoConnectionBuilder,
                  SocketConnectionInitializer socketConnectionInitializer) {
        super(name);
        this.console = console;
        this.playerIoConnectionBuilder = playerIoConnectionBuilder;
        this.socketConnectionInitializer = socketConnectionInitializer;

        try {
            connect();
        } catch (Exception exception) {
            console.error("");
        }
    }

    private void connect() throws IOException {
        console.println("Ziel-IP:");

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        socketConnectionInitializer.connect(input, getLocalName(), playerIoConnectionBuilder.localConnection(), console);
    }
}
