import static check.CheckThat.*;
import static check.CheckThat.Condition.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.junit.jupiter.api.MethodOrderer.*;
import check.*;

@TestMethodOrder(OrderAnnotation.class)
public class ArtistStructureTest {
    @BeforeAll
    public static void init() {
        CheckThat.theClass("city.people.Artist", withParent("city.people.Citizen"))
                 .thatIs(FULLY_IMPLEMENTED, INSTANCE_LEVEL, VISIBLE_TO_ALL)
                 ;
    }

    @Test @DisabledIf(notApplicable) @Order(1_00)
    public void fieldWORK_TO_FINISH_ART() {
        it.hasField("WORK_TO_FINISH_ART: int")
          .thatIs(USABLE_WITHOUT_INSTANCE, NOT_MODIFIABLE, VISIBLE_TO_NONE);
    }

    @Test @DisabledIf(notApplicable) @Order(1_01)
    public void fieldPopularity() {
        it.hasField("popularity: int")
          .thatIs(INSTANCE_LEVEL, MODIFIABLE, VISIBLE_TO_NONE);
    }

    @Test @DisabledIf(notApplicable) @Order(1_02)
    public void fieldArtProgress() {
        it.hasField("artProgress: int")
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
    public void methodIsArtFinished02() {
        it.hasMethod("isArtFinished", withNoParams())
          .thatIs(FULLY_IMPLEMENTED, INSTANCE_LEVEL, VISIBLE_TO_NONE)
          .thatReturns("boolean");
    }

    @Test @DisabledIf(notApplicable) @Order(3_02)
    public void methodGetPopularity03() {
        it.hasMethod("getPopularity", withNoParams())
          .thatIs(FULLY_IMPLEMENTED, INSTANCE_LEVEL, VISIBLE_TO_ALL)
          .thatReturns("int");
    }

    @Test @DisabledIf(notApplicable)
    public void text() {
        it.has(TEXTUAL_REPRESENTATION);
    }

}

