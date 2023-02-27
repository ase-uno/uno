package de.dhbwka.uno.application.server;

import de.dhbwka.uno.application.game.CardProvider;
import de.dhbwka.uno.application.game.ConnectionInstance;
import de.dhbwka.uno.application.io.Console;
import de.dhbwka.uno.application.game.GameInitializer;
import de.dhbwka.uno.application.io.Console;
import de.dhbwka.uno.application.model.SimplePlayerWithConnection;
import de.dhbwka.uno.application.persistance.HighScoreStorageRepository;
import de.dhbwka.uno.domain.CardStack;
import de.dhbwka.uno.domain.Player;

import java.util.ArrayList;
import java.util.List;

public class Server extends ConnectionInstance {
    private final ConnectionServer connectionServer;
    private final SimplePlayerWithConnection localPlayer;

    private final Console console;

    private boolean waitingForPlayers = true;

    private final List<SimplePlayerWithConnection> players = new ArrayList<>();

    public Server(SimplePlayerWithConnection localPlayer,
                  ConnectionServer connectionServer,
                  Console console,
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
        connectionServer.registerConnectDecider(player -> waitingForPlayers);
        connectionServer.registerConnectDecider(newPlayer -> {
            if(this.localPlayer.simplePlayer().getName().equals(newPlayer.getName())) return false;

            return players.stream()
                    .map(simplePlayerWithConnection -> simplePlayerWithConnection.simplePlayer().getName())
                    .noneMatch(existingPlayerName -> newPlayer.getName().equals(existingPlayerName));
        });

        connectionServer.onUserJoined(player -> {
            players.add(player);
            console.println("Player " + player.simplePlayer().getName() + " connected!");
        });
        connectionServer.startServer();

        console.println("Waiting for players");
        console.println("Press Enter to proceed");

        while (players.isEmpty()) {
            console.readLine();

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
