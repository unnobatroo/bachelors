import static org.junit.jupiter.api.Assertions.*;
import module org.junit.jupiter;

import module java.base;

import uno.Game;
import uno.util.InputSource;
import uno.util.Card;
import uno.util.NotEnoughPlayersException;

//javac with all files except suite (if structure tests are not in explicit classes)
//javac with just the suite (if structure tests are not in explicit classes)
//java for main
//java jar for junit with/without checkthat to run the suite

//javac -cp "junit6all.jar;checkthat6.jar" -p junit6all.jar --add-modules org.junit.jupiter *.java src/*.java uno/*.java uno/util/*.java

//javac -cp junit6all.jar -p junit6all.jar --add-modules org.junit.jupiter *.java uno/*.java uno/util/*.java
//java -jar junit6all.jar execute -c GameTest -cp .

public class GameTest {
    /*
    It creates a non-interactive `InputSource` driven game with three players, 2 cards each, in `1 0 1` steps.
Let's try the following.

1. Initially, the active card is `RED TAKE`.
1. Initially, the three players' cards are `GREEN SKIP, RED 1`, `RED 3, BLUE 8`, `BLUE REVERSE, RED 8`
1. call the `playNext` method three times, and after each step, check the next one.
	- The active player index is correct.
	- The active card is indeed the last card played.
	- The played card is out of the active player's cards.
	- The value of the game `isOn` is true.
1. If the `playNext` method is called again, `isOn` is false.

    */
    @Test
    public void test() throws NotEnoughPlayersException {
        Game game = new Game(2, new InputSource(false, 1, 0, 1), "Player1", "Player2", "Player3");
        List<Card> cards = game.getPlayers().get(game.getNextPlayerIdx()).getHand();
        game.playNext();
        assertEquals("Player1", game.getCurrentPlayer().getName());
        assertTrue(cards.contains(game.getCurrentCard()));
        assertTrue(game.getIsOn());
        cards = game.getPlayers().get(game.getNextPlayerIdx()).getHand();
        game.playNext();
        assertEquals("Player2", game.getCurrentPlayer().getName());
        assertTrue(cards.contains(game.getCurrentCard()));
        assertTrue(game.getIsOn());
        cards = game.getPlayers().get(game.getNextPlayerIdx()).getHand();
        game.playNext();
        assertEquals("Player3", game.getCurrentPlayer().getName());
        assertTrue(cards.contains(game.getCurrentCard()));
        assertTrue(game.getIsOn());
        game.playNext();
        assertFalse(game.getIsOn());
    }
}