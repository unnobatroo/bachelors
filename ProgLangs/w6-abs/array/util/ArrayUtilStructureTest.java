package array.util;
import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    Use.theClass("array.util.ArrayUtil")
       .that(hasUsualModifiers());
}

@Test
public void methodMax() {
    it.hasMethod("max", withParams("array: array of int"))
      .thatIs(USABLE_WITHOUT_INSTANCE, FULLY_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_ALL)
      .thatReturns("int");
}

@Test
public void methodMax2() {
    it.hasMethod("max2", withParams("array: array of int"))
      .thatIs(USABLE_WITHOUT_INSTANCE, FULLY_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_ALL)
      .thatReturns("int");
}

@Test
public void methodMax3() {
    it.hasMethod("max3", withParams("array: array of int"))
      .thatIs(USABLE_WITHOUT_INSTANCE, FULLY_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_ALL)
      .thatReturns("int");
}

@Test
public void methodMax4() {
    it.hasMethod("max4", withParams("array: array of int"))
      .thatIs(USABLE_WITHOUT_INSTANCE, FULLY_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_ALL)
      .thatReturns("int");
}

@Test
public void methodMinMax() {
    it.hasMethod("minMax", withParams("array: array of int"))
      .thatIs(USABLE_WITHOUT_INSTANCE, FULLY_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_ALL)
      .thatReturns("array of int");
}

void main() {}


