import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@SelectClasses({
      CityPart1TestSuite.class,
      CityPart2TestSuite.class,
      CityPart3TestSuite.class,
})
@Suite public class CityTestSuite {}

