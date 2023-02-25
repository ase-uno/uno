package de.dhbwka.uno.adapters.plugins;

import de.dhbwka.uno.domain.Card;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardGeneratorTest {

    @Test
    public void listAllCards() {
        CardGenerator generator = new CardGenerator();

        List<Card> cards = generator.listAllCards();

        assertEquals(cards.size(), 54);
    }

}
