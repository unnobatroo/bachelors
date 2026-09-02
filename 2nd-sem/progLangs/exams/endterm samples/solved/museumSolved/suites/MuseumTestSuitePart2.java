import org.junit.platform.suite.api.*;
import org.junit.jupiter.api.*;

@SelectClasses({
	MuseumTestSuitePart2.StructuralTests.class,
	MuseumTestSuitePart2.FunctionalTests.class,
})
@Suite(failIfNoTests=false) public class MuseumTestSuitePart2 {
	@SelectClasses({
        TouristStructureTest.class,
        VisitingExceptionStructureTest.class,
        VisitorStructureTest.class,
    })
    @Suite(failIfNoTests=false) @Tag("structural") public static class StructuralTests {}

    @SelectClasses({
        museum.visitor.TouristTest.class
    })
    @Suite(failIfNoTests=false) @Tag("functional") public static class FunctionalTests {}
}