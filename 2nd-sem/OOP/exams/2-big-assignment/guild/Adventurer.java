package guild;

import quests.Monster;
import weapons.Weapon;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Playable guild member with combat stats, progression, and inventory.
 */
public class Adventurer {
    private String name;
    private float basePower;
    private float health;
    private int experience;
    private int rank;
    private List<Weapon> inventory;

    public Adventurer(String name, float basePower, float health) {
        this.name = name;
        this.basePower = basePower;
        this.health = health;
        this.experience = 0;
        this.rank = 1;
        this.inventory = new ArrayList<>();
    }

    /**
     * Attacks a monster using the strongest available weapon.
     */
    public void attack(Monster monster) {
        Weapon strongest = getStrongestWeapon();
        if (strongest == null || strongest.isBroken()) {
            float unarmedDamage = Math.max(1.0f, basePower + rank);
            monster.takeDamage(unarmedDamage, false);
            return;
        }

        strongest.performAttack(monster);
        monster.takeDamage(Math.max(0, basePower + rank), false);
    }

    /**
     * Applies incoming damage to the adventurer.
     */
    public void takeDamage(float amount) {
        this.health = Math.max(0, this.health - amount);
    }

    /**
     * Returns the highest effective power non-broken weapon.
     */
    public Weapon getStrongestWeapon() {
        return inventory.stream()
                .filter(w -> !w.isBroken())
                .max(Comparator.comparing(Weapon::getEffectivePower))
                .orElse(null);
    }

    /**
     * Increases experience and updates rank thresholds.
     */
    public void gainExperience(int amount) {
        if (amount <= 0) {
            return;
        }

        this.experience += amount;

        int newRank = (this.experience / 100) + 1;
        if (newRank > this.rank) {
            this.rank = newRank;
        }
    }

    /**
     * Repairs a weapon by spending experience.
     */
    public boolean repairWeapon(Weapon weapon) {
        if (weapon == null || !inventory.contains(weapon)) {
            return false;
        }

        int repairCost = Math.max(1, Math.round(weapon.getBasePower() * 0.5f));
        if (experience < repairCost) {
            return false;
        }

        experience -= repairCost;
        weapon.setDurability(100);
        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getBasePower() {
        return basePower;
    }

    public void setBasePower(float basePower) {
        this.basePower = basePower;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = Math.max(0, experience);
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = Math.max(1, rank);
    }

    public List<Weapon> getInventory() {
        return inventory;
    }

    public void setInventory(List<Weapon> inventory) {
        this.inventory = inventory == null ? new ArrayList<>() : inventory;
    }

}
