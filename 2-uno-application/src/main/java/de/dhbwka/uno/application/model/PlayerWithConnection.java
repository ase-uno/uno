package de.dhbwka.uno.application.model;

import de.dhbwka.uno.application.game.PlayerConnection;
import de.dhbwka.uno.domain.Card;
import de.dhbwka.uno.domain.Player;

public class PlayerWithConnection {

    private final Player player;
    private final PlayerConnection playerConnection;

    public PlayerWithConnection(Player player, PlayerConnection playerConnection) {
        this.player = player;
        this.playerConnection = playerConnection;
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerConnection getPlayerConnection() {
        return playerConnection;
    }

    public Card input(Card activeCard) {
        return this.playerConnection.input(activeCard, this.player.getCardStack());
    }
}
