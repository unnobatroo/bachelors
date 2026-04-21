import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    // usedLang = Lang.EN; // uncomment to enforce the message language
    Use.theClass("agentic.workflow.Agent")
       .that(hasUsualModifiers())
       .info("""
           Represents an agent consisting of an ordered list of workflow steps.
           The agent can be built manually or loaded from a text file.
       """);
}

@Test
public void fieldName() {
    it.hasField("name: String")
      .that(hasUsualModifiers())
      .thatHas(GETTER, SETTER)
      .info("The name of the agent.");
}

@Test
public void fieldSteps() {
    it.hasField("steps: List of WorkflowStep")
      .thatIs(INSTANCE_LEVEL, FULLY_IMPLEMENTED, NOT_MODIFIABLE, VISIBLE_TO_NONE)
      .thatHas(GETTER)
      .thatHasNo(SETTER)
      .info("The ordered list of workflow steps belonging to the agent.")
      .info("The internal list must remain encapsulated.");
}

@Test
public void constructor() {
    it.hasConstructor(withArgsLikeFields("name"))
      .thatIs(VISIBLE_TO_ALL)
      .thatThrows("IllegalArgumentException", "the name must not be `null`, empty, or blank.")
      .info("Creates an agent with the given validated name and initially no steps.");
}

@Test
public void methodAddStep() {
    it.hasMethod("addStep", withParams("step: WorkflowStep"))
      .that(hasUsualModifiers())
      .thatReturnsNothing()
      .thatThrows("IllegalArgumentException", "the step must not be `null`, and another step with the same name must not already exist.")
      .info("""
          Adds a new step to the end of the workflow.
          Step names must be unique within one agent.
      """)
      .testWith(testCase("testStepCount"), "Verify that adding a step increases the number of stored steps.")
      .testWith(testCase("testAddDuplicateStepRejected"), "Verify that duplicate step names are rejected.");
}

@Test
public void methodGetStepCount() {
    it.hasMethod("getStepCount", withNoParams())
      .that(hasUsualModifiers())
      .thatReturns("int", "The number of workflow steps currently stored in the agent.")
      .testWith(testCase("testStepCount"), "Verify that the step count matches the number of added steps.");
}

@Test
public void methodFindStepByName() {
    it.hasMethod("findStepByName", withParams("stepName: String"))
      .that(hasUsualModifiers())
      .thatReturns("WorkflowStep", "The matching step, or `null` if no such step exists.")
      .thatThrows("IllegalArgumentException", "the step name must not be `null`, empty, or blank.")
      .info("""
          Searches for a step by name.
          The comparison uses the trimmed search text and exact string equality.
      """)
      .testWith(testCase("findStepByName"), "Verify that an existing step can be found by name.")
      .testWith(testCase("findStepByNameMissing"), "Verify that `null` is returned when no such step exists.");
}

@Test
public void methodRun() {
    it.hasMethod("run", withNoParams())
      .that(hasUsualModifiers())
      .thatReturnsNothing()
      .info("Simulates running the full agent by printing each step name and a sample response.")
      .info("The steps are processed in insertion order.");
}

@Test
public void methodLoadAgent() {
    it.hasMethod("loadAgent", withParams("filename: String"))
      .thatIs(USABLE_WITHOUT_INSTANCE, FULLY_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_ALL)
      .thatReturns("Agent", "The loaded agent.")
      .thatThrows("IllegalArgumentException", "the filename must not be `null`, empty, or blank.")
      .thatThrows("WorkflowFormatException", "if the file content is malformed.")
      .thatThrows("IOException", "if the file cannot be read.")
      .info("""
          Loads an agent from a workflow description text file.
          The first non-empty line must be `AGENT: ...`.
          Each step must begin with `STEP` and end with `ENDSTEP`.
          Duplicate step names are not allowed.
      """)
      .testWith(testCase("testLoadAgentSuccess"), "{@link AgentTest#testLoadAgentSuccess()} Verify that a valid workflow file is loaded successfully.")
      .testWith(testCase("testLoadAgentRejectsMissingHeader"), "Verify that a missing AGENT header causes a format exception.")
      .testWith(testCase("testLoadAgentRejectsDuplicateStepNames"), "Verify that duplicate step names in the file are rejected.");
}

@Test
public void methodParseStep() {
    it.hasMethod("parseStep", withParams("reader: BufferedReader"))
      .thatIs(USABLE_WITHOUT_INSTANCE, FULLY_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_NONE)
      .thatReturns("WorkflowStep", "The parsed workflow step.")
      .thatThrows("IOException", "if reading from the file fails.")
      .thatThrows("WorkflowFormatException", "if the step content is malformed or incomplete.")
      .info("""
          Parses one step body from the reader until `ENDSTEP` is reached.
          The required properties are `name`, `prompt`, `systemPrompt`, and `output`.
          Unknown properties and invalid property lines cause a format exception.
          The `output` value must match one of the `SchemaType` enum constants.
      """);
}

void main() {}


