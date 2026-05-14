package guild;

import org.junit.jupiter.api.Test;
import quests.Monster;
import weapons.inventory.Bow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CombatAndRepairTest {

    @Test
    void combatDamageUsesArmorAndBypassRules() {
        Monster monster = new Monster("Orc", 100.0f, 10.0f, 5.0f);

        monster.takeDamage(12.0f, false);
        assertEquals(93.0f, monster.getHealth(), 0.001f);

        monster.takeDamage(12.0f, true);
        assertEquals(81.0f, monster.getHealth(), 0.001f);
    }

    @Test
    void repairConsumesExperienceAndRestoresWeapon() {
        Adventurer adventurer = new Adventurer("Luna", 3.0f, 50.0f);
        Bow bow = new Bow("Field Bow", 20.0f);
        bow.setDurability(10);
        adventurer.getInventory().add(bow);

        assertFalse(adventurer.repairWeapon(bow));

        adventurer.setExperience(20);
        assertTrue(adventurer.repairWeapon(bow));
        assertEquals(10, adventurer.getExperience());
        assertEquals(100, bow.getDurability());
    }
}
