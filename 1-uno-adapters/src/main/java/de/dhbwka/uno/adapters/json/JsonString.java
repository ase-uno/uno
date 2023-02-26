package de.dhbwka.uno.adapters.json;

public class JsonString implements JsonElement {

    private final String value;

    public JsonString(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toJsonString() {
        return "\"" + value.replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
    }

}
