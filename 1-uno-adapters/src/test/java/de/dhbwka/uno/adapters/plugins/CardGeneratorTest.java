package de.dhbwka.uno.adapters.plugins;

import de.dhbwka.uno.domain.Card;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CardGeneratorTest {

    @Test
    void listAllCards() {
        CardGenerator generator = new CardGenerator();

        List<Card> cards = generator.listAllCards();

        assertEquals(54, cards.size());
    }

}
