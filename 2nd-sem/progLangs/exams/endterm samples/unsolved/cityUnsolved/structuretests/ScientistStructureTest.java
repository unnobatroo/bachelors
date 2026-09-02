import static check.CheckThat.*;
import static check.CheckThat.Condition.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.junit.jupiter.api.MethodOrderer.*;
import check.*;

@TestMethodOrder(OrderAnnotation.class)
public class ScientistStructureTest {
    @BeforeAll
    public static void init() {
        CheckThat.theClass("city.people.Scientist", withParent("city.people.Citizen"))
                 .thatIs(FULLY_IMPLEMENTED, INSTANCE_LEVEL, VISIBLE_TO_ALL)
                 ;
    }

    @Test @DisabledIf(notApplicable) @Order(1_00)
    public void fieldInventionsList() {
        it.hasField("inventions: List of String")
          .thatIs(INSTANCE_LEVEL, MODIFIABLE, VISIBLE_TO_NONE);
    }

    @Test @DisabledIf(notApplicable) @Order(2_00)
    public void constructor() {
        it.hasConstructor(withParams("name: String", "expertise: city.utils.Expertise"))
          .thatIs(VISIBLE_TO_ALL);
    }

    @Test @DisabledIf(notApplicable) @Order(3_00)
    public void methodWork01() {
        it.implementsMethod("work");
    }

    @Test @DisabledIf(notApplicable) @Order(3_01)
    public void methodGetInventionCount02() {
        it.hasMethod("getInventionCount", withNoParams())
          .thatIs(FULLY_IMPLEMENTED, INSTANCE_LEVEL, VISIBLE_TO_ALL)
          .thatReturns("int");
    }

    @Test @DisabledIf(notApplicable) @Order(3_02)
    public void methodGenerateInvention03() {
        it.hasMethod("generateInvention", withNoParams())
          .thatIs(FULLY_IMPLEMENTED, INSTANCE_LEVEL, VISIBLE_TO_NONE)
          .thatReturns("String");
    }

    @Test @DisabledIf(notApplicable)
    public void text() {
        it.has(TEXTUAL_REPRESENTATION);
    }

}

