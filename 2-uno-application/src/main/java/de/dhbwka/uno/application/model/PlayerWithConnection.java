package de.dhbwka.uno.application.model;

import de.dhbwka.uno.application.game.PlayerConnection;
import de.dhbwka.uno.domain.Card;
import de.dhbwka.uno.domain.Player;

/**
 * Repräsentiert einen mit dem Server verbundenen Spieler nachdem das Spiel begonnen hat
 *
 * @param player Spieler
 * @param playerConnection Verbindung zu dem Spieler
 */
public record PlayerWithConnection(Player player, PlayerConnection playerConnection) {

    /**
     * @param activeCard Karte, welche oben auf dem Stapel liegt
     * @return Karte, die
     */
    public Card placeCard(Card activeCard) {
        return this.playerConnection.playCard(activeCard, this.player.getCardStack());
    }
}
