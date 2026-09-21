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
        return new Dice() {
            private int i = 0;

            @Override
            public int roll() {
                return rolls.get(i++ % rolls.size());
            }
        };
    }
}
