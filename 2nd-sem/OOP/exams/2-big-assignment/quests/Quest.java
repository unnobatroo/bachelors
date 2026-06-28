package quests;

import guild.Adventurer;

import java.util.ArrayList;
import java.util.List;

/**
 * Quest containing ordered monster encounters.
 */
public class Quest {
    private String name;
    private int difficulty;
    private boolean isComplete;
    private List<Monster> monsters;

    public Quest(String name, int difficulty, List<Monster> monsters) {
        this.name = name;
        this.difficulty = difficulty;
        this.isComplete = false;
        this.monsters = monsters == null ? new ArrayList<>() : monsters;
    }

    /**
     * Executes turn-based combat against all quest monsters in sequence.
     */
    public boolean attemptQuest(Adventurer adventurer) {
        if (adventurer == null || adventurer.getHealth() <= 0) {
            return false;
        }

        for (Monster monster : monsters) {
            while (monster.getHealth() > 0 && adventurer.getHealth() > 0) {
                adventurer.attack(monster);
                if (monster.getHealth() > 0) {
                    monster.attack(adventurer);
                }
            }

            if (adventurer.getHealth() <= 0) {
                isComplete = false;
                return false;
            }

            int rewardXp = Math.max(10, Math.round((monster.getStrength() + monster.getArmor()) * 10 * difficulty));
            adventurer.gainExperience(rewardXp);
        }

        isComplete = true;
        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = Math.max(1, difficulty);
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public void setMonsters(List<Monster> monsters) {
        this.monsters = monsters == null ? new ArrayList<>() : monsters;
    }
}
