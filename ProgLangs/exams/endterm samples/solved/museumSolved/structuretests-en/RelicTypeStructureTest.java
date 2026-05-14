import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@Test
public void elements() {
    Use.theEnum("museum.utils.RelicType")
       .ofEnumElements("PAINTING", "SCULPTURE", "JEWELRY", "ARTIFACT")
       .that(hasUsualModifiers());
}

void main() {}


