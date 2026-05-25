import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.*;
import check.*;

@TestMethodOrder(OrderAnnotation.class)
public class ExpertiseStructureTest {
    @Test
    public void elems() {
        CheckThat.theEnum("city.utils.Expertise")
        		 .hasEnumElements("STUDENT", "INTERMEDIATE", "EXPERT", "MASTER");
                 ;
    }
}
