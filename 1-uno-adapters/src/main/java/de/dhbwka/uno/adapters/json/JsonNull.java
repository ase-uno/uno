package de.dhbwka.uno.adapters.json;

public class JsonNull implements JsonElement {

    @Override
    public String toJson() {
        return "null";
    }
}
