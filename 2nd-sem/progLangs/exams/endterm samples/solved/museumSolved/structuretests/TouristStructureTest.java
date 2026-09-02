import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    // usedLang = Lang.EN; // uncomment to enforce the message language
    Use.theClass("museum.visitor.Tourist", withInterface("museum.visitor.Visitor"))
       .that(hasUsualModifiers());
}

@Test
public void fieldTouristName() {
    it.hasField("touristName: String")
      .that(hasUsualModifiers())
      .thatHasNo(GETTER, SETTER);
}

@Test
public void fieldFavouriteRelicType() {
    it.hasField("favouriteRelicType: museum.utils.RelicType")
      .that(hasUsualModifiers())
      .thatHas(GETTER)
      .thatHasNo(SETTER);
}

@Test
public void fieldVisitedRelics() {
    it.hasField("visitedRelics: List of museum.relic.Relic")
      .that(hasUsualModifiers())
      .thatHasNo(GETTER, SETTER);
}

@Test
public void constructor() {
    it.hasConstructor(withArgsLikeFields("touristName", "favouriteRelicType"))
      .that(hasUsualModifiers())
      .info("`visitedRelics` should initially be empty.");
}

@Test
public void methodGetVisitedRelicsCount() {
    it.hasMethod("getVisitedRelicsCount", withNoParams())
      .that(hasUsualModifiers())
      .thatReturns("int", "The number of relics the tourist has visited.");
}

@Test
public void methodVisitRelic() {
    it.hasMethod("visitRelic", withParams("relic: museum.relic.Relic"))
      .that(hasUsualModifiers())
      .thatReturnsNothing()
      .thatThrows("museum.utils.VisitingException", "If the `relic`'s type is not equal to the favourite type or has already been visited by the tourist.")
      .info("Stores the relic.")
      .testWith(testCase("visitRelicTest"), "A `Relic` is stored after using the `visitRelic` method.")
      .testWith(testCase("visitRelicInvalidRelicTypeTest"), "The proper exception is thrown if tried to visit a relic that's not their favourite type of relic.")
      .testWith(testCase("visitRelicDuplicateVisitTest"), "The proper exception is thrown if tried to visit a relic they've already visited.");
}

@Test
public void text() {
    it.has(TEXTUAL_REPRESENTATION)
      .info("The tourist's name and all the names of the visited relics, separated by commas.")
      .info("If no relics have been visited, then the proper example text should be returned.")
      .info("""
          Example texts if the tourist's name is 'Example':
          No relics have been visited    --> Tourist Example hasn't visited any relics yet.
          One relic has been visited  --> Tourist Example visited the following relic(s): Example Relic
          More than one relic has been visited --> Tourist Example visited the following relic(s): Example Relic, Example Relic2
      """)
      .testWith(testCase("textualRepresentationTest"), "The textual representation is correct on the given examples.");
}

void main() {}


