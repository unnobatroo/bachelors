package math.operation.safe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class IncrementTest {
    @Test
    public void zeroInputTest() {
        assertEquals(1, Increment.increment(0), "Zero input failed :(");
    }

    @Test
    public void smallestPossibleTest() {
        assertEquals(Integer.MIN_VALUE + 1, Increment.increment(Integer.MIN_VALUE), "Min value failed :(");
    }
}
