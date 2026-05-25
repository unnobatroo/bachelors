import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    // usedLang = Lang.EN; // uncomment to enforce the message language
    Use.theUncheckedException("museum.utils.VisitingException")
       .that(hasUsualModifiers());
}

@Test
public void constructor() {
    it.hasConstructor(withNoParams())
      .that(hasUsualModifiers());
}

void main() {}


