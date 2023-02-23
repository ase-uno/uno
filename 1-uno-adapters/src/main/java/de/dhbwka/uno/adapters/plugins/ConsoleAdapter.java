package de.dhbwka.uno.adapters.plugins;

import de.dhbwka.uno.application.io.ConsoleColor;
import de.dhbwka.uno.application.io.ConsoleOut;

public class ConsoleAdapter implements ConsoleOut {

    @Override
    public void print(ConsoleColor color, String message) {
        System.out.print(colorChange(color.getColorNumber()) + message + colorChange(ConsoleColor.RESET.getColorNumber()));
    }

    @Override
    public void error(String message) {
        System.err.println(colorChange(ConsoleColor.RED.getColorNumber()) + message);
    }

    private String colorChange(int color) {
        return (char)27 + "[" + color + "m";
    }
}
