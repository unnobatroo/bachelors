import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    // usedLang = Lang.EN; // uncomment to enforce the message language
    Use.theClass("museum.relic.Relic")
       .that(hasUsualModifiers());
}

@Test
public void fieldRelicName() {
    it.hasField("relicName: String")
      .that(hasUsualModifiers())
      .thatHas(GETTER)
      .thatHasNo(SETTER);
}

@Test
public void fieldRelicType() {
    it.hasField("relicType: museum.utils.RelicType")
      .that(hasUsualModifiers())
      .thatHas(GETTER)
      .thatHasNo(SETTER);
}

@Test
public void constructor() {
    it.hasConstructor(withArgsLikeAllFields())
      .that(hasUsualModifiers())
      .testWith(testCase("relicConstructorTest"), "The class's constructor initializes its fields properly.");
}

@Test
public void eq() {
    it.has(EQUALITY_CHECK)
      .testWith(testCase("relicEqualityCheckTest"), "is a ParameterizedTest that should test the equality check of the `Relic` class.");
}

void main() {}


