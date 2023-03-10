package de.dhbwka.uno.application.io;

public enum ConsoleColor {
    BLACK(30),
    RED(31),
    GREEN(32),
    YELLOW(33),
    BLUE(34),
    MAGENTA(35),
    CYAN(36),
    WHITE(37),
    RESET(39);

    private final int colorNumber;

    ConsoleColor(int colorNumber) {
        this.colorNumber = colorNumber;
    }

    public int getColorNumber() {
        return colorNumber;
    }

}
