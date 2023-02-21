package de.dhbwka.uno.application.game;

import de.dhbwka.uno.application.model.SocketNameCombination;

public interface PlayerConnectionFactory {

    PlayerConnection localConnection();
    PlayerConnection remoteConnection(SocketNameCombination socketNameCombination);

}
