package uno.util;

import module java.base;

public class Card {
    public final Color color;
    public final CardType type;
    public final int value;
    public Card(Color color, CardType type, int value) {
        this.color = color;
        this.type = type;
        this.value = value;
    }
    public boolean isPlayableOver(Card card) {
        if (card.type != CardType.VALUE) {
            return color == card.color;
        }
        return color == card.color || value == card.value;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || !(obj instanceof Card)) return false;
        Card c = (Card)obj;
        return color == c.color && type == c.type && value == c.value;
    }
    @Override
    public int hashCode() {
        return Objects.hash(color, type, value);
    }
    @Override
    public String toString() {
        return color.toString() + " " +
            (type == CardType.VALUE ? Integer.toString(value) : type.toString());
    }
}