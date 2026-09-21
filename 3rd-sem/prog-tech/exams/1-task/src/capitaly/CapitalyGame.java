package capitaly;

import java.util.List;

public class CapitalyGame {

    private final List<Field> fields;
    private final List<Player> players;
    private final Dice dice;
    private int currentRound = 0;

    public CapitalyGame(List<Field> fields, List<Player> players, Dice dice) {
        this.fields = fields;
        this.players = players;
        this.dice = dice;
    }

    public void playRound() {
        currentRound++;
        for (Player player : players) {
            if (!player.isAlive()) continue;
            player.move(dice.roll(), fields.size());
            fields.get(player.getPosition()).apply(player);
        }
    }

    public void playRounds(int totalRounds) {
        for (int i = 0; i < totalRounds; i++) {
            if (activePlayers() <= 1) break;
            playRound();
        }
    }

    private int activePlayers() {
        int count = 0;
        for (Player p : players) {
            if (p.isAlive()) count++;
        }
        return count;
    }

    public void printPlayersState() {
        System.out.println("State after " + currentRound + " round(s):");
        for (Player player : players) {
            System.out.printf(
                "%-10s %-8s %-8s money=%-6d pos=%d%n",
                player.getName(),
                player.getStrategyName(),
                player.isAlive() ? "active" : "bankrupt",
                player.getMoney(),
                player.getPosition()
            );

            List<PropertyField> props = player.getProperties();
            if (props.isEmpty()) {
                System.out.println("  no properties");
            } else {
                List<String> desc = props
                    .stream()
                    .map(PropertyField::describe)
                    .toList();
                System.out.println("  properties: " + String.join(", ", desc));
            }
        }
    }
}
