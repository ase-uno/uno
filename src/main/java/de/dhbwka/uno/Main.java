package de.dhbwka.uno;

import de.dhbwka.uno.adapters.client.SocketConnectionInitializerImpl;
import de.dhbwka.uno.adapters.game.PlayerConnectionFactoryImpl;
import de.dhbwka.uno.adapters.model.SocketNameCombinationFactoryImpl;
import de.dhbwka.uno.adapters.persistence.FileStorage;
import de.dhbwka.uno.adapters.persistence.HighScoreStorage;
import de.dhbwka.uno.adapters.plugins.CardGenerator;
import de.dhbwka.uno.adapters.plugins.ConsoleAdapter;
import de.dhbwka.uno.application.client.Client;
import de.dhbwka.uno.application.io.ConsoleColor;
import de.dhbwka.uno.application.io.ConsoleOut;
import de.dhbwka.uno.application.server.Server;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static final ConsoleOut console = new ConsoleAdapter();

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

    private static void start(String name, int mode) throws IOException {

        if(mode == 0) {
            new Server(name,
                    console,
                    new CardGenerator(),
                    new HighScoreStorage(new FileStorage()),
                    new PlayerConnectionFactoryImpl(console),
                    new SocketNameCombinationFactoryImpl());
        } else {
            new Client(name,
                    console,
                    new PlayerConnectionFactoryImpl(console),
                    new SocketConnectionInitializerImpl());
        }
    }

}
