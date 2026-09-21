package capitaly;

public class ServiceField extends Field {

    private final int cost;

    public ServiceField(int id, int cost) {
        super(id);
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public void apply(Player player) {
        if (player.getMoney() < cost) {
            player.eliminate();
        } else {
            player.pay(cost);
        }
    }
}
