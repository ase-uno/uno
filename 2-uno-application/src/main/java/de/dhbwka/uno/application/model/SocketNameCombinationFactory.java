package de.dhbwka.uno.application.model;

import java.net.Socket;

public interface SocketNameCombinationFactory {

    SocketNameCombination fromSocket(Socket socket);

}
