package agentic.workflow.llm;

import java.util.Arrays;

/**
 * Describes the structured output schema expected from a workflow step.
 */
public class StructuredOutput {
    private final SchemaType[] schemaTypes;

    /**
     * Creates a structured output definition from one or more schema types.
     *
     * @param schemaTypes the allowed schema types for this output
     */
    public StructuredOutput(SchemaType... schemaTypes) {
        if (schemaTypes == null) {
            throw new NullPointerException("schema types themselves must not contain `null`.");
        }

        if (schemaTypes.length == 0) {
            throw new IllegalArgumentException("at least one schema type must be provided.");
        }

        for (SchemaType t : schemaTypes) {
            if (t == null) {
                throw new NullPointerException("schema types themselves must not contain `null`.");
            }
        }

        this.schemaTypes = schemaTypes.clone();
    }

    /**
     * Returns a defensive copy of the stored schema types.
     *
     * @return the schema types for this structured output
     */
    public SchemaType[] getSchemaTypes() {
        return schemaTypes.clone();
    }

    /**
     * Checks whether the given schema type is part of this structured output.
     *
     * @param schemaType the schema type to search for
     * @return {@code true} if the schema type is present, otherwise {@code false}
     */
    public boolean contains(SchemaType schemaType) {
        return Arrays.asList(schemaTypes).contains(schemaType);
    }

    /**
     * Returns the number of stored schema types.
     *
     * @return the number of schema types
     */
    public int size() {
        return schemaTypes.length;
    }

    @Override
    public String toString() {
        return "StructuredOutput{schemaTypes=" + Arrays.toString(schemaTypes) + "}";
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof StructuredOutput)) {
            return false;
        }
        StructuredOutput that = (StructuredOutput) other;
        return Arrays.equals(schemaTypes, that.schemaTypes);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(schemaTypes);
    }
}
