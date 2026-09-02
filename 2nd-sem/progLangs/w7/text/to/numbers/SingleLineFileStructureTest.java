package text.to.numbers;

import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll public static void init(){Use.theClass("text.to.numbers.SingleLineFile").that(hasUsualModifiers());}

@Test public void methodAddNumbers(){it.hasMethod("addNumbers",withParams("filename: String")).thatIs(USABLE_WITHOUT_INSTANCE,FULLY_IMPLEMENTED,MODIFIABLE,VISIBLE_TO_ALL).thatReturns("int");}

void main(){}