package weapons;

import quests.Monster;

/**
 * Capability interface for magical attacks.
 */
public interface MagicalAttack {
    /**
     * Default magical hit behavior.
     */
    default void castSpell(Monster target) {
        target.takeDamage(12.0f, true);
        System.out.println("Spell cast!");
    }
}
