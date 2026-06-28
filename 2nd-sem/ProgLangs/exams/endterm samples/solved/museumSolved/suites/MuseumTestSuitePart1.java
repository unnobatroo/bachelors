import org.junit.platform.suite.api.*;
import org.junit.jupiter.api.*;

@SelectClasses({
	MuseumTestSuitePart1.StructuralTests.class,
	MuseumTestSuitePart1.FunctionalTests.class,
})
@Suite(failIfNoTests=false) public class MuseumTestSuitePart1 {
	@SelectClasses({
        RelicStructureTest.class,
        RelicTypeStructureTest.class,
    })
    @Suite(failIfNoTests=false) @Tag("structural") public static class StructuralTests {}

    @SelectClasses({
        museum.relic.RelicTest.class,
    })
    @Suite(failIfNoTests=false) @Tag("functional") public static class FunctionalTests {}
}
