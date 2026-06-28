package weapons;

import quests.Monster;

/**
 * Base weapon abstraction with durability and condition handling.
 */
public abstract class Weapon {
    protected String name;
    protected float basePower;
    protected int durability = 100;
    protected Condition condition = Condition.NEW;

    public Weapon(String name, float basePower) {
        this.name = name;
        this.basePower = basePower;
    }

    /**
     * Performs an attack against a monster target.
     */
    public abstract void performAttack(Monster target);

    /**
     * Reduces durability and updates condition.
     */
    public void reduceDurability(int amount) {
        durability -= amount;
        if (durability < 0)
            durability = 0;
        condition = Condition.fromDurability(durability);
    }

    /**
     * Computes power after applying state multiplier and NEW bonus.
     */
    public float getEffectivePower() {
        float multiplier = condition.getPowerMultiplier();
        if (condition == Condition.NEW) {
            multiplier *= 1.10f;
        }
        return basePower * multiplier;
    }

    /**
     * Checks whether the weapon can still be used.
     */
    public boolean isBroken() {
        return condition == Condition.BROKEN;
    }

    public float getBasePower() {
        return basePower;
    }

    public void setBasePower(float basePower) {
        this.basePower = basePower;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = Math.max(0, Math.min(100, durability));
        this.condition = Condition.fromDurability(this.durability);
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
