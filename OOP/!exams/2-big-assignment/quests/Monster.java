package quests;

public class Monster {
    private String name;
    private float health;
    private float strength;
    private float armour;

    public Monster(String name, float health, float strength, float armour) {
        this.name = name;
        this.health = health;
        this.strength = strength;
        this.armour = armour;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public float getStrength() {
        return strength;
    }

    public void setStrength(float strength) {
        this.strength = strength;
    }

    public float getArmour() {
        return armour;
    }

    public void setArmour(float armour) {
        this.armour = armour;
    }
}