package de.dhbwka.uno.adapters.plugins;

import de.dhbwka.uno.application.game.PlayerConnection;
import de.dhbwka.uno.application.io.ConsoleColor;
import de.dhbwka.uno.application.io.ConsoleOut;
import de.dhbwka.uno.domain.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ConsolePlayerConnection implements PlayerConnection {

    private final ConsoleOut console;

    public ConsolePlayerConnection(ConsoleOut console) {
        this.console = console;
    }

    @Override
    public Card input(Card active, CardStack cardStack) {

        console.println("Input card: ");
        console.println();
        console.println("Active: ");
        printCard(active);
        console.println();

        for(int i = 0; i<cardStack.getCardList().size(); i++) {
            console.println(i + ") " + cardToString(cardStack.getCardList().get(i)));
        }

        Card card = requestCardSelection(cardStack);
        console.println();
        return card;
    }

    private Card requestCardSelection(CardStack cardStack) {
        int input = requestUserInputSelection(-1, cardStack.getCardList().size());

        if(input == -1) return null;
        return cardStack.getCardList().get(input);
    }

    private String cardToString(Card card) {
        Integer number = null;
        String color = null;
        if(card.getNumber() != null) number = card.getNumber().getValue();
        if(card.getColor() != null) color = card.getColor().getName();
        return "color=" + color + ", cardNumber=" + number + ", action=" + card.getAction();
    }

    private void printCard(Card card) {
        String filePath = getFilePathForCard(card);
        ConsoleColor color = cardColorToConsoleColor(card.getColor());
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(filePath);
        if(inputStream == null) {
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

        if(card.getNumber() != null) {
            path += "numbers/" + card.getNumber().getValue() + ".txt";
        } else if(card.hasAction()) {
            path += "actions/";
            String actionName = card.getAction().getAction().name().toLowerCase();
            path += actionName;
            if(card.getAction().getAction() == Action.DRAW) {
                path += "_" + card.getAction().getDraw();
            }
            if(card.getAction().getDraw() > 0 && card.getAction().getAction() != Action.DRAW) {
                path += "_draw_" + card.getAction().getDraw();
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
    public CardColor inputColor() {
        console.println("Input card");

        for(int i = 0; i < CardColor.values().length; i++) {
            console.println(i + ") " + CardColor.values()[i].getName());
        }

        int input = requestUserInputSelection(0, CardColor.values().length);
        console.println();

        return CardColor.values()[input];
    }

    @Override
    public void broadcastWinner(SimplePlayer winner) {
        if(winner == null) {
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
        Scanner scanner = new Scanner(System.in);
        int input = min - 1;
        do {
            try {
                console.println("Input:");
                input = scanner.nextInt();
            } catch (Exception ignored) {
                //if an error occurs, new user-input is requested by the loop
            }
        } while (input < min || input >= maxExcluded);
        return input;
    }
}
