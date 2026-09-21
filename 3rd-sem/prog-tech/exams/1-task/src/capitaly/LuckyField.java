package capitaly;

public class LuckyField extends Field {

    private final int reward;

    public LuckyField(int id, int reward) {
        super(id);
        this.reward = reward;
    }

    public int getReward() {
        return reward;
    }

    @Override
    public void apply(Player player) {
        player.receiveMoney(reward);
    }
}
