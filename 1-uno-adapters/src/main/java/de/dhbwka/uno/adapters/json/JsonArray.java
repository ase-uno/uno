package de.dhbwka.uno.adapters.json;

import java.util.List;
import java.util.stream.Collectors;

public record JsonArray(List<JsonElement> elements) implements JsonElement {

    @Override
    public String toJsonString() {
        return "[" + elements.stream()
                .map(JsonElement::toJsonString)
                .collect(Collectors.joining(","))
                + "]";
    }

}
