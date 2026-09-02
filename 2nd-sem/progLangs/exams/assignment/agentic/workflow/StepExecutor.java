package agentic.workflow;

/**
 * Executes a workflow step and returns a typed result.
 *
 * @param <T> the result type produced by the executor
 */
public interface StepExecutor<T> {
    T execute(WorkflowStep step);
}