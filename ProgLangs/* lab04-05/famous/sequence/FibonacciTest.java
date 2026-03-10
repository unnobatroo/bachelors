package famous.sequence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class FibonacciTest {

    @Test
    void testFibonacci() {
        assertEquals(0, Fibonacci.fib(0));
        assertEquals(1, Fibonacci.fib(1));
        assertEquals(1, Fibonacci.fib(2));
        assertEquals(2, Fibonacci.fib(3));
        assertEquals(3, Fibonacci.fib(4));
        assertEquals(5, Fibonacci.fib(5));
        assertEquals(8, Fibonacci.fib(6));
        assertEquals(13, Fibonacci.fib(7));
        assertEquals(21, Fibonacci.fib(8));
        assertEquals(34, Fibonacci.fib(9));
        assertEquals(55, Fibonacci.fib(10));
    }

    @ParameterizedTest
    @CsvSource({"0, 0", "1, 1", "1, 2", "2, 3", "3, 4", "5,5", "8,6", "13,7",
        "21,8", "34,9", "55,10"})
    void testFibonacciParametrized(int expected, int n) {
        assertEquals(expected, Fibonacci.fib(n));
    }
}