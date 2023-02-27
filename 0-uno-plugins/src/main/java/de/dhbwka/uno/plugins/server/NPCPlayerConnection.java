package de.dhbwka.uno.plugins.server;

import de.dhbwka.uno.application.game.PlayerConnection;
import de.dhbwka.uno.domain.*;

import java.util.Random;

public class NPCPlayerConnection implements PlayerConnection {


    private final String name;

    public NPCPlayerConnection(String name) {
        this.name = name;
    }

    private final Random random = new Random();

    @Override
    public Card playCard(Card active, CardStack cardStack) {
        for (Card card : cardStack.getCardList()) {
            if (active.isCompatibleWith(card)) {
                return card;
            }
        }
        return null;
    }

    @Override
    public CardColor selectColor() {
        return CardColor.values()[random.nextInt(CardColor.values().length)];
    }

    @Override
    public void broadcastWinner(SimplePlayer winner) {
        //only for testing, so no broadcast ist needed
    }

    @Override
    public void broadcastActivePlayer(SimplePlayer player) {
        //only for testing, so no broadcast ist needed
    }

    @Override
    public void broadcastHighScore(HighScore highScore) {
        //only for testing, so no broadcast ist needed
    }
}
