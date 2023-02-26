package de.dhbwka.uno.adapters.plugins;

import de.dhbwka.uno.domain.Card;
import de.dhbwka.uno.domain.CardColor;
import de.dhbwka.uno.domain.CardNumber;
import de.dhbwka.uno.domain.CardStack;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NPCPlayerConnectionTest {

    @Test
    public void input() {
        Card activeCard = new Card(CardColor.BLUE, new CardNumber(1));
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(CardColor.RED, new CardNumber(2)));
        CardStack cardStack = new CardStack(cards);

        NPCPlayerConnection con = new NPCPlayerConnection("Bob der Baumeister");

        Card c = con.input(activeCard, cardStack);

        assertNull(c);
    }

}