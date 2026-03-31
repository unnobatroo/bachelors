package text.util;

import java.util.HashMap;

public class CharacterStatistics {
    private HashMap<Character, Integer> charToCount = new HashMap<>();

    public CharacterStatistics(String text) {
        for (char c : text.toCharArray()) {
            if (charToCount.containsKey(c)) {
                charToCount.put(c, charToCount.get(c) + 1);
            } else {
                charToCount.put(c, 1);
            }
        }
    }

    public int getCount(char c) {
        return charToCount.getOrDefault(c, 0);
    }

    @Override
    public String toString() {
        String result = "";

        for (Character c : charToCount.keySet()) {
            if (!result.isEmpty()) {
                result += " ";
            }
            result += String.format("%c(%d)", c, charToCount.get(c));
        }

        return result;
    }
}
