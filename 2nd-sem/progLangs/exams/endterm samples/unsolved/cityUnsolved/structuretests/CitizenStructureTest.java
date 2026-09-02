import static check.CheckThat.*;
import static check.CheckThat.Condition.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.junit.jupiter.api.MethodOrderer.*;
import check.*;

@TestMethodOrder(OrderAnnotation.class)
public class CitizenStructureTest {
    @BeforeAll
    public static void init() {
        CheckThat.theClass("city.people.Citizen", withInterface("city.utils.Person"))
                 .thatIs(NOT_IMPLEMENTED, INSTANCE_LEVEL, VISIBLE_TO_ALL)
                 ;
    }

    @Test @DisabledIf(notApplicable) @Order(1_00)
    public void fieldName() {
        it.hasField("name: String")
          .thatIs(INSTANCE_LEVEL, NOT_MODIFIABLE, VISIBLE_TO_SUBCLASSES);
    }

    @Test @DisabledIf(notApplicable) @Order(1_01)
    public void fieldExpertise() {
        it.hasField("expertise: city.utils.Expertise")
          .thatIs(INSTANCE_LEVEL, NOT_MODIFIABLE, VISIBLE_TO_SUBCLASSES);
    }

    @Test @DisabledIf(notApplicable) @Order(1_02)
    public void fieldKnowledge() {
        it.hasField("knowledge: int")
          .thatIs(INSTANCE_LEVEL, MODIFIABLE, VISIBLE_TO_SUBCLASSES);
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
    public void methodGetKnowledge02() {
        it.hasMethod("getKnowledge", withNoParams())
          .thatIs(FULLY_IMPLEMENTED, INSTANCE_LEVEL, VISIBLE_TO_ALL)
          .thatReturns("int");
    }

    @Test @DisabledIf(notApplicable) @Order(3_02)
    public void methodGetExpertiseModifier03() {
        it.hasMethod("getExpertiseModifier", withNoParams())
          .thatIs(FULLY_IMPLEMENTED, INSTANCE_LEVEL, VISIBLE_TO_SUBCLASSES)
          .thatReturns("int");
    }

}

