package text.util;


import java.util.HashMap;

public class CharacterStatistics {
    private final HashMap<Character, Integer> charToCount;

    public CharacterStatistics(String text) {
        this.charToCount = new HashMap<>();
        for (char c : text.toCharArray()) {
            this.charToCount.put(c, this.charToCount.getOrDefault(c, 0) + 1);
        }
    }

    public int getCount(char c) {
        return this.charToCount.getOrDefault(c, 0);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        this.charToCount.entrySet().forEach(entry -> {
            sb.append(entry.getKey()).append("(").append(entry.getValue()).append(") ");
        });
        return sb.toString().trim();
    }
}