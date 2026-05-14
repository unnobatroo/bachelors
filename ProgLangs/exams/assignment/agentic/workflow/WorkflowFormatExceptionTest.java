package agentic.workflow;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WorkflowFormatExceptionTest {
    @Test
    public void constructor01() {
        WorkflowFormatException exception = new WorkflowFormatException("broken workflow");

        assertEquals("broken workflow", exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    public void constructor02() {
        IllegalStateException cause = new IllegalStateException("cause");
        WorkflowFormatException exception = new WorkflowFormatException("broken workflow", cause);

        assertEquals("broken workflow", exception.getMessage());
        assertSame(cause, exception.getCause());
    }
}
