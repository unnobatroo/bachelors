import agentic.workflow.WorkflowStep;
import agentic.workflow.llm.SchemaType;
import agentic.workflow.llm.StructuredOutput;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import module org.junit.jupiter;

@Test
public void testExpectsStructuredOutput() {
    WorkflowStep step = new WorkflowStep(
        "collect_numbers",
        "Ask the user for integers.",
        "Return only the numbers.",
        new StructuredOutput(new ArrayList<>(java.util.List.of(SchemaType.INT)))
    );

    assertTrue(step.expectsStructuredOutput());
}

@Test
public void testSimulateResponseByPrimaryType() {
    assertEquals("0", new WorkflowStep("int", "p", "s", new StructuredOutput(new ArrayList<>(java.util.List.of(SchemaType.INT)))).simulateResponse());
    assertEquals("sample", new WorkflowStep("string", "p", "s", new StructuredOutput(new ArrayList<>(java.util.List.of(SchemaType.STRING)))).simulateResponse());
    assertEquals("true", new WorkflowStep("bool", "p", "s", new StructuredOutput(new ArrayList<>(java.util.List.of(SchemaType.BOOLEAN)))).simulateResponse());
    assertEquals("[1,2,3]", new WorkflowStep("listInt", "p", "s", new StructuredOutput(new ArrayList<>(java.util.List.of(SchemaType.LIST_INT)))).simulateResponse());
    assertEquals("[\"a\",\"b\"]", new WorkflowStep("listString", "p", "s", new StructuredOutput(new ArrayList<>(java.util.List.of(SchemaType.LIST_STRING)))).simulateResponse());
    assertEquals("{\"key\":\"value\"}", new WorkflowStep("map", "p", "s", new StructuredOutput(new ArrayList<>(java.util.List.of(SchemaType.MAP_STRING_STRING)))).simulateResponse());
}

void main() {}
