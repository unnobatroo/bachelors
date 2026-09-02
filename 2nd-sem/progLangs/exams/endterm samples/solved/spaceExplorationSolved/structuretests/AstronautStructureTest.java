import static check.CheckThat.*;
import static check.CheckThat.Condition.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.junit.jupiter.api.MethodOrderer.*;
import check.*;

@TestMethodOrder(OrderAnnotation.class)
public class AstronautStructureTest {
    @BeforeAll
    public static void init() {
        CheckThat.theClass("spaceexploration.entity.Astronaut")
                 .thatIs(FULLY_IMPLEMENTED, INSTANCE_LEVEL, VISIBLE_TO_ALL)
                 ;
    }

    @Test @DisabledIf(notApplicable) @Order(1_00)
    public void fieldUnitId() {
        it.hasField("unitId: String")
          .thatIs(INSTANCE_LEVEL, MODIFIABLE, VISIBLE_TO_NONE);
    }

    @Test @DisabledIf(notApplicable) @Order(1_01)
    public void fieldSpecialityType() {
        it.hasField("specialityType: spaceexploration.model.Type")
          .thatIs(INSTANCE_LEVEL, MODIFIABLE, VISIBLE_TO_NONE);
    }

    @Test @DisabledIf(notApplicable) @Order(1_02)
    public void fieldResources() {
        it.hasField("resources: List of spaceexploration.model.Resource")
          .thatIs(INSTANCE_LEVEL, MODIFIABLE, VISIBLE_TO_NONE);
    }

    @Test @DisabledIf(notApplicable) @Order(2_00)
    public void constructor01() {
        it.hasConstructor(withParams("unitId: String", "specialityType: spaceexploration.model.Type"))
          .thatIs(VISIBLE_TO_ALL);
    }

    @Test @DisabledIf(notApplicable) @Order(2_01)
    public void constructor02() {
        it.hasConstructor(withNoParams())
          .thatIs(VISIBLE_TO_ALL);
    }

    @Test @DisabledIf(notApplicable) @Order(3_00)
    public void methodGetSpecialityType01() {
        it.hasMethod("getSpecialityType", withNoParams())
          .thatIs(FULLY_IMPLEMENTED, INSTANCE_LEVEL, VISIBLE_TO_ALL)
          .thatReturns("spaceexploration.model.Type");
    }

    @Test @DisabledIf(notApplicable) @Order(3_01)
    public void methodGetResources02() {
        it.hasMethod("getResources", withNoParams())
          .thatIs(FULLY_IMPLEMENTED, INSTANCE_LEVEL, VISIBLE_TO_ALL)
          .thatReturns("List of spaceexploration.model.Resource");
    }

    @Test @DisabledIf(notApplicable) @Order(3_02)
    public void methodTagResources03() {
        it.hasMethod("tagResources", withParams("resourceList: List of spaceexploration.model.Resource"))
          .thatIs(FULLY_IMPLEMENTED, INSTANCE_LEVEL, VISIBLE_TO_ALL)
          .thatReturnsNothing();
    }

    @Test @DisabledIf(notApplicable) @Order(3_03)
    public void methodTryToCollect04() {
        it.hasMethod("tryToCollect", withParams("resource: spaceexploration.model.Resource"))
          .thatIs(FULLY_IMPLEMENTED, INSTANCE_LEVEL, VISIBLE_TO_ALL)
          .thatReturns("boolean");
    }

    @Test @DisabledIf(notApplicable) @Order(3_04)
    public void methodForceInsert05() {
        it.hasMethod("forceInsert", withParams("resource: spaceexploration.model.Resource"))
          .thatIs(FULLY_IMPLEMENTED, INSTANCE_LEVEL, VISIBLE_TO_ALL)
          .thatReturnsNothing();
    }

}

