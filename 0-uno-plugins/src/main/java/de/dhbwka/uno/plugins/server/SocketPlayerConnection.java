package de.dhbwka.uno.plugins.server;

import de.dhbwka.uno.adapters.json.JsonConverter;
import de.dhbwka.uno.adapters.json.JsonElement;
import de.dhbwka.uno.adapters.json.JsonObject;
import de.dhbwka.uno.adapters.json.JsonString;
import de.dhbwka.uno.adapters.mapper.CardMapper;
import de.dhbwka.uno.adapters.mapper.CardStackMapper;
import de.dhbwka.uno.adapters.mapper.HighScoreMapper;
import de.dhbwka.uno.adapters.mapper.PlayerMapper;
import de.dhbwka.uno.application.game.PlayerConnection;
import de.dhbwka.uno.domain.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.HashMap;

public class SocketPlayerConnection implements PlayerConnection {

    private static final String ACTION = "action";
    private static final String DATA = "data";

    private final Socket socket;

    public SocketPlayerConnection(Socket socket) {
        this.socket = socket;
    }

    @Override
    public Card playCard(Card active, CardStack cardStack) {

        HashMap<String, JsonElement> data = new HashMap<>();
        data.put("active", CardMapper.cardToJson(active));
        data.put("cardStack", CardStackMapper.cardStackToJson(cardStack));


        HashMap<String, JsonElement> request = new HashMap<>();
        request.put(ACTION, new JsonString("input"));
        request.put(DATA, new JsonObject(data));

        JsonObject jsonObject = new JsonObject(request);

        try {
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(jsonObject.toJsonString());

            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String response = dataInputStream.readUTF();
            JsonElement jsonElement = new JsonConverter().fromJsonString(response);
            return CardMapper.cardFromJson(jsonElement);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public CardColor selectColor() {
        HashMap<String, JsonElement> request = new HashMap<>();
        request.put(ACTION, new JsonString("inputColor"));

        JsonObject jsonObject = new JsonObject(request);

        try {
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(jsonObject.toJsonString());

            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String response = dataInputStream.readUTF();
            JsonElement jsonResponse = new JsonConverter().fromJsonString(response);
            return CardMapper.cardColorFromJson(jsonResponse);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void broadcastWinner(SimplePlayer winner) {
        broadcastPlayer("broadcastWinner", winner);
    }

    @Override
    public void broadcastActivePlayer(SimplePlayer player) {
        broadcastPlayer("broadcastActivePlayer", player);
    }

    @Override
    public void broadcastHighScore(HighScore highScore) {

        HashMap<String, JsonElement> data = new HashMap<>();
        data.put("highScore", HighScoreMapper.highScoreToJson(highScore));

        HashMap<String, JsonElement> request = new HashMap<>();
        request.put(ACTION, new JsonString("broadcastHighScore"));
        request.put(DATA, new JsonObject(data));

        broadcastMessage(new JsonObject(request));
    }

    private void broadcastPlayer(String action, SimplePlayer player) {
        HashMap<String, JsonElement> data = new HashMap<>();
        data.put("player", PlayerMapper.playerDTOToJson(player));


        HashMap<String, JsonElement> request = new HashMap<>();
        request.put(ACTION, new JsonString(action));
        request.put(DATA, new JsonObject(data));

        broadcastMessage(new JsonObject(request));
    }

    private void broadcastMessage(JsonObject jsonObject) {
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(jsonObject.toJsonString());
        } catch (Exception ignored) {
            //ignore error for this message
        }
    }
}
