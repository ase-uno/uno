package de.dhbwka.uno.plugins;

import de.dhbwka.uno.application.io.ConsoleColor;
import de.dhbwka.uno.application.io.Console;

import java.util.Scanner;

public class ConsoleImpl implements Console {

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
        return colorChange(color.getColorNumber());
    }

    private String colorChange(int color) {
        return (char)27 + "[" + color + "m";
    }
}
