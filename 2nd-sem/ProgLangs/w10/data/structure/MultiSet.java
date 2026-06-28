package data.structure;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MultiSet<E> {
    private HashMap<E, Integer> elemToCount;

    public MultiSet(E[] elems) {
        elemToCount = new HashMap<>();
        if (elems == null) {
            return;
        }

        for (E elem : elems) {
            add(elem);
        }
    }

    private MultiSet() {
        elemToCount = new HashMap<>();
    }

    public int size() {
        int sum = 0;
        for (int count : elemToCount.values()) {
            sum += count;
        }
        return sum;
    }

    public int getCount(E elem) {
        return elemToCount.getOrDefault(elem, 0);
    }

    public int add(E elem) {
        int newCount = getCount(elem) + 1;
        elemToCount.put(elem, newCount);
        return newCount;
    }

    public MultiSet<E> intersect(MultiSet<E> otherMultiSet) {
        MultiSet<E> result = new MultiSet<>();
        if (otherMultiSet == null) {
            return result;
        }

        for (Map.Entry<E, Integer> entry : elemToCount.entrySet()) {
            E elem = entry.getKey();
            int thisCount = entry.getValue();
            int otherCount = otherMultiSet.getCount(elem);
            int minCount = Math.min(thisCount, otherCount);

            if (minCount > 0) {
                result.elemToCount.put(elem, minCount);
            }
        }

        return result;
    }

    public int countExcept(Set<E> notCounted) {
        int sum = 0;
        for (Map.Entry<E, Integer> entry : elemToCount.entrySet()) {
            if (notCounted == null || !notCounted.contains(entry.getKey())) {
                sum += entry.getValue();
            }
        }
        return sum;
    }
}