package capitaly;

public class PropertyField extends Field {

    public static final int BUY_PRICE = 1000;
    public static final int HOUSE_PRICE = 4000;
    public static final int BASE_RENT = 500;
    public static final int HOUSE_RENT = 2000;

    private Player owner;
    private boolean hasHouse;

    public PropertyField(int id) {
        super(id);
    }

    public Player getOwner() {
        return owner;
    }

    public boolean hasHouse() {
        return hasHouse;
    }

    public void reset() {
        this.owner = null;
        this.hasHouse = false;
    }

    @Override
    public void apply(Player player) {
        if (owner == null) {
            if (player.wantsToBuyProperty(BUY_PRICE)) {
                player.pay(BUY_PRICE);
                this.owner = player;
                player.addProperty(this);
            }
        } else if (owner == player) {
            if (!hasHouse && player.wantsToBuildHouse(HOUSE_PRICE)) {
                player.pay(HOUSE_PRICE);
                this.hasHouse = true;
            }
        } else {
            int rent = hasHouse ? HOUSE_RENT : BASE_RENT;
            if (player.getMoney() < rent) {
                owner.receiveMoney(player.getMoney());
                player.eliminate();
            } else {
                player.pay(rent);
                owner.receiveMoney(rent);
            }
        }
    }

    public String describe() {
        return "Field #" + getId() + (hasHouse ? " [House]" : "");
    }
}
