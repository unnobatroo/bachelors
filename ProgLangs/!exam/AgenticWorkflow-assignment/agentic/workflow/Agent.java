package agentic.workflow;

import java.util.List;

public class Agent {
    private String name;
    private List<WorkflowStep> steps;

    public Agent(String name) throws Exception {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name must not be null or blank");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name must not be null or blank");
        }
        this.name = name;
    }

    public void addStep(WorkflowStep step) throws IllegalArgumentException {

    }

}
