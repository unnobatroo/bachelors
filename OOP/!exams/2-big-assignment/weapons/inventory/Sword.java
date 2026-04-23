package weapons.inventory;

import quests.Monster;
import weapons.MeleeAttack;
import weapons.Weapon;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Melee weapon with a critical strike chance.
 */
public class Sword extends Weapon implements MeleeAttack {

    public Sword(String name, float basePower) {
        super(name, basePower);
    }

    /**
     * Performs sword attack and applies critical logic.
     */
    @Override
    public void performAttack(Monster target) {
        if (isBroken()) {
            return;
        }

        float damage = getEffectivePower();
        int durabilityCost = 10;

        if (ThreadLocalRandom.current().nextFloat() < 0.20f) {
            damage *= 2.0f;
            durabilityCost *= 2;
        }

        target.takeDamage(damage, false);
        reduceDurability(durabilityCost);
    }

}
