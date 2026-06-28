import static check.CheckThat.*;
import static check.CheckThat.Condition.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.junit.jupiter.api.MethodOrderer.*;
import check.*;

@TestMethodOrder(OrderAnnotation.class)
public class InvalidResourceExceptionStructureTest {
    @BeforeAll
    public static void init() {
        CheckThat.theCheckedException("spaceexploration.exception.InvalidResourceException")
                 .thatIs(FULLY_IMPLEMENTED, INSTANCE_LEVEL, VISIBLE_TO_ALL)
                 ;
    }

    @Test @DisabledIf(notApplicable) @Order(2_00)
    public void constructor() {
        it.hasConstructor(withParams("message: String"))
          .thatIs(VISIBLE_TO_ALL);
    }

}

