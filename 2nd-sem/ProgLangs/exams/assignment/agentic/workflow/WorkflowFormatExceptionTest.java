package agentic.workflow;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WorkflowFormatExceptionTest {
    @Test
    public void testMessageConstructor() {
        WorkflowFormatException exception = new WorkflowFormatException("broken file");

        assertEquals("broken file", exception.getMessage());
    }

    @Test
    public void testMessageAndCauseConstructor() {
        IllegalArgumentException cause = new IllegalArgumentException("cause");
        WorkflowFormatException exception = new WorkflowFormatException("broken file", cause);

        assertEquals("broken file", exception.getMessage());
        assertSame(cause, exception.getCause());
    }
}