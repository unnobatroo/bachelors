package agentic.workflow;

import llm.SchemaType;
import llm.StructuredOutput;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AgentTest {
    private WorkflowStep step(String name, SchemaType schemaType) {
        return new WorkflowStep(
                name,
                "Prompt for " + name,
                "System prompt for " + name,
                new StructuredOutput(new ArrayList<>(java.util.List.of(schemaType))));
    }

    @Test
    public void testStepCount() {
        Agent agent = new Agent("Demo");

        assertEquals(0, agent.getStepCount());
        agent.addStep(step("first", SchemaType.STRING));
        agent.addStep(step("second", SchemaType.INT));
        assertEquals(2, agent.getStepCount());
    }

    @Test
    public void testAddDuplicateStepRejected() {
        Agent agent = new Agent("Demo");
        agent.addStep(step("first", SchemaType.STRING));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> agent.addStep(step("first", SchemaType.INT)));

        assertTrue(exception.getMessage().contains("same name"));
    }

    @Test
    public void findStepByName() {
        Agent agent = new Agent("Demo");
        WorkflowStep first = step("first", SchemaType.STRING);
        agent.addStep(first);

        assertSame(first, agent.findStepByName(" first "));
    }

    @Test
    public void findStepByNameMissing() {
        Agent agent = new Agent("Demo");
        agent.addStep(step("first", SchemaType.STRING));

        assertNull(agent.findStepByName("missing"));
    }

    @Test
    public void testLoadAgentSuccess() throws IOException, WorkflowFormatException {
        Path file = Files.createTempFile("agent", ".agent");
        Files.writeString(file, """
                AGENT: Demo Agent
                STEP
                name=first
                prompt=Ask something
                systemPrompt=Be helpful
                output=STRING
                ENDSTEP
                STEP
                name=second
                prompt=Ask more
                systemPrompt=Be precise
                output=INT
                ENDSTEP
                """);

        Agent agent = Agent.loadAgent(file.toString());

        assertEquals("Demo Agent", agent.getName());
        assertEquals(2, agent.getStepCount());
        assertEquals("first", agent.findStepByName("first").getName());
        assertEquals("second", agent.findStepByName("second").getName());
    }

    @Test
    public void testLoadAgentRejectsMissingHeader() throws IOException {
        Path file = Files.createTempFile("agent-missing-header", ".agent");
        Files.writeString(file, """
                STEP
                name=first
                prompt=Ask something
                systemPrompt=Be helpful
                output=STRING
                ENDSTEP
                """);

        assertThrows(WorkflowFormatException.class, () -> Agent.loadAgent(file.toString()));
    }

    @Test
    public void testLoadAgentRejectsDuplicateStepNames() throws IOException {
        Path file = Files.createTempFile("agent-duplicate-step", ".agent");
        Files.writeString(file, """
                AGENT: Demo Agent
                STEP
                name=first
                prompt=Ask something
                systemPrompt=Be helpful
                output=STRING
                ENDSTEP
                STEP
                name=first
                prompt=Ask again
                systemPrompt=Be helpful again
                output=INT
                ENDSTEP
                """);

        assertThrows(WorkflowFormatException.class, () -> Agent.loadAgent(file.toString()));
    }
}
