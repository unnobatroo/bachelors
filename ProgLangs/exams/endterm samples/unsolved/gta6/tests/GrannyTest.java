package tests;

import player.Granny;
import player.util.ItemType;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class GrannyTest {
    @Test
    public void testGrannyConstructor()
    {
        Granny g = new Granny("Test", ItemType.CANE, 5, 500);
        assertEquals("Test", g.getName());
        assertEquals(ItemType.CANE, g.getItem());
        assertEquals(5, g.getLevel());
        assertEquals(500, g.getMoney());
    }
    @Test
    public void testGrannyEqualityCheck()
    {
        Granny g1 = new Granny("Test", ItemType.CANE, 5, 500);
        Granny g2 = new Granny("Test", ItemType.CANE, 5, 500);
        Granny g3 = new Granny("Other", ItemType.CANE, 5, 500);
        assertEquals(g1, g2);
        assertEquals(g1.hashCode(), g2.hashCode());
        assertNotEquals(g1, g3);
    }
}
