package de.dhbwka.uno.application.model;

import de.dhbwka.uno.application.game.PlayerConnection;
import de.dhbwka.uno.domain.SimplePlayer;

/**
 * Repräsentiert einen mit dem Server verbundenen Spieler bevor das Spiel begonnen hat
 *
 * @param simplePlayer Spieler
 * @param playerConnection Verbindung zu dem Spieler
 */
public record SimplePlayerWithConnection(SimplePlayer simplePlayer, PlayerConnection playerConnection) {
    
}
