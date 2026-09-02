import static check.CheckThat.*;
import static check.CheckThat.Condition.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.junit.jupiter.api.MethodOrderer.*;
import check.*;

@TestMethodOrder(OrderAnnotation.class)
public class OreStructureTest {
    @BeforeAll
    public static void init() {
        CheckThat.theClass("spaceexploration.model.Ore", withParent("spaceexploration.model.Resource"))
                 .thatIs(FULLY_IMPLEMENTED, INSTANCE_LEVEL, VISIBLE_TO_ALL)
                 ;
    }

    @Test @DisabledIf(notApplicable) @Order(2_00)
    public void constructor() {
        it.hasConstructor(withParams("position: String", "type: Type"))
          .thatIs(VISIBLE_TO_ALL);
    }

    @Test @DisabledIf(notApplicable) @Order(3_00)
    public void methodExtendPosition01() {
        it.implementsMethod("extendPosition");
    }

    @Test @DisabledIf(notApplicable) @Order(3_01)
    public void methodCollect02() {
        it.implementsMethod("collect");
    }

    @Test @DisabledIf(notApplicable) @Order(3_02)
    public void methodTag03() {
        it.implementsMethod("tag");
    }

}

