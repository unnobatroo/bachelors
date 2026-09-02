package quests;

import guild.Adventurer;
import org.junit.jupiter.api.Test;
import weapons.inventory.Bow;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QuestScenarioTest {

    @Test
    void scenario31SuccessfulQuestCompletion() {
        Adventurer adventurer = new Adventurer("Aria", 4.0f, 100.0f);
        adventurer.getInventory().add(new Bow("Hunter Bow", 20.0f));

        Monster monster = new Monster("Goblin Champion", 120.0f, 8.0f, 2.0f);
        Quest quest = new Quest("Champion Hunt", 2, List.of(monster));

        boolean result = quest.attemptQuest(adventurer);

        assertTrue(result);
        assertTrue(quest.isComplete());
        assertTrue(adventurer.getExperience() > 0);
        assertTrue(adventurer.getRank() > 1);
    }

    @Test
    void scenario32EquipmentFailureCanCauseQuestFailure() {
        Adventurer adventurer = new Adventurer("Bram", 1.0f, 25.0f);
        Bow fragileBow = new Bow("Old Bow", 4.0f);
        fragileBow.setDurability(8);
        adventurer.getInventory().add(fragileBow);

        Monster armoredMonster = new Monster("Stone Golem", 100.0f, 6.0f, 10.0f);
        Quest quest = new Quest("Golem Trial", 1, List.of(armoredMonster));

        boolean result = quest.attemptQuest(adventurer);

        assertFalse(result);
        assertFalse(quest.isComplete());
        assertTrue(fragileBow.isBroken());
    }
}
