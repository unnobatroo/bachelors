package math.operation.safe;

public class Increment {
    static int increment(int num) {
        return num == Integer.MAX_VALUE ? num : num + 1;
    }
}
