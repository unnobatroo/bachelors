package capitaly.factory;

import capitaly.CarefulPlayer;
import capitaly.GreedyPlayer;
import capitaly.InvalidInputException;
import capitaly.Player;
import capitaly.TacticalPlayer;

public class PlayerFactory {

    public static Player createPlayer(String name, String strategyType)
        throws InvalidInputException {
        return switch (strategyType.toLowerCase()) {
            case "greedy", "g" -> new GreedyPlayer(name);
            case "careful", "c" -> new CarefulPlayer(name);
            case "tactical", "t" -> new TacticalPlayer(name);
            default -> throw new InvalidInputException(
                "Unknown player strategy: " + strategyType
            );
        };
    }
}
