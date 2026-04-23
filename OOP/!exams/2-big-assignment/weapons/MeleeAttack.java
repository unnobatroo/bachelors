package weapons;

import quests.Monster;

/**
 * Capability interface for melee attacks.
 */
public interface MeleeAttack {
    /**
     * Default melee hit behavior.
     */
    default void strike(Monster target) {
        target.takeDamage(10.0f, false);
        System.out.println("Melee strike landed!");
    }
}
