package spaceexploration.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ResourceTest {
    @Test
    public void testCollect()
    {
        Ore o = new Ore("200-ME", Type.METAL);
        assertTrue(!o.isStable && !o.tagged);
        assertNull(o.collect());
        o.tag();
        assertTrue(o.collect().endsWith("#FOUND"));
    }

    @Test
    public void testTag()
    {
        Ore o = new Ore("200-ME", Type.METAL);
        assertTrue(!o.isStable && !o.tagged);
        o.tag();
        assertTrue(o.isStable && o.tagged);
        o.tag();
        assertTrue(!o.isStable && o.tagged);
    }

    @Test
    public void testEquals()
    {
        Ore o1 = new Ore("200-ME", Type.METAL);
        Ore o2 = new Ore("200-ME2", Type.METAL);
        Ore o3 = new Ore("200-CR", Type.CRYSTAL);
        Ore o4 = new Ore("201-ME", Type.METAL);
        assertEquals(o1, o2);
        assertEquals(o1.hashCode(), o2.hashCode());
        assertNotEquals(o1, o3);
        assertNotEquals(o1, o4);
    }

    @Test
    public void testText()
    {
        Ore o = new Ore("200-ME", Type.METAL);
        assertEquals("Position: 200-ME, Type: METAL, isStable: false", o.toString());
        o.collect();
        assertEquals("Position: 200-ME, Type: METAL, isStable: false", o.toString());
        o.collect();
        assertEquals("Position: 200-ME, Type: METAL, isStable: false", o.toString());
    }
}
