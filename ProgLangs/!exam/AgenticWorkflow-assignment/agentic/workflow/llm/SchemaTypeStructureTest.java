package agentic.workflow.llm;
import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@Test
public void elements() {
    Use.theEnum("agentic.workflow.llm.SchemaType")
       .ofEnumElements("INT", "STRING", "BOOLEAN", "LIST_INT", "LIST_STRING", "MAP_STRING_STRING")
       .that(hasUsualModifiers())
       .info("The available structured output field types.")
       .info("INT, STRING, and BOOLEAN represent simple scalar values.")
       .info("LIST_INT and LIST_STRING represent lists containing integers or strings.")
       .info("MAP_STRING_STRING represents a map where both keys and values are strings.");
}

void main() {}


