package de.dhbwka.uno.adapters.json;

public record JsonString(String value) implements JsonElement {

    @Override
    public String toJsonString() {
        return "\"" + value.replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
    }

}
