package de.dhbwka.uno.plugins;

import de.dhbwka.uno.application.model.SimplePlayerWithConnection;
import de.dhbwka.uno.application.server.ConnectionAcceptDecider;
import de.dhbwka.uno.application.server.ConnectionServer;
import de.dhbwka.uno.domain.SimplePlayer;
import de.dhbwka.uno.plugins.server.SocketPlayerConnection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionServerSocket implements ConnectionServer {
    private final int port;
    private ServerSocket socket;
    private ConnectionAcceptDecider connectionAcceptDecider;
    private Thread acceptThread;

    public ConnectionServerSocket(int port) {
        this.port = port;
    }

    @Override
    public void startServer() {
        try {
            socket = new ServerSocket(port);

            acceptThread = new Thread(() -> {
                while (!socket.isClosed()) {
                    try {
                        Socket s = socket.accept();

                        // TODO: read name from socket
                        String name = "Peter Lustig";

                        SimplePlayer player = new SimplePlayer(name);
                        SocketPlayerConnection connection = new SocketPlayerConnection(s);
                        SimplePlayerWithConnection spwc = new SimplePlayerWithConnection(player, connection);

                        boolean accept = connectionAcceptDecider.accept(spwc);

                        if (!accept) {
                            // TODO: schöne Nachricht schicken
                            s.close();
                            return;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            acceptThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stopServer() {
        if (socket == null) {
            return;
        }

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void registerConnectDecider(ConnectionAcceptDecider connectionAcceptDecider) {
        this.connectionAcceptDecider = connectionAcceptDecider;
    }
}
