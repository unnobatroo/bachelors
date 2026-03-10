import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    Use.theClass("famous.sequence.Fibonacci")
       .that(hasUsualModifiers());
}

@Test
public void methodFib() {
    it.hasMethod("fib", withParams("n: int"))
      .thatIs(USABLE_WITHOUT_INSTANCE, FULLY_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_ALL)
      .thatReturns("int")
      .info("""
          Returns the `n`th number in the Fibonacci sequence.
          The method uses the canonical implementation that recursively calls itself.
          We'll assume that it is called only with small, nonnegative numbers.
          See https://en.wikipedia.org/w/index.php?title=Fibonacci_sequence#Definition
      """)
      .info("Put its tester code in the same package in the class `FibonacciTest`.");
}

void main() {}


