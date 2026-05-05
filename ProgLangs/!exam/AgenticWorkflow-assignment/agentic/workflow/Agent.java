package agentic.workflow;

import java.util.ArrayList;
import java.util.List;

public class Agent {
    private String name;
    private List<WorkflowStep> steps;

    public Agent(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("the name must not be `null`, empty, or blank.");
        }
        this.name = name;
        this.steps = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<WorkflowStep> getSteps() {
        return new ArrayList<>(steps);
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("the name must not be `null`, empty, or blank.");
        }
        this.name = name;
    }

    /**
     * Adds a new step to the end of the workflow.
     * Step names must be unique within one agent.
     */
    public void addStep(WorkflowStep step) {
        if (step == null) {
            throw new IllegalArgumentException(
                    "the step must not be `null`, and another step with the same name must not already exist.");
        }

        String newName = step.getName();
        for (WorkflowStep s : steps) {
            if (s.getName().equals(newName)) {
                throw new IllegalArgumentException(
                        "the step must not be `null`, and another step with the same name must not already exist.");
            }
        }
        steps.add(step);
    }

    public int getStepCount() {
        return steps.size();
    }

    /**
     * Searches for a step by name.
     * The comparison uses the trimmed search text and exact string equality.
     */
    public WorkflowStep findStepByName(String stepName) {
        if (stepName == null || stepName.isBlank()) {
            throw new IllegalArgumentException("the step name must not be `null`, empty, or blank.");
        }
        String name = stepName.trim();
        for (WorkflowStep s : steps) {
            if (s.getName().equals(name)) {
                return s;
            }
        }
        return null;
    }

    /**
     * Simulates running the full agent by printing each step name and a sample
     * response.
     */
    public void run() {
        for (WorkflowStep s : steps) {
            System.out.println(s.getName());
            System.out.println("Sample response");
        }
    }

    public static Agent loadAgent(String filename) {
        if (filename.isBlank() || filename == null){
            throw new IllegalArgumentException("the filename must not be `null`, empty, or blank.");
        }

        if()
    }
}
