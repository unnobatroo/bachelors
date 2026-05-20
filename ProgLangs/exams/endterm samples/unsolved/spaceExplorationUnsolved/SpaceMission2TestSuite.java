import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;


@SelectClasses({
    SpaceMission2TestSuite.StructuralTests.class,
})
@Suite public class SpaceMission2TestSuite {
    @SelectClasses({
        AstronautStructureTest.class,
        RecyclerStructureTest.class,
        InvalidResourceExceptionStructureTest.class,
    })
    @Suite public static class StructuralTests {}
}

