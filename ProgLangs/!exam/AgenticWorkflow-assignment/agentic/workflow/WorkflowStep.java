package agentic.workflow;

import agentic.workflow.llm.SchemaType;
import agentic.workflow.llm.StructuredOutput;

public class WorkflowStep {
    private String name;
    private String prompt;
    private String systemPrompt;
    private StructuredOutput structuredOutput;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name must not be null or blank");
        }
        this.name = name;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        if (prompt == null || prompt.isBlank()) {
            throw new IllegalArgumentException("prompt must not be null or blank");
        }
        this.prompt = prompt;
    }

    public String getSystemPrompt() {
        return systemPrompt;
    }

    public void setSystemPrompt(String systemPrompt) {
        if (systemPrompt == null || systemPrompt.isBlank()) {
            throw new IllegalArgumentException("systemPrompt must not be null or blank");
        }
        this.systemPrompt = systemPrompt;
    }

    public StructuredOutput getStructuredOutput() {
        return structuredOutput;
    }

    public void setStructuredOutput(StructuredOutput structuredOutput) {
        if (structuredOutput == null) {
            throw new IllegalArgumentException("structuredOutput must not be null");
        }
        this.structuredOutput = structuredOutput;
    }

    public boolean expectsStructuredOutput() {
        return structuredOutput != null && structuredOutput.size() > 0;
    }

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
}
