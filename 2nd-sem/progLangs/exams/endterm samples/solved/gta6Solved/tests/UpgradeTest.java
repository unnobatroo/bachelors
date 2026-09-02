package tests;

import progress.upgrades.Upgrade;
import progress.upgrades.util.PurchaseException;
import player.Granny;
import player.util.ItemType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class UpgradeTest {
    @Test
    public void testUpgradeConstructor() {
        Upgrade u = new Upgrade("Speed boost", 100, 5);
        assertEquals("Speed boost", u.getName());
        assertEquals(100, u.getPrice());
        assertEquals(5, u.getRequiredLevel());
    }
    @Test
    public void testIsAffordableSuccess() throws PurchaseException {
        Granny g = new Granny("Test", ItemType.CANE, 5, 500);
        Upgrade u = new Upgrade("Speed boost", 100, 5);
        assertTrue(u.isAffordable(g));
    }
    @Test
    public void testIsAffordableFailure() {
        Granny g1 = new Granny("Test", ItemType.CANE, 4, 500);
        Granny g2 = new Granny("Test", ItemType.CANE, 5, 50);
        Upgrade u = new Upgrade("Speed boost", 100, 5);
        try {
            u.isAffordable(g1);
            fail();
        } catch (PurchaseException e) {
            assertEquals("Level not high enough!", e.getMessage());
        }
        try {
            u.isAffordable(g2);
            fail();
        } catch (PurchaseException e) {
            assertEquals("Not enough money!", e.getMessage());
        }
    }
}
