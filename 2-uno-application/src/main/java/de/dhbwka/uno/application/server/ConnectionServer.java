package de.dhbwka.uno.application.server;

/**
 *
 */
public interface ConnectionServer {

    /**
     * Startet den Server, sodass sich Spieler mit ihm verbinden können
     */
    void startServer();

    /**
     * Schließt den Server sowie alle Verbindungen zu Mitspielern
     */
    void stopServer();

    /**
     * @param connectionAcceptDecider Callback, um festzulegen, ob Spieler dem Spiel beitreten können
     */
    void registerConnectDecider(ConnectionAcceptDecider connectionAcceptDecider);

}
