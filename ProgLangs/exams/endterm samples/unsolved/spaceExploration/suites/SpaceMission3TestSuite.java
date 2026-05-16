import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import spaceexploration.operation.SpaceMissionTest;


@SelectClasses({
    SpaceMission3TestSuite.StructuralTests.class,
    SpaceMission3TestSuite.FunctionalTests.class,
})
@Suite public class SpaceMission3TestSuite {
    @SelectClasses({
        SpaceMissionStructureTest.class,
    })
    @Suite public static class StructuralTests {}

    @SelectClasses({
        SpaceMissionTest.class,
    })
    @Suite public static class FunctionalTests {}
}

