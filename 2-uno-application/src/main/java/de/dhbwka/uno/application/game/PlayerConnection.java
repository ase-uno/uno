package de.dhbwka.uno.application.game;

import de.dhbwka.uno.domain.*;

public interface PlayerConnection {

    Card input(Card active, CardStack cardStack);
    CardColor inputColor();

    void broadcastWinner(SimplePlayer winner);

    void broadcastActivePlayer(SimplePlayer player);

    void broadcastHighScore(HighScore highScore);

}
