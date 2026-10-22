package capitaly;

public class GreedyPlayer extends Player {

    public GreedyPlayer(String name) {
        super(name, "Greedy");
    }

    @Override
    protected boolean wantsToSpend(int price) {
        return getMoney() >= price;
    }
}
