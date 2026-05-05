package agentic.workflow.llm;

import java.util.ArrayList;

public class StructuredOutput {
    private final ArrayList<SchemaType> schemaTypes;

    public StructuredOutput(ArrayList<SchemaType> schemaTypes) {
        if (schemaTypes == null) {
            throw new NullPointerException("schema types themselves must not contain `null`.");
        }

        if (schemaTypes.isEmpty()) {
            throw new IllegalArgumentException("at least one schema type must be provided.");
        }

        for (SchemaType t : schemaTypes) {
            if (t == null) {
                throw new NullPointerException("schema types themselves must not contain `null`.");
            }
        }

        this.schemaTypes = new ArrayList<>(schemaTypes);
    }

    public ArrayList<SchemaType> getSchemaTypes() {
        return new ArrayList<>(schemaTypes);
    }
    public boolean contains(SchemaType schemaType) {
        return schemaTypes.contains(schemaType);
    }

    public int size() {
        return schemaTypes.size();
    }
}
