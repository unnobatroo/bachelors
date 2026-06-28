import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import city.city.CityTest;

@SelectClasses({
	CityPart3TestSuite.StructuralTests.class,
    CityPart3TestSuite.FunctionalTests.class,
})
@Suite public class CityPart3TestSuite {
	@SelectClasses({
        CityStructureTest.class,

        EmptyCitizensListExceptionStructureTest.class,
  })
  @Suite public static class StructuralTests {}

  @SelectClasses({
        CityTest.class,
  })
  @Suite public static class FunctionalTests {}
}
