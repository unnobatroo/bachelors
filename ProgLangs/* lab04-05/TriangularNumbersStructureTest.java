import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    Use.theClass("famous.sequence.TriangularNumbers")
       .that(hasUsualModifiers());
}

// ./check.cmd TriangularNumbersStructureTest.java TriangularNumbersStructureTest

@Test
public void methodGetTriangularNumber() {
    it.hasMethod("getTriangularNumber", withParams("n: int"))
      .thatIs(USABLE_WITHOUT_INSTANCE, FULLY_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_ALL)
      .thatReturns("int", "The `n`th triangular number: 1+2+...+n.")
      .info("@link https://en.wikipedia.org/wiki/Triangular_number")
      .testWith(testCase("singleValues"), "0", "1", "-1", "-100")
      .testWith(testCase("Gauss"), """
          The number little Gauss is anecdotally famous for.
          @see https://en.wikipedia.org/wiki/Carl_Friedrich_Gauss#Anecdotes
      """);
}

@Test
public void methodGetTriangularNumberAlternative() {
    it.hasMethod("getTriangularNumberAlternative", withParams("n: int"))
      .thatIs(USABLE_WITHOUT_INSTANCE, FULLY_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_ALL)
      .thatReturns("int")
      .info("In this implementation, use the simple mathematical formula.")
      .info("For negative values, return zero.")
      .testWith(testCase("singleValuesAlt"), "input as before", "Gauss's famous number, too");
}

void main() {}


