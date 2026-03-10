import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    Use.theClass("math.operation.safe.Increment")
       .that(hasUsualModifiers());
}

@Test
public void methodIncrement() {
    it.hasMethod("increment", withParams("num: int"))
      .thatIs(USABLE_WITHOUT_INSTANCE, FULLY_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_ALL)
      .thatReturns("int");
}

void main() {}


