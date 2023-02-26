package de.dhbwka.uno.application.game;

import de.dhbwka.uno.domain.*;

public class MockedPlayerConnection implements PlayerConnection {

    private int inputCalled = 0;
    private int inputColorCalled = 0;
    private int broadcastWinnerCalled = 0;
    private int broadcastActivePlayerCalled = 0;
    private int broadcastHighscoreCalled = 0;


    @Override
    public Card playCard(Card active, CardStack cardStack) {
        inputCalled++;

        for(Card card: cardStack.getCardList()) {
            if(card.isCompatibleWith(active)) return card;
        }
        return null;
    }

    @Override
    public CardColor selectColor() {
        inputColorCalled++;
        return CardColor.RED;
    }

    @Override
    public void broadcastWinner(SimplePlayer winner) {
        broadcastWinnerCalled++;
    }

    @Override
    public void broadcastActivePlayer(SimplePlayer player) {
        broadcastActivePlayerCalled++;
    }

    @Override
    public void broadcastHighScore(HighScore highScore) {
        broadcastHighscoreCalled++;
    }

    public int getInputCalled() {
        return inputCalled;
    }

    public int getInputColorCalled() {
        return inputColorCalled;
    }

    public int getBroadcastWinnerCalled() {
        return broadcastWinnerCalled;
    }

    public int getBroadcastActivePlayerCalled() {
        return broadcastActivePlayerCalled;
    }

    public int getBroadcastHighscoreCalled() {
        return broadcastHighscoreCalled;
    }
}