package capitaly;

public class LuckyField extends Field {

    private final int reward;

    public LuckyField(int id, int reward) {
        super(id);
        if (reward <= 0) {
            throw new IllegalArgumentException(
                "Lucky field reward must be positive, got: " + reward
            );
        }
        this.reward = reward;
    }

    public int getReward() {
        return reward;
    }

    @Override
    public void apply(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null.");
        }
        if (!player.isAlive()) return;

        player.receiveMoney(reward);
    }
}
