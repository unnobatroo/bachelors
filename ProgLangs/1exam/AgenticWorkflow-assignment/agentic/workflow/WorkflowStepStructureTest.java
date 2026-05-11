import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    // usedLang = Lang.EN; // uncomment to enforce the message language
    Use.theClass("agentic.workflow.WorkflowStep")
       .that(hasUsualModifiers());
}

@Test
public void fieldName() {
    it.hasField("name: String")
      .that(hasUsualModifiers())
      .thatHas(GETTER, SETTER)
      .info("Human-readable identifier of the workflow step.");
}

@Test
public void fieldPrompt() {
    it.hasField("prompt: String")
      .that(hasUsualModifiers())
      .thatHas(GETTER, SETTER)
      .info("User-facing prompt describing what the step should do.");
}

@Test
public void fieldSystemPrompt() {
    it.hasField("systemPrompt: String")
      .that(hasUsualModifiers())
      .thatHas(GETTER, SETTER)
      .info("System-level instruction associated with the step.");
}

@Test
public void fieldStructuredOutput() {
    it.hasField("structuredOutput: agentic.workflow.llm.StructuredOutput")
      .that(hasUsualModifiers())
      .thatHas(GETTER, SETTER)
      .info("Structured output description expected from this step.");
}

@Test
public void constructor() {
    it.hasConstructor(withArgsLikeAllFields())
      .thatIs(VISIBLE_TO_ALL)
      .thatThrows("IllegalArgumentException", "`name`, `prompt`, and `systemPrompt` must not be blank, and `structuredOutput` must not be `null`.")
      .info("Creates a workflow step with validated fields.");
}

@Test
public void methodExpectsStructuredOutput() {
    it.hasMethod("expectsStructuredOutput", withNoParams())
      .that(hasUsualModifiers())
      .thatReturns("boolean", """
          `true` if the step has at least one declared schema type, otherwise `false`.
          Since `StructuredOutput` requires at least one schema type, a valid step is expected to return `true`.
      """)
      .testWith(testCase("testExpectsStructuredOutput"), "Verify that a step with structured output reports that it expects one.");
}

@Test
public void methodSimulateResponse() {
    it.hasMethod("simulateResponse", withNoParams())
      .that(hasUsualModifiers())
      .thatReturns("String")
      .info("""
          Returns a simple simulated response based on the first declared schema type.
          INT maps to `"0"`, STRING to `"sample"`, BOOLEAN to `"true"`, LIST_INT to `"[1,2,3]"`, LIST_STRING to `"[\"a\",\"b\"]"`, and MAP_STRING_STRING to `"{\"key\":\"value\"}"`.
          test testSimulateResponseByPrimaryType Verify that the returned example matches the first schema type.
      """);
}

void main() {}


