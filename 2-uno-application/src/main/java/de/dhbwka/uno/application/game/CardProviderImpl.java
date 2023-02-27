package de.dhbwka.uno.application.game;

import de.dhbwka.uno.domain.*;

import java.util.ArrayList;
import java.util.List;

public class CardProviderImpl implements CardProvider {


    @Override
    public List<Card> listAllCards() {

        List<Card> cards = new ArrayList<>();

        for(CardColor color : CardColor.values()) {
            for(int i = 0; i<10; i++) {
                cards.add(new Card(color, new CardNumber(i)));
            }
            cards.add(new Card(color, new CardAction(Action.DRAW, 2)));
            cards.add(new Card(color, new CardAction(Action.CHANGE_DIRECTION, 0)));
            cards.add(new Card(color, new CardAction(Action.BLOCK, 0)));
        }
        cards.add(new Card(new CardAction(Action.CHANGE_COLOR, 0)));
        cards.add(new Card(new CardAction(Action.CHANGE_COLOR, 4)));

        return cards;
    }


}
