import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    Use.theClass("math.operation.textual.Adder")
       .that(hasUsualModifiers());
}

@Test
public void defaultConstructor() {
    it.hasDefaultConstructor();
}

@Test
public void methodAddAsText() {
    it.hasMethod("addAsText", withParams("opTxt1: String", "opTxt2: String"))
      .that(hasUsualModifiers())
      .thatReturns("String");
}

void main() {}


