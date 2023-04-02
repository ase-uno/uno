package de.dhbwka.uno.application.io;

public interface Console {

    default void print(String message) {
        print(ConsoleColor.RESET, message);
    }

    default void println(String message) {
        println(ConsoleColor.RESET, message);
    }

    default void println() {
        println("");
    }

    void println(ConsoleColor color, String message);

    void print(ConsoleColor color, String message);

    void error(String message);

    String readLine();

    int readInt();

}
