package capitaly;

public class TacticalPlayer extends Player {

    private boolean skipNextChance = false;

    public TacticalPlayer(String name) {
        super(name, "Tactical");
    }

    @Override
    protected boolean wantsToSpend(int price) {
        if (getMoney() < price) {
            return false;
        }
        boolean buy = !skipNextChance;
        skipNextChance = !skipNextChance;
        return buy;
    }
}
