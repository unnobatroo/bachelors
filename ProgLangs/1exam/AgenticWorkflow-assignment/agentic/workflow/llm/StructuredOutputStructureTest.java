import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    // usedLang = Lang.EN; // uncomment to enforce the message language
    Use.theClass("agentic.workflow.llm.StructuredOutput")
       .that(hasUsualModifiers());
}

@Test
public void fieldSchemaTypes() {
    it.hasField("schemaTypes: array of SchemaType")
      .thatIs(INSTANCE_LEVEL, FULLY_IMPLEMENTED, NOT_MODIFIABLE, VISIBLE_TO_NONE)
      .thatHas(GETTER)
      .thatHasNo(SETTER)
      .info("Stores the allowed schema types for one structured output definition.")
      .info("The array is never exposed directly; defensive copies are used to avoid data leaks.");
}

@Test
public void constructor() {
    it.hasConstructor(withArgsLikeAllFields())
      .thatIs(VISIBLE_TO_ALL)
      .thatThrows("IllegalArgumentException", "at least one schema type must be provided.")
      .thatThrows("NullPointerException", "schema types themselves must not contain `null`.")
      .info("Creates a structured output definition from one or more schema types.");
}

@Test
public void methodContains() {
    it.hasMethod("contains", withParams("schemaType: SchemaType"))
      .that(hasUsualModifiers())
      .thatReturns("boolean", "`true` if the given schema type is present in this structured output, otherwise `false`.")
      .info("The comparison is done with enum identity.")
      .testWith(testCase("testContainsExistingType"), "Verify that an included schema type is found.")
      .testWith(testCase("testContainsMissingType"), "Verify that a non-included schema type is not found.");
}

@Test
public void methodSize() {
    it.hasMethod("size", withNoParams())
      .that(hasUsualModifiers())
      .thatReturns("int", "The number of stored schema types.")
      .testWith(testCase("testSize"), "Verify that the size matches the number of constructor arguments.");
}

void main() {}


