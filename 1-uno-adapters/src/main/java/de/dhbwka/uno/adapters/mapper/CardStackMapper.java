package de.dhbwka.uno.adapters.mapper;

import de.dhbwka.uno.adapters.json.JsonArray;
import de.dhbwka.uno.adapters.json.JsonElement;
import de.dhbwka.uno.adapters.json.JsonObject;
import de.dhbwka.uno.domain.Card;
import de.dhbwka.uno.domain.CardStack;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class CardStackMapper {

    private static final String CARDSTACK_CARDLIST = "cardList";

    private CardStackMapper() {}

    public static JsonElement cardStackToJson(CardStack cardStack) {
        HashMap<String, JsonElement> props = new HashMap<>();

        List<JsonElement> cards = cardStack.getCardList()
                .stream()
                .map(CardMapper::cardToJson)
                .toList();
        JsonArray elements = new JsonArray(cards);
        props.put(CARDSTACK_CARDLIST, elements);

        return new JsonObject(props);
    }

    public static CardStack cardStackFromJson(JsonElement jsonElement) {
        JsonObject jsonObject = (JsonObject) jsonElement;
        JsonArray jsonArray = (JsonArray) jsonObject.get(CARDSTACK_CARDLIST);

        List<Card> cardList = jsonArray.getElements()
                .stream()
                .map(CardMapper::cardFromJson)
                .filter(Objects::nonNull)
                .toList();
        return new CardStack(cardList);
    }

}
