package weapons;

import quests.Monster;

abstract class Weapon {
    protected String name;
    protected float basePower;
    protected int durability = 100;
    protected Condition condition = Condition.NEW;

    public void performAttack(Monster target) {

    }

    public void reduceDurability(int amount) {
        durability -= amount;
        if (durability < 0) durability = 0;
        condition = Condition.fromDurability(durability);
    }

    public float getEffectivePower() {

    }

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
        this.durability = durability;
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
