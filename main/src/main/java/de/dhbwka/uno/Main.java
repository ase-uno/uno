package de.dhbwka.uno;

import de.dhbwka.uno.adapters.persistence.HighScoreStorage;
import de.dhbwka.uno.application.client.Client;
import de.dhbwka.uno.application.game.CardProviderImpl;
import de.dhbwka.uno.application.game.PlayerConnection;
import de.dhbwka.uno.application.io.ConsoleColor;
import de.dhbwka.uno.application.io.ConsoleOut;
import de.dhbwka.uno.application.model.SimplePlayerWithConnection;
import de.dhbwka.uno.application.server.Server;
import de.dhbwka.uno.domain.SimplePlayer;
import de.dhbwka.uno.plugins.ConnectionServerSocket;
import de.dhbwka.uno.plugins.ConsoleOutImpl;
import de.dhbwka.uno.plugins.FileStorage;
import de.dhbwka.uno.plugins.client.ConnectionInitializerImpl;
import de.dhbwka.uno.plugins.game.PlayerConnectionFactoryImpl;
import de.dhbwka.uno.plugins.server.ConsolePlayerConnection;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static final int PORT = 9999;

    private static final ConsoleOut console = new ConsoleOutImpl();

    public static void main(String[] args) throws IOException {
        console.println(ConsoleColor.YELLOW, "Willkommen");

        console.println();

        String name = inputName();
        int mode = inputMode();
        start(name, mode);
    }

    private static String inputName() {
        console.println("Wie heißt du?");

        Scanner scanner = new Scanner(System.in);

        String name;
        do {
            name = scanner.nextLine();
        } while (name.strip().length() == 0);

        console.println();

        return name;
    }


    private static int inputMode() {

        Scanner scanner = new Scanner(System.in);

        console.println("0) Server öffnen");
        console.println("1) Mit anderem Server verbinden");
        console.println();
        console.println("Input:");

        int input = -1;
        do {
            try {
                input = scanner.nextInt();
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
                    new PlayerConnectionFactoryImpl(console),
                    new ConnectionInitializerImpl(PORT));
        }
    }

}
