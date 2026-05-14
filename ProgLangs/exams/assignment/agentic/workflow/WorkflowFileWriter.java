package agentic.workflow;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Utility for generating example workflow files.
 */
public final class WorkflowFileWriter {
    private WorkflowFileWriter() {
    }

    /**
     * Writes a calculator workflow file in the assignment format.
     *
     * @param outputFile the target file
     * @throws IOException if the file cannot be written
     */
    public static void writeCalculatorWorkflow(Path outputFile) throws IOException {
        writeWorkflow(outputFile,
                "Calculator Helper",
                new StepSpec("collect_numbers", "Ask the user for a list of integers.",
                        "You are a precise calculator assistant.", "LIST_INT"),
                new StepSpec("sum_numbers", "Return the sum of the integers.",
                        "Return only the numeric answer.", "INT"));
    }

    /**
     * Writes a study coach workflow file in the assignment format.
     *
     * @param outputFile the target file
     * @throws IOException if the file cannot be written
     */
    public static void writeStudyCoachWorkflow(Path outputFile) throws IOException {
        writeWorkflow(outputFile,
                "Study Coach",
                new StepSpec("ask_topic", "Ask the student what topic they want to revise.",
                        "Be supportive and concise.", "STRING"),
                new StepSpec("create_quiz", "Generate three review questions.",
                        "Output a small list.", "LIST_STRING"),
                new StepSpec("check_readiness", "Decide if the student seems ready for a quiz.",
                        "Return true or false only.", "BOOLEAN"));
    }

    /**
     * Writes a workflow file with the given agent name and steps.
     *
     * @param outputFile the target file
     * @param agentName  the workflow agent name
     * @param steps      the workflow steps to write
     * @throws IOException if the file cannot be written
     */
    public static void writeWorkflow(Path outputFile, String agentName, StepSpec... steps) throws IOException {
        if (outputFile == null) {
            throw new IllegalArgumentException("outputFile must not be null");
        }
        if (agentName == null || agentName.isBlank()) {
            throw new IllegalArgumentException("agentName must not be null or blank");
        }
        if (steps == null) {
            throw new IllegalArgumentException("steps must not be null");
        }

        Path parent = outputFile.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }

        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(outputFile))) {
            writer.println("AGENT: " + agentName);
            for (StepSpec step : steps) {
                if (step == null) {
                    throw new IllegalArgumentException("steps must not contain null values");
                }
                writer.println("STEP");
                writer.println("name=" + step.name());
                writer.println("prompt=" + step.prompt());
                writer.println("systemPrompt=" + step.systemPrompt());
                writer.println("output=" + step.outputType());
                writer.println("ENDSTEP");
            }
        }
    }

    /**
     * Describes one workflow step to be written by the utility.
     *
     * @param name         the step name
     * @param prompt       the step prompt
     * @param systemPrompt the system prompt
     * @param outputType   the schema type name
     */
    public record StepSpec(String name, String prompt, String systemPrompt, String outputType) {
    }
}