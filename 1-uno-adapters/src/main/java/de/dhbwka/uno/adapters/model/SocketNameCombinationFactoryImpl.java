package de.dhbwka.uno.adapters.model;

import de.dhbwka.uno.adapters.json.JsonConverter;
import de.dhbwka.uno.adapters.json.JsonElement;
import de.dhbwka.uno.adapters.json.JsonString;
import de.dhbwka.uno.application.model.SocketNameCombination;
import de.dhbwka.uno.application.model.SocketNameCombinationFactory;

import java.io.DataInputStream;
import java.net.Socket;

public class SocketNameCombinationFactoryImpl implements SocketNameCombinationFactory {
    @Override
    public SocketNameCombination fromSocket(Socket socket) {

        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            JsonElement jsonElement = new JsonConverter().fromJson(dataInputStream.readUTF());
            String remoteName = ((JsonString) jsonElement).getValue();
            return new SocketNameCombination(socket, remoteName);
        } catch (Exception e) {
            return null;
        }

    }
}
