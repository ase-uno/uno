package de.dhbwka.uno;

import de.dhbwka.uno.adapters.persistence.HighScoreStorage;
import de.dhbwka.uno.application.client.Client;
import de.dhbwka.uno.application.game.CardProviderImpl;
import de.dhbwka.uno.application.game.PlayerConnection;
import de.dhbwka.uno.application.io.Console;
import de.dhbwka.uno.application.io.ConsoleColor;
import de.dhbwka.uno.application.model.SimplePlayerWithConnection;
import de.dhbwka.uno.application.server.Server;
import de.dhbwka.uno.domain.SimplePlayer;
import de.dhbwka.uno.plugins.ConsoleImpl;
import de.dhbwka.uno.plugins.FileStorage;
import de.dhbwka.uno.plugins.client.ConnectionInitializerImpl;
import de.dhbwka.uno.plugins.server.ConnectionServerSocket;
import de.dhbwka.uno.plugins.server.ConsolePlayerConnection;

public class Main {
    private static final int PORT = 9999;

    private static final Console console = new ConsoleImpl();

    public static void main(String[] args) {
        console.println(ConsoleColor.YELLOW, "Willkommen");

        console.println();

        String name = inputName();
        int mode = inputMode();
        start(name, mode);
    }

    private static String inputName() {
        console.println("Wie heißt du?");

        String name;
        do {
            name = console.readLine();
        } while (name.strip().length() == 0);

        console.println();

        return name;
    }


    private static int inputMode() {

        console.println("0) Server öffnen");
        console.println("1) Mit anderem Server verbinden");
        console.println();
        console.println("Input:");

        int input = -1;
        do {
            try {
                input = console.readInt();
            } catch (Exception ignored) {
                //if an error occurs, new user-input is requested by the loop
            }
        } while (input < 0 || input > 1);

        return input;
    }

    private static void start(String name, int mode) {

        if (mode == 0) {
            SimplePlayer player = new SimplePlayer(name);
            PlayerConnection connection = new ConsolePlayerConnection(console);

            new Server(new SimplePlayerWithConnection(player, connection),
                    new ConnectionServerSocket(PORT),
                    console,
                    new CardProviderImpl(),
                    new HighScoreStorage(new FileStorage()));
        } else {
            new Client(name, console,
                    new ConsolePlayerConnection(console),
                    new ConnectionInitializerImpl(PORT));
        }
    }

}
