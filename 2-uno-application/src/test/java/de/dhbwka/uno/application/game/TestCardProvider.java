package de.dhbwka.uno.application.game;

import de.dhbwka.uno.domain.Card;

import java.util.ArrayList;
import java.util.List;

public class TestCardProvider implements CardProvider {

    private final int amountCards;
    private final Card cardToProvide;

    public TestCardProvider(int amountCards, Card cardToProvide) {
        this.amountCards = amountCards;
        this.cardToProvide = cardToProvide;
    }

    @Override
    public List<Card> listAllCards() {
        List<Card> list = new ArrayList<>();

        for (int i = 0; i < amountCards; i++) {
            list.add(cardToProvide);
        }

        return list;
    }
}
