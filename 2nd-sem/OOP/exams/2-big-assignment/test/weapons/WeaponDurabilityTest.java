package weapons;

import org.junit.jupiter.api.Test;
import quests.Monster;
import weapons.inventory.Bow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WeaponDurabilityTest {

    @Test
    void bowAttackReducesDurabilityAndTransitionsCondition() {
        Bow bow = new Bow("Oak Bow", 20.0f);
        Monster monster = new Monster("Target", 200.0f, 0.0f, 0.0f);

        bow.performAttack(monster);
        assertEquals(92, bow.getDurability());
        assertEquals(Condition.USED, bow.getCondition());

        bow.setDurability(8);
        bow.performAttack(monster);
        assertEquals(0, bow.getDurability());
        assertEquals(Condition.BROKEN, bow.getCondition());
        assertTrue(bow.isBroken());
    }
}
