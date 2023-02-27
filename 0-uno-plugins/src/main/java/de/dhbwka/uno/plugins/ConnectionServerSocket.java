package de.dhbwka.uno.plugins;

import de.dhbwka.uno.adapters.json.JsonConverter;
import de.dhbwka.uno.adapters.json.JsonElement;
import de.dhbwka.uno.adapters.json.JsonString;
import de.dhbwka.uno.application.model.SimplePlayerWithConnection;
import de.dhbwka.uno.application.server.ConnectionAcceptDecider;
import de.dhbwka.uno.application.server.ConnectionServer;
import de.dhbwka.uno.domain.SimplePlayer;
import de.dhbwka.uno.plugins.server.SocketPlayerConnection;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ConnectionServerSocket implements ConnectionServer {
    private final int port;
    private ServerSocket socket;
    private final List<ConnectionAcceptDecider> connectionAcceptDeciders;
    private final List<Consumer<SimplePlayerWithConnection>> userJoinedEventConsumers;

    public ConnectionServerSocket(int port) {
        this.port = port;
        this.connectionAcceptDeciders = new ArrayList<>();
        this.userJoinedEventConsumers = new ArrayList<>();
    }

    @Override
    public void startServer() {
        try {
            socket = new ServerSocket(port);

            Thread acceptThread = new Thread(() -> {
                while (!socket.isClosed()) {
                    try {
                        Socket s = socket.accept();

                        SimplePlayer player = readNameFromSocket(s);
                        SocketPlayerConnection connection = new SocketPlayerConnection(s);
                        SimplePlayerWithConnection spwc = new SimplePlayerWithConnection(player, connection);

                        boolean anyDeciderDenies = connectionAcceptDeciders.stream()
                                .anyMatch(cad -> !cad.accept(player));

                        if (anyDeciderDenies) {
                            s.close();
                            return;
                        }
                        userJoinedEventConsumers.forEach(consumer -> consumer.accept(spwc));
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
        this.connectionAcceptDeciders.add(connectionAcceptDecider);
    }

    @Override
    public void onUserJoined(Consumer<SimplePlayerWithConnection> consumer) {
        this.userJoinedEventConsumers.add(consumer);
    }

    private SimplePlayer readNameFromSocket(Socket socket) {

        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            JsonElement jsonElement = new JsonConverter().fromJsonString(dataInputStream.readUTF());
            String remoteName = ((JsonString) jsonElement).getValue();
            return new SimplePlayer(remoteName);
        } catch (Exception e) {
            return null;
        }

    }
}
