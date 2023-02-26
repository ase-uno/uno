package de.dhbwka.uno.plugins.client;

import de.dhbwka.uno.adapters.json.*;
import de.dhbwka.uno.adapters.mapper.CardMapper;
import de.dhbwka.uno.adapters.mapper.CardStackMapper;
import de.dhbwka.uno.adapters.mapper.HighScoreMapper;
import de.dhbwka.uno.adapters.mapper.PlayerMapper;
import de.dhbwka.uno.application.game.PlayerConnection;
import de.dhbwka.uno.application.io.ConsoleColor;
import de.dhbwka.uno.application.io.ConsoleOut;
import de.dhbwka.uno.domain.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketConnection {

    private final PlayerConnection playerConnection;
    private final ConsoleOut console;
    private Socket socket;

    public SocketConnection(
            String ip,
            int port,
            String name,
            PlayerConnection playerConnection,
            ConsoleOut console) throws IOException {
        this.playerConnection = playerConnection;
        this.console = console;

        connect(ip, port, name);

        while (socket.isConnected()) {
            waitForMessage();
        }
        socket.close();
    }

    private void connect(String ip, int port, String name) throws IOException {
        socket = new Socket(ip, port);
        returnResponseToServer(new JsonString(name));
        console.println(ConsoleColor.GREEN, "Connected");
    }

    private void waitForMessage() throws IOException {
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        String message = dataInputStream.readUTF();
        try {
            parseMessage(message);
        } catch (Exception e) {
            console.error("Exception on parsing Server message");
            e.printStackTrace();
        }
    }

    private void parseMessage(String message) throws JsonConvertException {
        JsonObject jsonObject = (JsonObject) new JsonConverter().fromJsonString(message);

        String action = ((JsonString) jsonObject.get("action")).getValue();
        JsonElement element = jsonObject.get("data");
        switch (action) {
            case "input" -> {
                JsonObject jsonObject1 = (JsonObject) element;
                Card active = CardMapper.cardFromJson(jsonObject1.get("active"));
                CardStack cardStack = CardStackMapper.cardStackFromJson(jsonObject1.get("cardStack"));

                Card card = playerConnection.playCard(active, cardStack);
                returnResponseToServer(CardMapper.cardToJson(card));
            }
            case "inputColor" -> {
                CardColor color = playerConnection.selectColor();
                returnResponseToServer(CardMapper.cardColorToJson(color));
            }
            case "broadcastWinner" -> {
                JsonObject jsonObject1 = (JsonObject) element;
                SimplePlayer simplePlayer = PlayerMapper.playerDTOFromJson(jsonObject1.get("player"));
                playerConnection.broadcastWinner(simplePlayer);
            }
            case "broadcastActivePlayer" -> {
                JsonObject jsonObject1 = (JsonObject) element;
                SimplePlayer simplePlayer = PlayerMapper.playerDTOFromJson(jsonObject1.get("player"));
                playerConnection.broadcastActivePlayer(simplePlayer);
            }
            case "broadcastHighScore" -> {
                JsonObject jsonObject1 = (JsonObject) element;
                HighScore highScore = HighScoreMapper.highScoreFromJson(jsonObject1.get("highScore"));
                playerConnection.broadcastHighScore(highScore);
            }
            default -> console.error("Error, invalid message received from Server");
        }
    }

    private void returnResponseToServer(JsonElement jsonElement) {
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(jsonElement.toJsonString());
        } catch (Exception e) {
            console.error("Exception on returning Server message");
            e.printStackTrace();
        }
    }
}
