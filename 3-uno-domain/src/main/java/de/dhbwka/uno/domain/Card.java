package de.dhbwka.uno.domain;

import java.util.Objects;

public class Card {
    private final CardColor color;
    private final CardNumber number;
    private final CardAction action;

    public Card(CardColor color, CardNumber number) {
        this(color, number, null);
    }

    public Card(CardColor color, CardAction action) {
        this(color, null, action);
    }

    public Card(CardAction action) {
        this(null, null, action);
    }

    public Card(CardColor color) {
        this(color, null, null);
    }

    public Card(CardColor color, CardNumber number, CardAction action) {
        this.color = color;
        this.number = number;
        this.action = action;
    }

    public CardColor getColor() {
        return color;
    }

    public CardNumber getNumber() {
        return number;
    }

    public boolean hasAction() {
        return action != null;
    }

    public CardAction getAction() {
        return action;
    }

    public boolean isCompatibleWith(Card card) {
        boolean colorsEqual = color != null && getColor() == card.getColor();
        boolean numbersEqual = number != null && getNumber().equals(card.getNumber());
        boolean hasAction = action != null && action.getAction() == Action.CHANGE_COLOR
                || card.action != null && card.action.getAction() == Action.CHANGE_COLOR;
        boolean actionsEqual = action != null && getAction().equals(card.getAction());

        return colorsEqual || numbersEqual || hasAction || actionsEqual;
    }

    @Override
    public String toString() {
        return "Card{" +
                "color=" + color +
                ", number=" + number +
                ", action=" + action +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return color == card.color && Objects.equals(number, card.number) && Objects.equals(action, card.action);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, number, action);
    }

}
