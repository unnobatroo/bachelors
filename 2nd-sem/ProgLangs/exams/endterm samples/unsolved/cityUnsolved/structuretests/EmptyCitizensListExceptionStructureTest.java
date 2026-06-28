import static check.CheckThat.Condition.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.*;
import check.*;

@TestMethodOrder(OrderAnnotation.class)
public class EmptyCitizensListExceptionStructureTest {
    @BeforeAll
    public static void init() {
        CheckThat.theUncheckedException("city.exception.EmptyCitizensListException")
                 .thatIs(FULLY_IMPLEMENTED, INSTANCE_LEVEL, VISIBLE_TO_ALL)
                 ;
    }

}

