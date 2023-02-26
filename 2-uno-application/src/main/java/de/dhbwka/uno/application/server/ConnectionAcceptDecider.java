package de.dhbwka.uno.application.server;

import de.dhbwka.uno.application.model.SimplePlayerWithConnection;

public interface ConnectionAcceptDecider {

    /**
     * @param player {@link SimplePlayerWithConnection}, der dem Spiel beitreten möchte
     * @return {@code true}, wenn der Spieler dem Spiel erfolgreich beigetreten ist. Sonst {@code false}.
     */
    boolean accept(SimplePlayerWithConnection player);

}
