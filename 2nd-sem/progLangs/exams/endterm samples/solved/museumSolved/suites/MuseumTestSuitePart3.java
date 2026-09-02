import org.junit.platform.suite.api.*;
import org.junit.jupiter.api.*;

@SelectClasses({
	MuseumTestSuitePart3.StructuralTests.class,
	MuseumTestSuitePart3.FunctionalTests.class,
})
@Suite(failIfNoTests=false) public class MuseumTestSuitePart3 {
	@SelectClasses({
        MuseumStructureTest.class,
    })
    @Suite(failIfNoTests=false) @Tag("structural") public static class StructuralTests {}

    @SelectClasses({
        museum.MuseumTest.class,
    })
    @Suite(failIfNoTests=false) @Tag("functional") public static class FunctionalTests {}
}