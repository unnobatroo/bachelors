package simulation;

import guild.Adventurer;
import guild.Guild;
import quests.Monster;
import quests.Quest;
import weapons.Weapon;
import weapons.inventory.Bow;
import weapons.inventory.Staff;
import weapons.inventory.Sword;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Entry point for running the guild simulation from text input files.
 */
public class Simulation {

    /**
     * Loads data files, builds the guild world, and executes quests.
     *
     * @param args CLI args (unused)
     */
    public static void main(String[] args) {
        Guild guild = new Guild();

        List<Adventurer> adventurers = loadAdventurers(Path.of("simulation", "data", "adventurers.txt"));
        Map<String, Adventurer> adventurerByName = new HashMap<>();
        for (Adventurer adventurer : adventurers) {
            guild.addMember(adventurer);
            adventurerByName.put(adventurer.getName(), adventurer);
        }

        loadWeapons(Path.of("simulation", "data", "weapons.txt"), adventurerByName);
        List<Quest> quests = loadQuests(
                Path.of("simulation", "data", "quests.txt"),
                Path.of("simulation", "data", "monsters.txt"));
        for (Quest quest : quests) {
            guild.addQuest(quest);
        }

        if (guild.getMembers().isEmpty() || guild.getQuests().isEmpty()) {
            System.out.println(
                    "No simulation data found in simulation/data/. Add adventurers.txt, weapons.txt, quests.txt, and monsters.txt.");
            return;
        }

        runSimulation(guild);
    }

    /**
     * Runs all quests once using round-robin adventurer assignment.
     */
    private static void runSimulation(Guild guild) {
        List<Adventurer> members = guild.getMembers();
        List<Quest> quests = guild.getQuests();

        int adventurerIndex = 0;
        for (Quest quest : quests) {
            Adventurer selected = members.get(adventurerIndex % members.size());
            adventurerIndex++;

            System.out.println("\n=== Quest: " + quest.getName() + " | Adventurer: " + selected.getName() + " ===");
            boolean success = quest.attemptQuest(selected);
            System.out.println("Outcome: " + (success ? "SUCCESS" : "FAILURE"));
            System.out.println(
                    "Adventurer HP: " + selected.getHealth() + " | XP: " + selected.getExperience() + " | Rank: "
                            + selected.getRank());
        }
    }

    /**
     * Reads adventurer definitions from file.
     */
    private static List<Adventurer> loadAdventurers(Path file) {
        List<Adventurer> result = new ArrayList<>();
        for (String line : readLines(file)) {
            // format: name;basePower;health
            String[] parts = split(line, 3);
            if (parts == null) {
                continue;
            }

            String name = parts[0];
            float basePower = parseFloat(parts[1], 5.0f);
            float health = parseFloat(parts[2], 100.0f);
            result.add(new Adventurer(name, basePower, health));
        }
        return result;
    }

    /**
     * Reads weapons and attaches them to their adventurers.
     */
    private static void loadWeapons(Path file, Map<String, Adventurer> adventurerByName) {
        for (String line : readLines(file)) {
            // format: adventurerName;type;weaponName;basePower
            String[] parts = split(line, 4);
            if (parts == null) {
                continue;
            }

            Adventurer owner = adventurerByName.get(parts[0]);
            if (owner == null) {
                continue;
            }

            Weapon weapon = createWeapon(parts[1], parts[2], parseFloat(parts[3], 10.0f));
            if (weapon != null) {
                List<Weapon> inventory = owner.getInventory();
                inventory.add(weapon);
            }
        }
    }

    /**
     * Reads quests and monster templates, then maps quest monster lists.
     */
    private static List<Quest> loadQuests(Path questsFile, Path monstersFile) {
        Map<String, Monster> monstersByName = new HashMap<>();
        for (String line : readLines(monstersFile)) {
            // format: name;health;strength;armor
            String[] parts = split(line, 4);
            if (parts == null) {
                continue;
            }
            Monster monster = new Monster(
                    parts[0],
                    parseFloat(parts[1], 40.0f),
                    parseFloat(parts[2], 6.0f),
                    parseFloat(parts[3], 2.0f));
            monstersByName.put(monster.getName(), monster);
        }

        List<Quest> result = new ArrayList<>();
        for (String line : readLines(questsFile)) {
            // format: questName;difficulty;monster1,monster2,...
            String[] parts = split(line, 3);
            if (parts == null) {
                continue;
            }

            String name = parts[0];
            int difficulty = parseInt(parts[1], 1);
            List<Monster> questMonsters = new ArrayList<>();
            String[] monsterNames = parts[2].split(",");
            for (String monsterName : monsterNames) {
                Monster template = monstersByName.get(monsterName.trim());
                if (template == null) {
                    continue;
                }
                questMonsters.add(new Monster(
                        template.getName(),
                        template.getHealth(),
                        template.getStrength(),
                        template.getArmor()));
            }

            result.add(new Quest(name, difficulty, questMonsters));
        }

        return result;
    }

    /**
     * Factory method for weapon creation by type string.
     */
    private static Weapon createWeapon(String type, String name, float basePower) {
        switch (type.trim().toLowerCase()) {
            case "sword":
                return new Sword(name, basePower);
            case "bow":
                return new Bow(name, basePower);
            case "staff":
                return new Staff(name, basePower);
            default:
                return null;
        }
    }

    /**
     * Reads non-empty, non-comment lines from a file.
     */
    private static List<String> readLines(Path file) {
        try {
            if (!Files.exists(file)) {
                return List.of();
            }
            List<String> lines = Files.readAllLines(file);
            List<String> cleaned = new ArrayList<>();
            for (String line : lines) {
                String trimmed = line.trim();
                if (!trimmed.isEmpty() && !trimmed.startsWith("#")) {
                    cleaned.add(trimmed);
                }
            }
            return cleaned;
        } catch (IOException e) {
            return List.of();
        }
    }

    /**
     * Splits semicolon-separated input and enforces expected field count.
     */
    private static String[] split(String line, int expectedSize) {
        String[] parts = line.split(";");
        if (parts.length != expectedSize) {
            return null;
        }
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
        }
        return parts;
    }

    /**
     * Safe float parser with fallback.
     */
    private static float parseFloat(String value, float fallback) {
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            return fallback;
        }
    }

    /**
     * Safe int parser with fallback.
     */
    private static int parseInt(String value, int fallback) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return fallback;
        }
    }
}
