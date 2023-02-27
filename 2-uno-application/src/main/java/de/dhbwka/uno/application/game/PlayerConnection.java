package de.dhbwka.uno.application.game;

import de.dhbwka.uno.domain.*;

/**
 * Verbindung zu einem Client
 */
public interface PlayerConnection {

    Card playCard(Card active, CardStack cardStack);
    CardColor selectColor();

    void broadcastWinner(SimplePlayer winner);

    void broadcastActivePlayer(SimplePlayer player);

    void broadcastHighScore(HighScore highScore);

}
