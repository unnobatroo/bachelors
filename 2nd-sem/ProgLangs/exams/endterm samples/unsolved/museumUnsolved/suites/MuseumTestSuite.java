import org.junit.platform.suite.api.*;

@SelectClasses({
	MuseumTestSuitePart1.class,
	MuseumTestSuitePart2.class,
	MuseumTestSuitePart3.class,
})
@Suite(failIfNoTests=false) public class MuseumTestSuite {

}
