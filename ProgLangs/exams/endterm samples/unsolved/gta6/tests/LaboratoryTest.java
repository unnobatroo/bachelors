package tests;

//javac -cp ";junit5all.jar;checkthat.jar" suites/*.java structuretests/*.java tests/*.java player/*.java player/util/*.java progress/*.java progress/upgrades/*java progress/upgrades/util/*.java
//java -jar junit5all.jar execute -c suites.GTA6TestSuite -cp . -cp checkthat.jar

//./check.cmd suites/GTA6TestSuite.java suites.GTA6TestSuite
//cmd /c "del /S /Q *.class"

//Grading key: ItemType(2)+Granny(8)+GrannyTest(2+3) + PurchaseException(3)+Purchasable(2)+Upgrade(5)+UpgradeTest(1+3+3) + Laboratory(12)+LaboratoryTest(2+2+2)
//2+8+2+3 + 3+2+5+1+3+3 + 12+2+2+2=50

import progress.Laboratory;
import progress.upgrades.Upgrade;
import progress.upgrades.util.PurchaseException;
import player.Granny;
import player.util.ItemType;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

public class LaboratoryTest {
    static Upgrade[] upgrades = new Upgrade[] {
        new Upgrade("Speed boost", 100, 5),
        new Upgrade("Bingo luck", 900, 10),
        new Upgrade("Secret recipe", 2000, 20),
        new Upgrade("Cheat codes", 9999.9, 99),
    };
    @Test
    public void testPurchaseUpgradeSuccess() throws PurchaseException {
        Laboratory l = new Laboratory(upgrades);
        Granny g = new Granny("Test", ItemType.CANE, 5, 500);
        l.purchaseUpgrade(g, upgrades[0]);
        //System.out.println(l);
    }
    @Test
    public void testPurchaseUpgradeFailure() throws PurchaseException {
        Laboratory l = new Laboratory(upgrades);
        Granny g = new Granny("Test", ItemType.CANE, 5, 500);
        l.purchaseUpgrade(g, upgrades[0]);
        try {
            l.purchaseUpgrade(g, upgrades[0]);
            fail();
        } catch (PurchaseException e) {
            assertEquals("Upgrade not available!", e.getMessage());
        }
    }
    @Test
    public void testHasPurchasableUpgrade() {
        Laboratory l = new Laboratory(upgrades);
        Granny g1 = new Granny("Test", ItemType.CANE, 5, 500);
        Granny g2 = new Granny("Test", ItemType.CANE, 4, 500);
        assertTrue(l.hasPurchasableUpgrade(g1));
        assertFalse(l.hasPurchasableUpgrade(g2));
    }
}
