package agentic.workflow;

/**
 * A simple executor that returns the simulated response for a workflow step.
 */
public final class SimulatedStepExecutor implements StepExecutor<String> {
    @Override
    public String execute(WorkflowStep step) {
        if (step == null) {
            throw new IllegalArgumentException("step must not be null");
        }
        return step.simulateResponse();
    }
}