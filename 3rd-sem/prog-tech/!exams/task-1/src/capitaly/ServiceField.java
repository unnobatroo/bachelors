package capitaly;

public class ServiceField extends Field {

    private final int cost;

    public ServiceField(int id, int cost) {
        super(id);
        if (cost <= 0) {
            throw new IllegalArgumentException(
                "Service field cost must be positive, got: " + cost
            );
        }
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public void apply(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null.");
        }
        if (!player.isAlive()) return;

        if (player.getMoney() < cost) {
            player.eliminate();
        } else {
            player.pay(cost);
        }
    }
}
