import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import spaceexploration.model.ResourceTest;


@SelectClasses({
    SpaceMission1TestSuite.StructuralTests.class,
    SpaceMission1TestSuite.FunctionalTests.class,
})
@Suite public class SpaceMission1TestSuite {
    @SelectClasses({
        TaggedStructureTest.class,
        ResourceStructureTest.class,

        OreStructureTest.class,
        DebrisStructureTest.class,
    })
    @Suite public static class StructuralTests {}

    @SelectClasses({
        ResourceTest.class,
    })
    @Suite public static class FunctionalTests {}
}

