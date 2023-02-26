package de.dhbwka.uno.adapters.json;

import java.util.List;
import java.util.stream.Collectors;

public class JsonArray implements JsonElement {

    private final List<JsonElement> elements;

    public JsonArray(List<JsonElement> elements) {
        this.elements = elements;
    }

    public List<JsonElement> getElements() {
        return elements;
    }

    @Override
    public String toJsonString() {
        return "[" + elements.stream()
                .map(JsonElement::toJsonString)
                .collect(Collectors.joining(","))
                + "]";
    }
}
