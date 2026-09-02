import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@SelectClasses({
      CityPart1TestSuite.StructuralTests.class,
})
@Suite public class CityPart1TestSuite {
	@SelectClasses({
		CitizenStructureTest.class,

        ExpertiseStructureTest.class,
        PersonStructureTest.class,
  })
  @Suite public static class StructuralTests {}
}
