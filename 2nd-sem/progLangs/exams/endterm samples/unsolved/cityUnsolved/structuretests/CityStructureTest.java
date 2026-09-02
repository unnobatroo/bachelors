import static check.CheckThat.*;
import static check.CheckThat.Condition.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.junit.jupiter.api.MethodOrderer.*;
import check.*;

@TestMethodOrder(OrderAnnotation.class)
public class CityStructureTest {
    @BeforeAll
    public static void init() {
        CheckThat.theClass("city.city.City")
                 .thatIs(FULLY_IMPLEMENTED, INSTANCE_LEVEL, VISIBLE_TO_ALL)
                 ;
    }

    @Test @DisabledIf(notApplicable) @Order(1_00)
    public void fieldCitizens() {
        it.hasField("citizens: List of city.people.Citizen")
          .thatIs(INSTANCE_LEVEL, MODIFIABLE, VISIBLE_TO_NONE);
    }

    @Test @DisabledIf(notApplicable) @Order(1_01)
    public void fieldCityResources() {
        it.hasField("cityResources: Map of String to Integer")
          .thatIs(INSTANCE_LEVEL, MODIFIABLE, VISIBLE_TO_ALL);
    }

    @Test @DisabledIf(notApplicable) @Order(2_00)
    public void constructor() {
        it.hasConstructor(withNoParams())
          .thatIs(VISIBLE_TO_ALL);
    }

    @Test @DisabledIf(notApplicable) @Order(3_00)
    public void methodWorkAll01() {
        it.hasMethod("workAll", withNoParams())
          .thatIs(FULLY_IMPLEMENTED, INSTANCE_LEVEL, VISIBLE_TO_ALL)
          .thatReturnsNothing();
    }

    @Test @DisabledIf(notApplicable) @Order(3_01)
    public void methodAddCitizen02() {
        it.hasMethod("addCitizen", withParams("citizen: city.people.Citizen"))
          .thatIs(FULLY_IMPLEMENTED, INSTANCE_LEVEL, VISIBLE_TO_ALL)
          .thatReturnsNothing();
    }

    @Test @DisabledIf(notApplicable) @Order(3_02)
    public void methodGetAllResources03() {
        it.hasMethod("getAllResources", withNoParams())
          .thatIs(FULLY_IMPLEMENTED, INSTANCE_LEVEL, VISIBLE_TO_NONE)
          .thatReturnsNothing();
    }

    @Test @DisabledIf(notApplicable)
    public void eq() {
        it.has(EQUALITY_CHECK);
    }

}

