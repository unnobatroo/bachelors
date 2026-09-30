package capitaly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Player {

    private final String name;
    private final String strategyName;
    private int money = 10_000;
    private int position = 0;
    private boolean alive = true;
    private final List<PropertyField> properties = new ArrayList<>();

    protected Player(String name, String strategyName) {
        this.name = name;
        this.strategyName = strategyName;
    }

    public String getName() {
        return name;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public int getMoney() {
        return money;
    }

    public int getPosition() {
        return position;
    }

    public boolean isAlive() {
        return alive;
    }

    public List<PropertyField> getProperties() {
        return Collections.unmodifiableList(properties);
    }

    public void addProperty(PropertyField property) {
        properties.add(property);
    }

    public void move(int steps, int boardSize) {
        if (!alive) return;
        position = (position + steps) % boardSize;
    }

    public void pay(int amount) {
        money -= amount;
    }

    public void receiveMoney(int amount) {
        money += amount;
    }

    public void eliminate() {
        alive = false;
        for (PropertyField p : properties) {
            p.reset();
        }
        properties.clear();
    }

    public boolean wantsToBuyProperty(int price) {
        return wantsToSpend(price);
    }

    public boolean wantsToBuildHouse(int price) {
        return wantsToSpend(price);
    }

    protected abstract boolean wantsToSpend(int price);

    @Override
    public String toString() {
        return (
            name +
            " (" +
            strategyName +
            ") money=" +
            money +
            " pos=" +
            position +
            " alive=" +
            alive
        );
    }
}
