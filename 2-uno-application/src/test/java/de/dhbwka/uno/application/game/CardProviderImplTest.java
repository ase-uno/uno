package de.dhbwka.uno.application.game;

import de.dhbwka.uno.domain.Card;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CardProviderImplTest {

    @Test
    void listAllCards() {
        CardProviderImpl generator = new CardProviderImpl();

        List<Card> cards = generator.listAllCards();

        assertEquals(54, cards.size());
    }

}
