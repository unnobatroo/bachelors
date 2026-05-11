package agentic.workflow;

import agentic.workflow.llm.SchemaType;
import agentic.workflow.llm.StructuredOutput;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Agent {
    private String name;
    private List<WorkflowStep> steps;

    /**
     * Creates an agent with the given validated name and initially no steps.
     *
     * @param name the agent name
     * @throws IllegalArgumentException if the name is null, empty, or blank
     */
    public Agent(String name) throws IllegalArgumentException {
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

    public void setName(String name) throws IllegalArgumentException {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("the name must not be `null`, empty, or blank.");
        }
        this.name = name;
    }

    /**
     * Adds a new step to the end of the workflow.
     * Step names must be unique within one agent.
     *
     * @param step the step to add
     * @throws IllegalArgumentException if the step is null or a step with the same
     *                                  name already exists
     */
    public void addStep(WorkflowStep step) throws IllegalArgumentException {
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
     *
     * @param stepName the name to search for
     * @return the matching step, or null if no such step exists
     * @throws IllegalArgumentException if the step name is null, empty, or blank
     */
    public WorkflowStep findStepByName(String stepName) throws IllegalArgumentException {
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
        StepExecutor<String> executor = new SimulatedStepExecutor();
        for (WorkflowStep s : steps) {
            System.out.println(s.getName());
            System.out.println(executor.execute(s));
        }
    }

    /**
     * Loads an agent from a workflow description text file.
     * The first non-empty line must be `AGENT: ...`.
     * Each step must begin with `STEP` and end with `ENDSTEP`.
     * Duplicate step names are not allowed.
     *
     * @param filename the workflow file to read
     * @return the loaded agent
     * @throws IllegalArgumentException if the filename is null, empty, or blank
     * @throws WorkflowFormatException if the file content is malformed
     * @throws IOException if the file cannot be read
     */
    public static Agent loadAgent(String filename) throws IllegalArgumentException, WorkflowFormatException, IOException {
        if (filename == null || filename.isBlank()) {
            throw new IllegalArgumentException("the filename must not be `null`, empty, or blank.");
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            String agentName = null;

            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }

                if (!line.startsWith("AGENT:")) {
                    throw new WorkflowFormatException("the first non-empty line must start with `AGENT:`.");
                }

                agentName = line.substring("AGENT:".length()).trim();
                if (agentName.isBlank()) {
                    throw new WorkflowFormatException("the agent name must not be blank.");
                }
                break;
            }

            if (agentName == null) {
                throw new WorkflowFormatException("the workflow file must contain an agent header.");
            }

            Agent agent = new Agent(agentName);
            WorkflowStep step;
            while ((step = parseStep(reader)) != null) {
                if (agent.findStepByName(step.getName()) != null) {
                    throw new WorkflowFormatException("duplicate step names are not allowed.");
                }
                agent.addStep(step);
            }
            return agent;
        }
    }

    private static WorkflowStep parseStep(BufferedReader reader) throws IOException, WorkflowFormatException {
        String line;

        while ((line = reader.readLine()) != null) {
            if (line.isBlank()) {
                continue;
            }
            if (!line.trim().equals("STEP")) {
                throw new WorkflowFormatException("each step must begin with `STEP`.");
            }
            break;
        }

        if (line == null) {
            return null;
        }

        String name = null;
        String prompt = null;
        String systemPrompt = null;
        SchemaType outputType = null;

        while ((line = reader.readLine()) != null) {
            if (line.isBlank()) {
                continue;
            }

            if (line.trim().equals("ENDSTEP")) {
                if (name == null || prompt == null || systemPrompt == null || outputType == null) {
                    throw new WorkflowFormatException("a step is missing one or more required properties.");
                }

                ArrayList<SchemaType> schemaTypes = new ArrayList<>();
                schemaTypes.add(outputType);
                return new WorkflowStep(name, prompt, systemPrompt, new StructuredOutput(schemaTypes));
            }

            int separator = line.indexOf('=');
            if (separator < 0) {
                throw new WorkflowFormatException("invalid step property line: " + line);
            }

            String key = line.substring(0, separator).trim();
            String value = line.substring(separator + 1).trim();
            if (value.isBlank()) {
                throw new WorkflowFormatException("step property values must not be blank.");
            }

            switch (key) {
                case "name":
                    name = value;
                    break;
                case "prompt":
                    prompt = value;
                    break;
                case "systemPrompt":
                    systemPrompt = value;
                    break;
                case "output":
                    try {
                        outputType = SchemaType.valueOf(value);
                    } catch (IllegalArgumentException exception) {
                        throw new WorkflowFormatException("unknown schema type: " + value, exception);
                    }
                    break;
                default:
                    throw new WorkflowFormatException("unknown step property: " + key);
            }
        }

        throw new WorkflowFormatException("a step must end with `ENDSTEP`.");
    }

    @Override
    public String toString() {
        return "Agent{name='" + name + "', steps=" + steps + "}";
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Agent)) {
            return false;
        }
        Agent agent = (Agent) other;
        return Objects.equals(name, agent.name) && Objects.equals(steps, agent.steps);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, steps);
    }
}
