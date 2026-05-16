import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    // usedLang = Lang.EN; // uncomment to enforce the message language
    Use.theInterface("museum.visitor.Visitor")
       .thatIs(INSTANCE_LEVEL, NOT_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_ALL);
}

@Test
public void methodVisitRelic() {
    it.hasMethod("visitRelic", withParams("relic: museum.relic.Relic"))
      .that(hasUsualModifiers())
      .thatReturnsNothing()
      .thatThrows("museum.utils.VisitingException");
}

void main() {}


