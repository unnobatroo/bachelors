import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import city.people.ArtistTest;
import city.people.ChefTest;
import city.people.ScientistTest;

@SelectClasses({
	CityPart2TestSuite.StructuralTests.class,
    CityPart2TestSuite.FunctionalTests.class,
})
@Suite public class CityPart2TestSuite {
	@SelectClasses({
		ArtistStructureTest.class,
        ChefStructureTest.class,
        ScientistStructureTest.class,
  })
  @Suite public static class StructuralTests {}

  @SelectClasses({
	  	ArtistTest.class,
        ChefTest.class,
        ScientistTest.class,
  })
  @Suite public static class FunctionalTests {}
}
