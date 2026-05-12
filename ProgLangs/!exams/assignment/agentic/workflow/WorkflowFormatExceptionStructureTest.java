import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    // usedLang = Lang.EN; // uncomment to enforce the message language
    Use.theCheckedException("agentic.workflow.WorkflowFormatException")
       .that(hasUsualModifiers())
       .info("Thrown when a workflow description file or workflow content has an invalid format.")
       .info("This checked exception signals that the caller should handle malformed workflow input explicitly.");
}

@Test
public void constructor01() {
    it.hasConstructor(withParams("message: String"))
      .thatIs(VISIBLE_TO_ALL)
      .info("Creates the exception with an explanatory error message.");
}

@Test
public void constructor02() {
    it.hasConstructor(withParams("message: String", "cause: Throwable"))
      .thatIs(VISIBLE_TO_ALL)
      .info("Creates the exception with an explanatory error message and the original cause.")
      .info("Useful when workflow parsing fails because of another lower-level exception.");
}

void main() {}


