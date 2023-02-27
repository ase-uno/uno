package de.dhbwka.uno.adapters.json;

public class JsonConvertException extends Exception {

    public JsonConvertException(String message) {
        super(message);
    }

    public JsonConvertException(int index) {
        this("Malformed json at index " + index);
    }

}
