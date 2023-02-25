package de.dhbwka.uno.application.server;

import de.dhbwka.uno.application.config.ConnectionConfig;
import de.dhbwka.uno.application.game.*;
import de.dhbwka.uno.application.io.ConsoleOut;
import de.dhbwka.uno.application.model.PlayerWithConnection;
import de.dhbwka.uno.application.model.SocketNameCombination;
import de.dhbwka.uno.application.model.SocketNameCombinationFactory;
import de.dhbwka.uno.application.persistance.HighScoreStorageRepository;
import de.dhbwka.uno.domain.Card;
import de.dhbwka.uno.domain.CardStack;
import de.dhbwka.uno.domain.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Server extends ConnectionInstance {

    private Game game;

    private List<PlayerWithConnection> players;
    private List<Card> cards;
    private ServerSocket serverSocket;
    private boolean isServerSearchingForInput = true;
    private final List<SocketNameCombination> connections = new ArrayList<>();

    private final ConsoleOut console;
    private final CardProvider cardProvider;

    private final HighScoreStorageRepository highScoreStorageRepository;

    private final PlayerConnectionFactory playerConnectionFactory;
    private final SocketNameCombinationFactory socketNameCombinationFactory;

    public Server(String localName,
                  ConsoleOut console,
                  CardProvider cardProvider,
                  HighScoreStorageRepository highScoreStorageRepository,
                  PlayerConnectionFactory playerConnectionFactory,
                  SocketNameCombinationFactory socketNameCombinationFactory) throws IOException {
        super(localName);
        this.console = console;
        this.cardProvider = cardProvider;
        this.highScoreStorageRepository = highScoreStorageRepository;
        this.playerConnectionFactory = playerConnectionFactory;
        this.socketNameCombinationFactory = socketNameCombinationFactory;

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
        players = new ArrayList<>();
        players.add(new PlayerWithConnection(
                new Player(getLocalName(), getPlayerCards()),
                playerConnectionFactory.localConnection()));
        for(SocketNameCombination snc : connections) {
            Player p = new Player(snc.getName(), getPlayerCards());
            PlayerWithConnection pwc = new PlayerWithConnection(p, playerConnectionFactory.remoteConnection(snc));
            players.add(pwc);
        }
    }

    private void initGame() {
        Card activeCard;
        int i = -1;
        do {
            activeCard = cards.get(++i);
        } while(activeCard.hasAction());
        cards.remove(i);
        game = new Game(players, new CardStack(cards), activeCard, highScoreStorageRepository);
    }

    private void startServer() throws IOException {

        console.println("Waiting for players");
        serverSocket = new ServerSocket(ConnectionConfig.SOCKET_PORT);

        Thread thread = new Thread(() -> {
            while(isServerSearchingForInput) {
                try {
                    Socket socket = serverSocket.accept();
                    if(!isServerSearchingForInput) {
                        socket.close();
                        return;
                    }
                    SocketNameCombination socketNameCombination = socketNameCombinationFactory.fromSocket(socket);
                    connections.add(socketNameCombination);
                    console.println("Player " + socketNameCombination.getName() + " connected");
                } catch (IOException e) {
                    if(isServerSearchingForInput) {
                        console.error("Error in Socket connection loop");
                    }
                }
            }
        });
        thread.start();

        console.println("Press Enter to proceed");
        Scanner scanner = new Scanner(System.in);
        while(connections.isEmpty()) {
            scanner.nextLine();
            if(connections.isEmpty()) {
                console.error("No players connected yet, not starting...");
            }
        }
        isServerSearchingForInput = false;
    }

    private void closeServer() {
        try {
            serverSocket.close();
        } catch (IOException ignored) {
            // if that throws an error, the connection is probably already closed
        }
    }

}
