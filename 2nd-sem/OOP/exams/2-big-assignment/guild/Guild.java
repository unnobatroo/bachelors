package guild;

import quests.Quest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Guild board manager for members and quests.
 */
public class Guild {
    private List<Adventurer> members;
    private List<Quest> quests;

    public Guild() {
        this.members = new ArrayList<>();
        this.quests = new ArrayList<>();
    }

    /**
     * Assigns a random quest to a random member.
     */
    public void assignRandomQuest() {
        if (members.isEmpty() || quests.isEmpty()) {
            return;
        }

        Adventurer adventurer = members.get(ThreadLocalRandom.current().nextInt(members.size()));
        Quest quest = quests.get(ThreadLocalRandom.current().nextInt(quests.size()));

        boolean success = quest.attemptQuest(adventurer);
        String status = success ? "completed" : "failed";
        System.out.println(adventurer.getName() + " " + status + " quest " + quest.getName());
    }

    /**
     * Adds a member to the guild.
     */
    public void addMember(Adventurer adventurer) {
        if (adventurer != null) {
            members.add(adventurer);
        }
    }

    /**
     * Adds a quest to the guild board.
     */
    public void addQuest(Quest quest) {
        if (quest != null) {
            quests.add(quest);
        }
    }

    public List<Adventurer> getMembers() {
        return members;
    }

    public void setMembers(List<Adventurer> members) {
        this.members = members == null ? new ArrayList<>() : members;
    }

    public List<Quest> getQuests() {
        return quests;
    }

    public void setQuests(List<Quest> quests) {
        this.quests = quests == null ? new ArrayList<>() : quests;
    }
}
