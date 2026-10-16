package capitaly;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@FunctionalInterface
public interface Dice {
    int roll();

    static Dice random() {
        return () -> ThreadLocalRandom.current().nextInt(1, 7);
    }

    static Dice fromList(List<Integer> rolls) {
        if (rolls == null || rolls.isEmpty()) {
            throw new IllegalArgumentException(
                "Dice rolls list cannot be null or empty."
            );
        }
        for (Integer r : rolls) {
            if (r == null || r < 1 || r > 6) {
                throw new IllegalArgumentException(
                    "Each dice roll must be between 1 and 6, got: " + r
                );
            }
        }
        List<Integer> copy = List.copyOf(rolls);
        return new Dice() {
            private int i = 0;

            @Override
            public int roll() {
                int value = copy.get(i);
                i = (i + 1) % copy.size();
                return value;
            }
        };
    }
}
