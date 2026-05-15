package agentic.workflow;

import org.junit.jupiter.api.Test;

import agentic.workflow.llm.SchemaType;
import agentic.workflow.llm.StructuredOutput;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class AgentTest {
    @Test
    public void testLoadAgentSuccess() throws Exception {
        Agent agent = Agent.loadAgent("calculator.txt");

        assertEquals("Calculator Helper", agent.getName());
        assertEquals(2, agent.getStepCount());
        assertEquals("collect_numbers", agent.findStepByName("collect_numbers").getName());
        assertEquals("0", agent.findStepByName("sum_numbers").simulateResponse());
    }

    @Test
    public void findStepByName() {
        Agent agent = new Agent("Finder");
        WorkflowStep step = new WorkflowStep(
                "exists",
                "prompt",
                "system",
                new StructuredOutput(SchemaType.STRING));

        agent.addStep(step);

        assertEquals(step, agent.findStepByName("exists"));
    }

    @Test
    public void testLoadAgentRejectsMissingHeader() throws Exception {
        Path file = Files.createTempFile("missing-header", ".txt");
        Files.writeString(file, "STEP\nname=s\nprompt=p\nsystemPrompt=sp\noutput=STRING\nENDSTEP\n");

        try {
            assertThrows(WorkflowFormatException.class, () -> Agent.loadAgent(file.toString()));
        } finally {
            Files.deleteIfExists(file);
        }
    }

    @Test
    public void testStepCount() {
        Agent agent = new Agent("Study Coach");
        agent.addStep(new WorkflowStep(
                "ask_topic",
                "Ask the topic.",
                "Be helpful.",
                new StructuredOutput(SchemaType.STRING)));

        assertEquals(1, agent.getStepCount());
        assertEquals(1, agent.getSteps().size());
    }

    @Test
    public void testAddDuplicateStepRejected() {
        Agent agent = new Agent("Duplicate Test");
        WorkflowStep step = new WorkflowStep(
                "ask_topic",
                "Ask the topic.",
                "Be helpful.",
                new StructuredOutput(SchemaType.STRING));

        agent.addStep(step);

        assertThrows(IllegalArgumentException.class, () -> agent.addStep(step));
    }

    @Test
    public void findStepByNameMissing() {
        Agent agent = new Agent("Study Coach");

        assertNull(agent.findStepByName("missing"));
    }

    @Test
    public void testLoadAgentRejectsDuplicateStepNames() throws IOException {
        Path file = Files.createTempFile("duplicate-agent", ".txt");
        Files.writeString(file, """
                AGENT: Duplicate Agent
                STEP
                name=step1
                prompt=First prompt
                systemPrompt=First system prompt
                output=STRING
                ENDSTEP
                STEP
                name=step1
                prompt=Second prompt
                systemPrompt=Second system prompt
                output=BOOLEAN
                ENDSTEP
                """);

        try {
            assertThrows(WorkflowFormatException.class, () -> Agent.loadAgent(file.toString()));
        } finally {
            Files.deleteIfExists(file);
        }
    }
}