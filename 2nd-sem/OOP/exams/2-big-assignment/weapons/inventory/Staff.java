package weapons.inventory;

import quests.Monster;
import weapons.MagicalAttack;
import weapons.Weapon;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Magical weapon that can weaken monster strength.
 */
public class Staff extends Weapon implements MagicalAttack {

    public Staff(String name, float basePower) {
        super(name, basePower);
    }

    /**
     * Performs a magical attack with optional debuff proc.
     */
    @Override
    public void performAttack(Monster target) {
        if (isBroken()) {
            return;
        }

        target.takeDamage(getEffectivePower(), true);

        if (ThreadLocalRandom.current().nextFloat() < 0.25f) {
            target.setStrength(target.getStrength() * 0.5f);
        }

        reduceDurability(12);
    }
}
