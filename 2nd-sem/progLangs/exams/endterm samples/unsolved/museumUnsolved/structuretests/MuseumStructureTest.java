import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    // usedLang = Lang.EN; // uncomment to enforce the message language
    Use.theClass("museum.Museum")
       .that(hasUsualModifiers());
}

@Test
public void fieldTICKET_PRICE() {
    it.hasField("TICKET_PRICE: int")
      .thatIs(USABLE_WITHOUT_INSTANCE, FULLY_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_NONE)
      .thatHasNo(GETTER, SETTER)
      .info("Its value should be 50.");
}

@Test
public void fieldVisitingTourists() {
    it.hasField("visitingTourists: List of museum.visitor.Tourist")
      .that(hasUsualModifiers())
      .thatHasNo(GETTER, SETTER);
}

@Test
public void fieldMuseumData() {
    it.hasField("museumData: Map of String to Integer")
      .that(hasUsualModifiers())
      .thatHasNo(GETTER, SETTER);
}

@Test
public void constructor() {
    it.hasConstructor(withNoParams())
      .that(hasUsualModifiers())
      .info("The `visitingTourists` should initially be empty.")
      .info("Initializes the `museumData` field,")
      .info("with the keys 'income' and 'popularity', both set to 0.");
}

@Test
public void methodCalculateMuseumData() {
    it.hasMethod("calculateMuseumData", withNoParams())
      .thatIs(INSTANCE_LEVEL, FULLY_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_NONE)
      .thatReturnsNothing()
      .info("Calculates and stores in the `museumData` the new 'income' and 'popularity' values as follows:")
      .info("The 'income' should be the count of the tourists multiplied by the `TICKET_PRICE`.")
      .info("The 'popularity' should be the count of all the relics visited by the tourists.")
      .info("Relics that occur multiple times are counted multiple times.");
}

@Test
public void methodAddVisitingTourist() {
    it.hasMethod("addVisitingTourist", withParams("tourist: museum.visitor.Tourist"))
      .that(hasUsualModifiers())
      .thatReturnsNothing()
      .info("Adds `tourist` to the appropriate field.")
      .info("Then recalculate data of the museum using the `calculateMuseumData` method.");
}

@Test
public void methodAllTouristsVisitRelic() {
    it.hasMethod("allTouristsVisitRelic", withParams("relic: museum.relic.Relic"))
      .that(hasUsualModifiers())
      .thatReturnsNothing()
      .info("Those tourists should visit the relic received as a parameter whose favourite relic type matches the `relic`'s type.")
      .info("Then recalculate data of the museum using the `calculateMuseumData` method.");
}

@Test
public void methodGetMuseumIncome() {
    it.hasMethod("getMuseumIncome", withNoParams())
      .that(hasUsualModifiers())
      .thatReturns("int", "Returns the value in the `museumData` field using the appropriate key.")
      .testWith(testCase("museumIncomeTest"), """
          The testing should be as follows:
          1. Create an instance of a `Museum`, then create an instance of a `Tourist` with parameters of your choice.
          2. Check whether the museum's income is 0.
          3. Add your created tourist to the museum.
          4. Check the museum's income again.
      """);
}

@Test
public void methodGetMuseumPopularity() {
    it.hasMethod("getMuseumPopularity", withNoParams())
      .that(hasUsualModifiers())
      .thatReturns("int", "Returns the value in the `museumData` field using the appropriate key.")
      .testWith(testCase("museumPopularityTest"), """
          The testing should be as follows:
          1. Create an instance of a `Museum`, then create an instance of a `Tourist` with parameters of your choice.
          After that, create two `Relic` instances: one whose type matches the created tourist's favourite relic type
          and one whose type does not.
          2. Check whether the museum's popularity is 0.
          3. Add your created tourist to the museum.
          4. Check the museum's popularity again.
          5. Now use the appropriate method with one of the created relics.
          6. Check the museum's popularity once again.
          7. Now use the appropriate method with the other created relics.
          8. And check the museum's popularity one last time.
      """);
}

void main() {}


