package de.dhbwka.uno.plugins;

import de.dhbwka.uno.application.io.Console;
import de.dhbwka.uno.application.io.ConsoleColor;

import java.util.Scanner;

public class ConsoleImpl implements Console {

    @Override
    public void println(ConsoleColor color, String message) {
        print(color, message.concat("\n"));
    }

    @Override
    public void print(ConsoleColor color, String message) {
        System.out.print(colorChange(color) + message + colorChange(ConsoleColor.RESET));
    }

    @Override
    public void error(String message) {
        System.err.println(colorChange(ConsoleColor.RED) + message);
    }

    @Override
    public String readLine() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    @Override
    public int readInt() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    private String colorChange(ConsoleColor color) {
        return (char) 27 + "[" + colorCodeOf(color) + "m";
    }

    private int colorCodeOf(ConsoleColor color) {
        return switch(color) {
            case BLACK -> 30;
            case RED -> 31;
            case GREEN -> 32;
            case YELLOW -> 33;
            case BLUE -> 34;
            case MAGENTA -> 35;
            case CYAN -> 36;
            case WHITE -> 37;
            case RESET -> 39;
        };
    }
}
