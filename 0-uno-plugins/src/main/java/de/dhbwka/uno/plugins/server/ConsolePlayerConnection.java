package de.dhbwka.uno.plugins.server;

import de.dhbwka.uno.application.game.PlayerConnection;
import de.dhbwka.uno.application.io.Console;
import de.dhbwka.uno.application.io.ConsoleColor;
import de.dhbwka.uno.domain.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ConsolePlayerConnection implements PlayerConnection {

    private final Console console;

    public ConsolePlayerConnection(Console console) {
        this.console = console;
    }

    @Override
    public Card playCard(Card active, CardStack cardStack) {
        console.println("Input card: ");
        console.println();
        console.println("Active: ");
        printCard(active);
        console.println();

        console.println("-1) Draw card");

        for (int i = 0; i < cardStack.cardList().size(); i++) {
            console.println(i + ") " + cardToString(cardStack.cardList().get(i)));
        }

        Card card = requestCardSelection(cardStack);
        console.println();
        return card;
    }

    private Card requestCardSelection(CardStack cardStack) {
        int input = requestUserInputSelection(-1, cardStack.cardList().size());

        if (input == -1) return null;
        return cardStack.cardList().get(input);
    }

    private String cardToString(Card card) {
        Integer number = null;
        String color = null;
        if (card.getNumber() != null) number = card.getNumber().getValue();
        if (card.getColor() != null) color = card.getColor().getName();
        return "color=" + color + ", cardNumber=" + number + ", action=" + card.getAction();
    }

    private void printCard(Card card) {
        String filePath = getFilePathForCard(card);
        ConsoleColor color = cardColorToConsoleColor(card.getColor());
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(filePath);
        if (inputStream == null) {
            console.println(cardToString(card));
            return;
        }
        InputStreamReader streamReader = new InputStreamReader(inputStream);

        BufferedReader bufferedReader = new BufferedReader(streamReader);
        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                console.println(color, line);
            }
        } catch (IOException e) {
            console.println(cardToString(card));
        }
    }

    private String getFilePathForCard(Card card) {
        String path = "";

        if (card.getNumber() != null) {
            path += "numbers/" + card.getNumber().getValue() + ".txt";
        } else if (card.hasAction()) {
            path += "actions/";
            String actionName = card.getAction().action().name().toLowerCase();
            path += actionName;
            if (card.getAction().action() == Action.DRAW) {
                path += "_" + card.getAction().draw();
            }
            if (card.getAction().draw() > 0 && card.getAction().action() != Action.DRAW) {
                path += "_draw_" + card.getAction().draw();
            }
            path += ".txt";
        } else {
            path += "empty.txt";
        }

        return "ascii/" + path;
    }

    private ConsoleColor cardColorToConsoleColor(CardColor color) {
        return switch (color) {
            case RED -> ConsoleColor.RED;
            case YELLOW -> ConsoleColor.YELLOW;
            case GREEN -> ConsoleColor.GREEN;
            case BLUE -> ConsoleColor.BLUE;
        };
    }

    @Override
    public CardColor selectColor() {
        console.println("Input card");

        for (int i = 0; i < CardColor.values().length; i++) {
            console.println(i + ") " + CardColor.values()[i].getName());
        }

        int input = requestUserInputSelection(0, CardColor.values().length);
        console.println();

        return CardColor.values()[input];
    }

    @Override
    public void broadcastWinner(SimplePlayer winner) {
        if (winner == null) {
            console.println("Tie");
        } else {
            console.println("Winner: " + winner.getName());
        }
    }

    @Override
    public void broadcastActivePlayer(SimplePlayer player) {
        console.println("Now playing: " + player.getName());
    }

    @Override
    public void broadcastHighScore(HighScore highScore) {
        console.println("High score:");
        highScore.getElements()
                .forEach((key, value) -> console.println(key.getName() + ": " + value));
    }

    private int requestUserInputSelection(int min, int maxExcluded) {
        int input = min - 1;
        do {
            try {
                console.println("Input:");
                input = console.readInt();
            } catch (Exception ignored) {
                //if an error occurs, new user-input is requested by the loop
            }
        } while (input < min || input >= maxExcluded);
        return input;
    }

}
