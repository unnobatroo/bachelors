package ranger;

import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class RangerTest {
    private Ranger ranger;
    private StreamRanger streamRanger;
    private Arrow fireArrow1, fireArrow2, fireArrow3;
    private Arrow iceArrow1, iceArrow2;
    private Arrow poisonArrow1, poisonArrow2, poisonArrow3, poisonArrow4;

    @Before
    public void setUp() {
        ranger = new Ranger();
        streamRanger = new StreamRanger();

        fireArrow1 = new Arrow(10, "Fire");
        fireArrow2 = new Arrow(15, "Fire");
        fireArrow3 = new Arrow(12, "Fire");
        iceArrow1 = new Arrow(12, "Ice");
        iceArrow2 = new Arrow(8, "Ice");
        poisonArrow1 = new Arrow(20, "Poison");
        poisonArrow2 = new Arrow(20, "Poison");
        poisonArrow3 = new Arrow(25, "Poison");
        poisonArrow4 = new Arrow(27, "Poison");

        ranger.addArrow(fireArrow1);
        ranger.addArrow(iceArrow1);
        ranger.addArrow(poisonArrow1);
        ranger.addArrow(poisonArrow3);
        ranger.addArrow(fireArrow3);

        streamRanger.addArrow(fireArrow2);
        streamRanger.addArrow(iceArrow2);
        streamRanger.addArrow(poisonArrow2);
        streamRanger.addArrow(poisonArrow4);
    }

    @Test
    public void testQuickShot() {
        assertNotNull("QuickShot should return an arrow", ranger.quickShot("Fire"));
        assertNotNull("QuickShot should return an arrow", streamRanger.quickShot("Fire"));
        assertNull("QuickShot should return null for a non-existent type", ranger.quickShot("Electric"));
    }

    @Test
    public void testCalledShot() {
        assertEquals("CalledShot should return the strongest poison arrow", poisonArrow3, ranger.calledShot("Poison"));
        assertEquals("CalledShot should return the strongest poison arrow", poisonArrow4,
                streamRanger.calledShot("Poison"));
        assertNull("CalledShot should return null for unknown type", ranger.calledShot("Electric"));
    }

    @Test
    public void testRainOfArrows() {
        List<Arrow> rangerFireArrows = ranger.rainOfArrows("Fire");
        List<Arrow> streamFireArrows = streamRanger.rainOfArrows("Fire");

        assertFalse("Ranger should have Fire arrows", rangerFireArrows.isEmpty());
        assertFalse("StreamRanger should have Fire arrows", streamFireArrows.isEmpty());

        assertEquals("Ranger should have two Fire arrows", 2, rangerFireArrows.size());
        assertEquals("StreamRanger should have one Fire arrow", 1, streamFireArrows.size());
    }

    @Test
    public void testCountArrows() {
        assertEquals("Ranger should have 2 Fire arrows", 2, ranger.countArrows("Fire"));
        assertEquals("StreamRanger should have 1 Fire arrow", 1, streamRanger.countArrows("Fire"));
        assertEquals("Ranger should have 0 Electric arrows", 0, ranger.countArrows("Electric"));
    }

    @Test
    public void testShootArrow() {
        assertTrue("Ranger should shoot a Poison arrow", ranger.shoot(poisonArrow1));
        assertFalse("Ranger should not shoot an arrow twice", ranger.shoot(poisonArrow1));
        assertTrue("StreamRanger should shoot a Poison arrow", streamRanger.shoot(poisonArrow2));
        assertFalse("StreamRanger should not shoot an arrow twice", streamRanger.shoot(poisonArrow2));

        assertEquals("Ranger should have 1 Poison arrow left", 1, ranger.countArrows("Poison"));
        assertEquals("StreamRanger should have 1 Poison arrow left", 1, streamRanger.countArrows("Poison"));
    }

    @Test
    public void testShootManyArrows() {
        Arrow[] arrowsToShoot = { fireArrow1, iceArrow1 };
        assertTrue("shootMany should execute without failure", ranger.shootMany(arrowsToShoot));

        Arrow[] streamArrowsToShoot = { fireArrow2, iceArrow2 };
        assertTrue("shootMany should execute without failure", streamRanger.shootMany(streamArrowsToShoot));

        assertEquals("Ranger should have one Fire arrow left", 1, ranger.countArrows("Fire"));
        assertEquals("Ranger should have zero Ice arrows left", 0, ranger.countArrows("Ice"));

        assertEquals("StreamRanger should have zero Fire arrows left", 0, streamRanger.countArrows("Fire"));
        assertEquals("StreamRanger should have zero Ice arrows left", 0, streamRanger.countArrows("Ice"));
    }

    @Test
    public void testEmptyQuiverAfterShooting() {
        Arrow[] allRangerArrows = { fireArrow1, iceArrow1, poisonArrow1, poisonArrow3, fireArrow3 };
        assertTrue("Ranger should shoot all arrows", ranger.shootMany(allRangerArrows));

        assertEquals("Ranger's quiver should be empty", 0,
                ranger.countArrows("Fire") + ranger.countArrows("Ice") + ranger.countArrows("Poison"));

        Arrow[] allStreamArrows = { fireArrow2, iceArrow2, poisonArrow2, poisonArrow4 };
        assertTrue("StreamRanger should shoot all arrows", streamRanger.shootMany(allStreamArrows));

        assertEquals("StreamRanger's quiver should be empty", 0, streamRanger.countArrows("Fire")
                + streamRanger.countArrows("Ice") + streamRanger.countArrows("Poison"));
    }
}