package agentic.workflow;

/**
 * Thrown when a workflow description file or workflow content has an invalid
 * format.
 * This checked exception signals that the caller should handle malformed
 * workflow input explicitly.
 */
public class WorkflowFormatException extends Exception {
    /**
     * Creates the exception with an explanatory error message.
     *
     * @param message the explanation of the parsing or format problem
     */
    public WorkflowFormatException(String message) {
        super(message);
    }

    /**
     * Creates the exception with an explanatory error message and the original
     * cause.
     *
     * @param message the explanation of the parsing or format problem
     * @param cause   the underlying cause
     */
    public WorkflowFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
