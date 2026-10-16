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
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                "Player name cannot be null or blank."
            );
        }
        if (strategyName == null || strategyName.isBlank()) {
            throw new IllegalArgumentException(
                "Strategy name cannot be null or blank."
            );
        }
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
        if (property == null) {
            throw new IllegalArgumentException("Property cannot be null.");
        }
        properties.add(property);
    }

    public void move(int steps, int boardSize) {
        if (steps <= 0) {
            throw new IllegalArgumentException(
                "Steps must be positive, got: " + steps
            );
        }
        if (boardSize <= 0) {
            throw new IllegalArgumentException(
                "Board size must be positive, got: " + boardSize
            );
        }
        if (!alive) return;
        position = (position + steps) % boardSize;
    }

    public void pay(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException(
                "Payment amount cannot be negative, got: " + amount
            );
        }
        if (amount > money) {
            throw new IllegalArgumentException(
                "Payment amount (" +
                    amount +
                    ") exceeds available balance (" +
                    money +
                    ")."
            );
        }
        money -= amount;
    }

    public void receiveMoney(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException(
                "Received amount cannot be negative, got: " + amount
            );
        }
        money += amount;
    }

    public void eliminate() {
        alive = false;
        money = 0;
        for (PropertyField p : properties) {
            p.reset();
        }
        properties.clear();
    }

    public boolean wantsToBuyProperty(int price) {
        if (price < 0) {
            throw new IllegalArgumentException(
                "Property price cannot be negative, got: " + price
            );
        }
        if (!alive) return false;
        return wantsToSpend(price);
    }

    public boolean wantsToBuildHouse(int price) {
        if (price < 0) {
            throw new IllegalArgumentException(
                "House price cannot be negative, got: " + price
            );
        }
        if (!alive) return false;
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
