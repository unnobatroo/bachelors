package agentic.workflow.llm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StructuredOutputTest {
    @Test
    public void testSize() {
        StructuredOutput output = new StructuredOutput(SchemaType.INT, SchemaType.STRING);

        assertEquals(2, output.size());
        assertTrue(output.contains(SchemaType.INT));
        assertTrue(output.contains(SchemaType.STRING));
    }

    @Test
    public void testContainsExistingType() {
        StructuredOutput output = new StructuredOutput(SchemaType.BOOLEAN);

        assertTrue(output.contains(SchemaType.BOOLEAN));
    }

    @Test
    public void testContainsMissingType() {
        StructuredOutput output = new StructuredOutput(SchemaType.BOOLEAN);

        assertFalse(output.contains(SchemaType.STRING));
    }

    @Test
    public void testDefensiveCopy() {
        SchemaType[] schemaTypes = {SchemaType.INT};
        StructuredOutput output = new StructuredOutput(schemaTypes);

        schemaTypes[0] = SchemaType.STRING;

        assertEquals(1, output.size());
        assertEquals(1, output.schemaTypes().length);
        assertEquals(SchemaType.INT, output.schemaTypes()[0]);
    }
}