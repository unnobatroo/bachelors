package uno;

import uno.util.*;
import module java.base;

//javac -cp "junit6all.jar;checkthat6.jar" -p junit6all.jar --add-modules org.junit.jupiter  *.java uno/*.java uno/util/*.java
//java uno.Game "Player 1" "Player 2"

public class Game {
    protected LinkedList<Card> deck;
    protected List<Player> players;
    public List<Player> getPlayers() { return new ArrayList<>(players); }
    protected Card currentCard;
    public Card getCurrentCard() { return currentCard; }
    protected int currentPlayerIdx;
    protected boolean isForward = true;
    protected boolean isOn = true;
    public boolean getIsOn() { return isOn; }
    protected InputSource inputSource;
    public Game(int numCardsPerPlayer, InputSource inputSource, String... playerNames)
        throws NotEnoughPlayersException {
        this.inputSource = inputSource;
        if (playerNames.length < 2) throw new NotEnoughPlayersException(playerNames.length);
        initDeck();
        initPlayers(numCardsPerPlayer, playerNames);
    }
    private void initDeck() {
        deck = new LinkedList<>();
        for (Color c : Color.values()) {
            for (int i = 1; i <= 9; i++)
                deck.add(new Card(c, CardType.VALUE, i));
            for (int i = 1; i <= 2; i++) {
                deck.add(new Card(c, CardType.SKIP, 0));
                deck.add(new Card(c, CardType.TAKE, 0));
                deck.add(new Card(c, CardType.REVERSE, 0));
            }
        }
        if (inputSource.isInteractive)
            Collections.shuffle(deck);
        else
            Collections.shuffle(deck, new java.util.Random(12345));
    }
    private void initPlayers(int numCardsPerPlayer, String... playerNames) {
        players = new ArrayList<>();
        for (String playerName : playerNames) {
            players.add(new Player(playerName, drawCards(numCardsPerPlayer), this));
        }
        currentCard = drawCards(1).get(0);
        currentPlayerIdx = players.size()-1;
    }
    private List<Card> drawCards(int number) {
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            cards.add(deck.remove(0));
        }
        return cards;
    }
    public static void main(String[] args) throws NotEnoughPlayersException {
        Game game = new Game(6, new InputSource(true, null), args);
        while (game.isOn) {
            game.playNext();
        }
    }
    public void playNext() {
        if (!isOn) return;
        int nextPlayer = getNextPlayerIdx();
        if (inputSource.isInteractive) {
            interactiveMsg("Current Card: " + currentCard.toString());
            interactiveMsg(players.get(nextPlayer).toString());
        }
        currentPlayerIdx = nextPlayer;
        int nextCard = inputSource.getNextInput();
        if (nextCard == -1) { isOn = false; return; }
        Card c = getCurrentPlayer().getHand().get(nextCard);
        if (currentCard.isPlayableOver(c)) {
            getCurrentPlayer().removeFromHand(nextCard);
            currentCard = c;
            if (currentCard.type != CardType.VALUE) useCardEffect();
        } else currentPlayerDrawCard();
        if (getCurrentPlayer().getHand().size() == 0) isOn = false;


    }
    public int getNextPlayerIdx() {
        return (currentPlayerIdx + 1) % players.size();
    }
    private void currentPlayerDrawCard() {
        getCurrentPlayer().addToHand(drawCards(1));
    }
    public Player getCurrentPlayer() {
        return players.get(currentPlayerIdx);
    }
    private void interactiveMsg(String msg) {
        IO.println(msg);
    }
    private void useCardEffect() {
        if (currentCard.type == CardType.REVERSE)
            isForward = !isForward;
        else if (currentCard.type == CardType.SKIP)
            currentPlayerIdx = getNextPlayerIdx();
        else { //if (currentCard.type == CardType.TAKE) {
            currentPlayerIdx = getNextPlayerIdx();
            currentPlayerDrawCard();
        }
    }

}