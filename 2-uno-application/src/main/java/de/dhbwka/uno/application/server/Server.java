package de.dhbwka.uno.application.server;

import de.dhbwka.uno.application.game.CardProvider;
import de.dhbwka.uno.application.game.ConnectionInstance;
import de.dhbwka.uno.application.game.GameInitializer;
import de.dhbwka.uno.application.io.ConsoleOut;
import de.dhbwka.uno.application.model.SimplePlayerWithConnection;
import de.dhbwka.uno.application.persistance.HighScoreStorageRepository;
import de.dhbwka.uno.domain.CardStack;
import de.dhbwka.uno.domain.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server extends ConnectionInstance {
    private final ConnectionServer connectionServer;
    private final SimplePlayerWithConnection localPlayer;

    private final ConsoleOut console;

    private boolean waitingForPlayers = true;

    private final List<SimplePlayerWithConnection> players = new ArrayList<>();

    public Server(SimplePlayerWithConnection localPlayer,
                  ConnectionServer connectionServer,
                  ConsoleOut console,
                  CardProvider cardProvider,
                  HighScoreStorageRepository highScoreStorageRepository) {
        super(localPlayer.simplePlayer().getName());

        this.localPlayer = localPlayer;
        this.connectionServer = connectionServer;
        this.console = console;

        startServer();

        startGame(cardProvider, highScoreStorageRepository);

        closeServer();
    }

    private void startGame(CardProvider cardProvider, HighScoreStorageRepository highScoreStorageRepository) {
        this.players.add(new SimplePlayerWithConnection(
                new Player(getLocalName(), new CardStack(new ArrayList<>())),
                localPlayer.playerConnection()
        ));

        GameInitializer.startNewGame(cardProvider, players, highScoreStorageRepository);
    }

    private void startServer() {
        connectionServer.startServer();
        connectionServer.registerConnectDecider((player) -> {
            if (!waitingForPlayers) {
                return false;
            }

            players.add(player);
            console.println("Player " + player.simplePlayer().getName() + " connected!");

            return true;
        });

        console.println("Waiting for players");
        console.println("Press Enter to proceed");

        Scanner scanner = new Scanner(System.in); // TODO: -> plugin
        while (players.isEmpty()) {
            scanner.nextLine();

            if (players.isEmpty()) {
                console.error("No players connected yet, not starting...");
            }
        }

        waitingForPlayers = false;
    }

    private void closeServer() {
        connectionServer.stopServer();
    }

}
