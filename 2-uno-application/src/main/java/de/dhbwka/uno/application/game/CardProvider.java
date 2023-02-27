package de.dhbwka.uno.application.game;

import de.dhbwka.uno.domain.Card;

import java.util.List;

public interface CardProvider {

    List<Card> listAllCards();

}
