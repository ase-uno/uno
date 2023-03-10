package de.dhbwka.uno.domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardStackTest {

    @Test
    public void add() {
        List<Card> cards = new ArrayList<>();

        CardStack stack = new CardStack(cards);

        assertEquals(stack.cardList().size(), 0);

        Card card = new Card(CardColor.BLUE, new CardNumber(1));
        stack.add(card);

        assertEquals(stack.cardList().get(0), card);
    }

    @Test
    public void remove() {
        List<Card> cards = new ArrayList<>();
        Card card = new Card(CardColor.BLUE, new CardNumber(1));
        cards.add(card);

        CardStack stack = new CardStack(cards);

        assertEquals(stack.cardList().size(), 1);

        stack.remove(card);

        assertEquals(stack.cardList().size(), 0);
    }

    @Test
    public void isFinished() {
        List<Card> cards = new ArrayList<>();

        CardStack stack = new CardStack(cards);

        assertEquals(stack.isFinished(), true);
    }

    @Test
    public void isNotFinished() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(CardColor.BLUE, new CardNumber(1)));

        CardStack stack = new CardStack(cards);

        assertEquals(stack.isFinished(), false);
    }

}
