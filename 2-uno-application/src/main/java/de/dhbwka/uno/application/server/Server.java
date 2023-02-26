package de.dhbwka.uno.application.server;

import de.dhbwka.uno.application.game.CardProvider;
import de.dhbwka.uno.application.game.ConnectionInstance;
import de.dhbwka.uno.application.game.Game;
import de.dhbwka.uno.application.io.ConsoleOut;
import de.dhbwka.uno.application.model.PlayerWithConnection;
import de.dhbwka.uno.application.model.SimplePlayerWithConnection;
import de.dhbwka.uno.application.persistance.HighScoreStorageRepository;
import de.dhbwka.uno.domain.Card;
import de.dhbwka.uno.domain.CardStack;
import de.dhbwka.uno.domain.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Server extends ConnectionInstance {
    private final ConnectionServer connectionServer;
    private final SimplePlayerWithConnection localPlayer;

    private final ConsoleOut console;
    private final CardProvider cardProvider;

    private boolean waitingForPlayers = true;

    private final List<SimplePlayerWithConnection> players = new ArrayList<>();
    private List<PlayerWithConnection> players2 = new ArrayList<>();
    private List<Card> cards;
    private Game game;

    private final HighScoreStorageRepository highScoreStorageRepository;

    public Server(SimplePlayerWithConnection localPlayer,
                  ConnectionServer connectionServer,
                  ConsoleOut console,
                  CardProvider cardProvider,
                  HighScoreStorageRepository highScoreStorageRepository) {
        super(localPlayer.simplePlayer().getName());

        this.localPlayer = localPlayer;
        this.connectionServer = connectionServer;
        this.console = console;
        this.cardProvider = cardProvider;
        this.highScoreStorageRepository = highScoreStorageRepository;

        startServer();

        initCards();
        initPlayers();
        initGame();

        game.start();

        closeServer();
    }

    private void initCards() {
        cards = cardProvider.listAllCards();
        Collections.shuffle(cards);
    }

    private CardStack getPlayerCards() {
        List<Card> playerCards = cards.subList(0, 7);
        cards = cards.subList(7, cards.size());
        return new CardStack(playerCards);
    }

    private void initPlayers() {
        players2 = new ArrayList<>();

        players2.add(new PlayerWithConnection(
                new Player(getLocalName(), getPlayerCards()),
                localPlayer.playerConnection()));

        for (SimplePlayerWithConnection spwc : this.players) {
            Player p = new Player(spwc.simplePlayer().getName(), getPlayerCards());
            PlayerWithConnection pwc = new PlayerWithConnection(p, spwc.playerConnection());
            players2.add(pwc);
        }
    }

    private void initGame() {
        Card activeCard;
        int i = -1;
        do {
            activeCard = cards.get(++i);
        } while (activeCard.hasAction());
        cards.remove(i);

        game = new Game(players2, new CardStack(cards), activeCard, highScoreStorageRepository);
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
