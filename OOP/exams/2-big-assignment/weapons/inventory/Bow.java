package weapons.inventory;

import quests.Monster;
import weapons.RangedAttack;
import weapons.Weapon;

/**
 * Ranged weapon with moderate durability usage.
 */
public class Bow extends Weapon implements RangedAttack {

    public Bow(String name, float basePower) {
        super(name, basePower);
    }

    /**
     * Performs a ranged bow attack.
     */
    @Override
    public void performAttack(Monster target) {
        if (isBroken()) {
            return;
        }

        target.takeDamage(getEffectivePower(), false);
        reduceDurability(8);
    }
}
