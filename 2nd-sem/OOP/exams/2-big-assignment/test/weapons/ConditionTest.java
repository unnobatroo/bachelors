package weapons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConditionTest {

    @Test
    void fromDurabilityMapsToExpectedState() {
        assertEquals(Condition.NEW, Condition.fromDurability(100));
        assertEquals(Condition.USED, Condition.fromDurability(99));
        assertEquals(Condition.USED, Condition.fromDurability(50));
        assertEquals(Condition.DAMAGED, Condition.fromDurability(49));
        assertEquals(Condition.DAMAGED, Condition.fromDurability(1));
        assertEquals(Condition.BROKEN, Condition.fromDurability(0));
    }
}
