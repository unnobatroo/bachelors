package uno;

import module java.base;
import uno.util.*;

public class Player {
    private String name;
    public String getName() { return name; }
    private List<Card> hand;
    public List<Card> getHand() { return new ArrayList<>(hand); }
    private Game game;
    public Player(String name, List<Card> hand, Game game) {
        this.name = name;
        this.hand = new ArrayList<>(hand);
        this.game = game;
    }
    public void addToHand(List<Card> cards) {
        hand.addAll(cards);
    }
    public void removeFromHand(int idx) {
        hand.remove(idx);
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Player ").append(name).append(": ");
        for (int i = 0; i < hand.size(); i++) {
            Card c = hand.get(i);
            sb.append(i+1);
            if (game.currentCard.isPlayableOver(c))
                sb.append('*');
            sb.append('=').append(c.toString()).append(' ');
        }
        return sb.toString();
    }
}