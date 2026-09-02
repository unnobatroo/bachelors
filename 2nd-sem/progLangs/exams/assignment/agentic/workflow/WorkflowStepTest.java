package agentic.workflow;

import agentic.workflow.llm.SchemaType;
import agentic.workflow.llm.StructuredOutput;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WorkflowStepTest {
    @Test
    public void testExpectsStructuredOutput() {
        WorkflowStep step = new WorkflowStep(
                "sum_numbers",
                "Return a sum.",
                "You are a calculator.",
                new StructuredOutput(SchemaType.INT));

        assertTrue(step.expectsStructuredOutput());
    }

    @Test
    public void testSimulateResponseByPrimaryType() {
        WorkflowStep intStep = new WorkflowStep(
                "sum_numbers",
                "Return a sum.",
                "You are a calculator.",
                new StructuredOutput(SchemaType.INT));
        WorkflowStep listStep = new WorkflowStep(
                "collect_numbers",
                "Collect numbers.",
                "You are a calculator.",
                new StructuredOutput(SchemaType.LIST_INT));

        assertEquals("0", intStep.simulateResponse());
        assertEquals("[1,2,3]", listStep.simulateResponse());
    }

    @Test
    public void testSettersValidateInput() {
        WorkflowStep step = new WorkflowStep(
                "ask_topic",
                "Ask the topic.",
                "Be helpful.",
                new StructuredOutput(SchemaType.STRING));

        assertThrows(IllegalArgumentException.class, () -> step.setName(" "));
        assertThrows(IllegalArgumentException.class, () -> step.setPrompt(null));
        assertThrows(IllegalArgumentException.class, () -> step.setSystemPrompt(""));
        assertThrows(IllegalArgumentException.class, () -> step.setStructuredOutput(null));
    }
}