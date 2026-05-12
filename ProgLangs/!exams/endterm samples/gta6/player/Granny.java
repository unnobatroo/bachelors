package player;

import java.util.Objects;
import player.util.ItemType;

public class Granny {
    private String name;
    private ItemType item;
    private int level;
    private double money;

    public Granny(String name, ItemType item, int level, double money) {
        this.name = name;
        this.item = item;
        this.level = level;
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public ItemType getItem() {
        return item;
    }

    public int getLevel() {
        return level;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, item, level, money == -0.0 ? 0.0 : money);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o == this)
            return true;
        if (!(o instanceof Granny))
            return false;
        Granny g = (Granny) o;
        return g.name.equals(name) && g.item == item && g.level == level
                && (g.money == money || Double.isNaN(g.money) && Double.isNaN(money));
    }
}
