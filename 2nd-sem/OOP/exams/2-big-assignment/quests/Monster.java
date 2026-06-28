package quests;

import guild.Adventurer;

/**
 * Enemy unit with health, strength, and armor.
 */
public class Monster {
    private String name;
    private float health;
    private float strength;
    private float armor;

    public Monster(String name, float health, float strength, float armor) {
        this.name = name;
        this.health = health;
        this.strength = strength;
        this.armor = armor;
    }

    /**
     * Applies damage and optionally bypasses armor.
     */
    public void takeDamage(float amount, boolean bypassArmor) {
        float appliedDamage = bypassArmor ? amount : Math.max(0, amount - armor);
        this.health = Math.max(0, this.health - appliedDamage);
    }

    /**
     * Deals strength damage to an adventurer.
     */
    public void attack(Adventurer adventurer) {
        if (health <= 0) {
            return;
        }
        adventurer.takeDamage(strength);
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

    public float getArmor() {
        return armor;
    }

    public void setArmor(float armor) {
        this.armor = armor;
    }
}