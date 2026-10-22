package capitaly;

public class CarefulPlayer extends Player {

    public CarefulPlayer(String name) {
        super(name, "Careful");
    }

    @Override
    protected boolean wantsToSpend(int price) {
        return price <= getMoney() / 2;
    }
}
