package de.dhbwka.uno.domain;

public record CardAction(Action action, int draw) {

    @Override
    public String toString() {
        return "CardAction{" +
                "action=" + action +
                ", draw=" + draw +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardAction that = (CardAction) o;
        return draw == that.draw && action == that.action;
    }

}
