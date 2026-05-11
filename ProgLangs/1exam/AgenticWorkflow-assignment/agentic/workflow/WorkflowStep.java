package agentic.workflow;

import agentic.workflow.llm.SchemaType;
import agentic.workflow.llm.StructuredOutput;

import java.util.Objects;

/**
 * Represents one step in an agent workflow.
 */
public class WorkflowStep {
    private String name;
    private String prompt;
    private String systemPrompt;
    private StructuredOutput structuredOutput;

    /**
     * Creates a workflow step with validated fields.
     *
     * @param name             the human-readable identifier of the workflow step
     * @param prompt           the user-facing prompt describing what the step
     *                         should do
     * @param systemPrompt     the system-level instruction associated with the step
     * @param structuredOutput the structured output description expected from this
     *                         step
     */
    public WorkflowStep(String name, String prompt, String systemPrompt, StructuredOutput structuredOutput) {
        if (name == null || name.isBlank()
                || prompt == null || prompt.isBlank()
                || systemPrompt == null || systemPrompt.isBlank()
                || structuredOutput == null) {
            throw new IllegalArgumentException(
                    "`name`, `prompt`, and `systemPrompt` must not be blank, and `structuredOutput` must not be `null`.");
        }
        this.name = name;
        this.prompt = prompt;
        this.systemPrompt = systemPrompt;
        this.structuredOutput = structuredOutput;
    }

    /**
     * Returns the workflow step name.
     *
     * @return the step name
     */
    public String getName() {
        return name;
    }

    /**
     * Updates the workflow step name.
     *
     * @param name the new step name
     */
    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name must not be null or blank");
        }
        this.name = name;
    }

    /**
     * Returns the user-facing prompt.
     *
     * @return the prompt text
     */
    public String getPrompt() {
        return prompt;
    }

    /**
     * Updates the user-facing prompt.
     *
     * @param prompt the new prompt text
     */
    public void setPrompt(String prompt) {
        if (prompt == null || prompt.isBlank()) {
            throw new IllegalArgumentException("prompt must not be null or blank");
        }
        this.prompt = prompt;
    }

    /**
     * Returns the system-level instruction.
     *
     * @return the system prompt text
     */
    public String getSystemPrompt() {
        return systemPrompt;
    }

    /**
     * Updates the system-level instruction.
     *
     * @param systemPrompt the new system prompt text
     */
    public void setSystemPrompt(String systemPrompt) {
        if (systemPrompt == null || systemPrompt.isBlank()) {
            throw new IllegalArgumentException("systemPrompt must not be null or blank");
        }
        this.systemPrompt = systemPrompt;
    }

    /**
     * Returns the structured output description.
     *
     * @return the structured output specification
     */
    public StructuredOutput getStructuredOutput() {
        return structuredOutput;
    }

    /**
     * Updates the structured output description.
     *
     * @param structuredOutput the new structured output specification
     */
    public void setStructuredOutput(StructuredOutput structuredOutput) {
        if (structuredOutput == null) {
            throw new IllegalArgumentException("structuredOutput must not be null");
        }
        this.structuredOutput = structuredOutput;
    }

    /**
     * Returns whether this step expects a structured output.
     *
     * @return {@code true} if at least one schema type is declared, otherwise
     *         {@code false}
     */
    public boolean expectsStructuredOutput() {
        return structuredOutput != null && structuredOutput.size() > 0;
    }

    /**
     * Produces a simple example response for the first declared schema type.
     *
     * @return a simulated response text, or an empty string when no schema is
     *         available
     */
    public String simulateResponse() {
        if (structuredOutput == null || structuredOutput.size() == 0)
            return "";

        SchemaType primary = structuredOutput.getSchemaTypes().get(0);

        switch (primary) {
            case INT:
                return "0";
            case STRING:
                return "sample";
            case BOOLEAN:
                return "true";
            case LIST_INT:
                return "[1,2,3]";
            case LIST_STRING:
                return "[\"a\",\"b\"]";
            case MAP_STRING_STRING:
                return "{\"key\":\"value\"}";
            default:
                return "";
        }
    }

    @Override
    public String toString() {
        return "WorkflowStep{name='" + name + "', prompt='" + prompt + "', systemPrompt='" + systemPrompt
                + "', structuredOutput=" + structuredOutput + "}";
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof WorkflowStep)) {
            return false;
        }
        WorkflowStep step = (WorkflowStep) other;
        return Objects.equals(name, step.name)
                && Objects.equals(prompt, step.prompt)
                && Objects.equals(systemPrompt, step.systemPrompt)
                && Objects.equals(structuredOutput, step.structuredOutput);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, prompt, systemPrompt, structuredOutput);
    }
}
