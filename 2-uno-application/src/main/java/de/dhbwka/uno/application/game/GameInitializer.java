package de.dhbwka.uno.application.game;

import de.dhbwka.uno.application.model.PlayerWithConnection;
import de.dhbwka.uno.application.model.SimplePlayerWithConnection;
import de.dhbwka.uno.application.persistence.HighScoreStorageRepository;
import de.dhbwka.uno.domain.Card;
import de.dhbwka.uno.domain.CardStack;
import de.dhbwka.uno.domain.Player;
import de.dhbwka.uno.domain.SimplePlayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameInitializer {

    private GameInitializer() {}

    public static void startNewGame(CardProvider cardProvider, List<SimplePlayerWithConnection> simplePlayers, HighScoreStorageRepository highScoreStorageRepository) {
        List<PlayerWithConnection> players = initPlayers(simplePlayers);
        List<Card> cards = distributeCards(players, cardProvider);
        Card activeCard = getActiveCard(cards);

        new Game(players, new CardStack(cards), activeCard, highScoreStorageRepository)
                .start();
    }

    /**
     * Wandelt {@link SimplePlayer}s (ohne Kartenstapel) zu {@link Player}s (mit Kartenstapel) um
     */
    private static List<PlayerWithConnection> initPlayers(List<SimplePlayerWithConnection> simplePlayers) {
        List<PlayerWithConnection> players = new ArrayList<>();

        for (SimplePlayerWithConnection p : simplePlayers) {
            players.add(new PlayerWithConnection(
                    new Player(p.simplePlayer().getName(), new CardStack(new ArrayList<>())),
                    p.playerConnection()
            ));
        }

        return players;
    }

    /**
     * Gibt jedem Spieler in {@code players} 7 Karten von {@code cardProvider}
     *
     * @return Übrige Karten, die nicht verteilt wurden
     */
    private static List<Card> distributeCards(List<PlayerWithConnection> players, CardProvider cardProvider) {
        List<Card> cards = cardProvider.listAllCards();
        Collections.shuffle(cards);

        for (PlayerWithConnection p : players) {
            cards.subList(0, 7).forEach(c -> p.player().getCardStack().add(c));

            cards = cards.subList(7, cards.size());
        }

        return cards;
    }

    /**
     * Wählt eine gültige aktive Karte für den Spielstart aus, entfernt sie aus {@code cards} und gibt sie zurück
     */
    private static Card getActiveCard(List<Card> cards) {
        Card activeCard;

        int i = -1;
        do {
            activeCard = cards.get(++i);
        } while(activeCard.hasAction());

        cards.remove(i);

        return activeCard;
    }

}
