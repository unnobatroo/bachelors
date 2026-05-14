package museum.relic;

import module org.junit.jupiter;
import static org.junit.jupiter.api.Assertions.*;
import museum.utils.*;

public class RelicTest {
    @Test
    public void relicConstructorTest() {
        Relic relic = new Relic("Test", RelicType.SCULPTURE);
        assertEquals("Test", relic.getRelicName());
        assertEquals(RelicType.SCULPTURE, relic.getRelicType());
    }
    @Test
    public void relicEqualityCheckTest() {
        Relic r1 = new Relic("Test", RelicType.SCULPTURE);
        Relic r2 = new Relic("Test", RelicType.SCULPTURE);
        Relic r3 = new Relic("Test2", RelicType.SCULPTURE);
        Relic r4 = new Relic("Test", RelicType.PAINTING);
        assertEquals(r1, r2);
        assertNotEquals(r1, r3);
        assertNotEquals(r1, r4);
        assertEquals(r1.hashCode(), r2.hashCode());
    }
}