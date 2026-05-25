import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@SelectClasses({
    SpaceMission1TestSuite.class,
    SpaceMission2TestSuite.class,
    SpaceMission3TestSuite.class,
})
@Suite public class SpaceMissionTestSuite {
}

