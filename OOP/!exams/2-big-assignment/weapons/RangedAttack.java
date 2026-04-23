package weapons;

import quests.Monster;

/**
 * Capability interface for ranged attacks.
 */
public interface RangedAttack {
    /**
     * Default ranged hit behavior.
     */
    default void shoot(Monster target) {
        target.takeDamage(8.0f, false);
        System.out.println("Arrow fired!");
    }
}
