package uno.util;

public class NotEnoughPlayersException extends Exception {
    public NotEnoughPlayersException(int numPlayers) {
        super("Only " + numPlayers  + " players were given");
    }
}