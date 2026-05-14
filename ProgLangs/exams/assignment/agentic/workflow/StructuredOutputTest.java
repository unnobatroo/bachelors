package agentic.workflow;

import agentic.workflow.llm.SchemaType;
import agentic.workflow.llm.StructuredOutput;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class StructuredOutputTest {
    @Test
    public void testSize() {
        ArrayList<SchemaType> schemaTypes = new ArrayList<>();
        schemaTypes.add(SchemaType.INT);
        schemaTypes.add(SchemaType.STRING);

        StructuredOutput output = new StructuredOutput(schemaTypes);

        assertEquals(2, output.size());
        assertTrue(output.contains(SchemaType.INT));
        assertTrue(output.contains(SchemaType.STRING));
    }

    @Test
    public void testContainsExistingType() {
        StructuredOutput output = new StructuredOutput(new ArrayList<>(java.util.List.of(SchemaType.BOOLEAN)));

        assertTrue(output.contains(SchemaType.BOOLEAN));
    }

    @Test
    public void testContainsMissingType() {
        StructuredOutput output = new StructuredOutput(new ArrayList<>(java.util.List.of(SchemaType.BOOLEAN)));

        assertFalse(output.contains(SchemaType.STRING));
    }

    @Test
    public void testDefensiveCopy() {
        ArrayList<SchemaType> schemaTypes = new ArrayList<>(java.util.List.of(SchemaType.INT));
        StructuredOutput output = new StructuredOutput(schemaTypes);

        schemaTypes.add(SchemaType.STRING);

        assertEquals(1, output.size());
        assertEquals(1, output.getSchemaTypes().size());
    }
}
