import static check.CheckThat.*;
import static check.CheckThat.Condition.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.junit.jupiter.api.MethodOrderer.*;
import check.*;

@TestMethodOrder(OrderAnnotation.class)
public class ResourceStructureTest {
    @BeforeAll
    public static void init() {
        CheckThat.theClass("spaceexploration.model.Resource", withInterface("spaceexploration.contract.Tagged"))
                 .thatIs(NOT_IMPLEMENTED, INSTANCE_LEVEL, VISIBLE_TO_ALL)
                 ;
    }

    @Test @DisabledIf(notApplicable) @Order(1_00)
    public void fieldPosition() {
        it.hasField("position: String")
          .thatIs(INSTANCE_LEVEL, MODIFIABLE, VISIBLE_TO_SUBCLASSES);
    }

    @Test @DisabledIf(notApplicable) @Order(1_01)
    public void fieldType() {
        it.hasField("type: Type")
          .thatIs(INSTANCE_LEVEL, MODIFIABLE, VISIBLE_TO_SUBCLASSES);
    }

    @Test @DisabledIf(notApplicable) @Order(1_02)
    public void fieldIsStable() {
        it.hasField("isStable: boolean")
          .thatIs(INSTANCE_LEVEL, MODIFIABLE, VISIBLE_TO_SUBCLASSES);
    }

    @Test @DisabledIf(notApplicable) @Order(1_03)
    public void fieldTagged() {
        it.hasField("tagged: boolean")
          .thatIs(INSTANCE_LEVEL, MODIFIABLE, VISIBLE_TO_SUBCLASSES);
    }

    @Test @DisabledIf(notApplicable) @Order(2_00)
    public void constructor() {
        it.hasConstructor(withParams("position: String", "type: Type"))
          .thatIs(VISIBLE_TO_ALL);
    }

    @Test @DisabledIf(notApplicable) @Order(3_00)
    public void methodExtendPosition01() {
        it.hasMethod("extendPosition", withParams("extension: String"))
          .thatIs(NOT_IMPLEMENTED, INSTANCE_LEVEL, VISIBLE_TO_ALL)
          .thatReturnsNothing();
    }

    @Test @DisabledIf(notApplicable) @Order(3_01)
    public void methodCollect02() {
        it.hasMethod("collect", withNoParams())
          .thatIs(NOT_IMPLEMENTED, INSTANCE_LEVEL, VISIBLE_TO_ALL)
          .thatReturns("String");
    }

    @Test @DisabledIf(notApplicable)
    public void text() {
        it.has(TEXTUAL_REPRESENTATION);
    }

    @Test @DisabledIf(notApplicable)
    public void eq() {
        it.has(EQUALITY_CHECK);
    }

    @Test @DisabledIf(notApplicable) @Order(3_05)
    public void methodGetPosition06() {
        it.hasMethod("getPosition", withNoParams())
          .thatIs(FULLY_IMPLEMENTED, INSTANCE_LEVEL, VISIBLE_TO_ALL)
          .thatReturns("String");
    }

    @Test @DisabledIf(notApplicable) @Order(3_06)
    public void methodGetType07() {
        it.hasMethod("getType", withNoParams())
          .thatIs(FULLY_IMPLEMENTED, INSTANCE_LEVEL, VISIBLE_TO_ALL)
          .thatReturns("Type");
    }

    @Test @DisabledIf(notApplicable) @Order(3_07)
    public void methodIsStable08() {
        it.hasMethod("isStable", withNoParams())
          .thatIs(FULLY_IMPLEMENTED, INSTANCE_LEVEL, VISIBLE_TO_ALL)
          .thatReturns("boolean");
    }

    @Test @DisabledIf(notApplicable) @Order(3_08)
    public void methodSetStable09() {
        it.hasMethod("setStable", withParams("stable: boolean"))
          .thatIs(FULLY_IMPLEMENTED, INSTANCE_LEVEL, VISIBLE_TO_ALL)
          .thatReturnsNothing();
    }

    @Test @DisabledIf(notApplicable) @Order(3_09)
    public void methodIsTagged10() {
        it.implementsMethod("isTagged");
    }

}

