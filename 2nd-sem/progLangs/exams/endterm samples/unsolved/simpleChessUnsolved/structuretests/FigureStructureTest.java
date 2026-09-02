package chess.tests;

import static check.CheckThat.*;
import static check.CheckThat.Condition.*;
import org.junit.jupiter.api.*;
import check.CheckThat;

public class FigureStructureTest {
    @BeforeAll
    public static void init() {
        CheckThat.theClass("chess.pieces.Figure", withInterfaces("chess.utils.Actions"))
            .thatIs(NOT_IMPLEMENTED, INSTANCE_LEVEL, VISIBLE_TO_ALL);
    }

    @Test
    public void fieldColor() {
        it.hasField("color: chess.utils.Colors")
            .thatIs(INSTANCE_LEVEL, NOT_MODIFIABLE, VISIBLE_TO_SUBCLASSES)
            .thatHas(GETTER)
            .thatHasNo(SETTER);
    }

    @Test
    public void fieldColumn() {
        it.hasField("c: int")
            .thatIs(INSTANCE_LEVEL, MODIFIABLE, VISIBLE_TO_SUBCLASSES)
            .thatHasNo(GETTER, SETTER);
    }

    @Test
    public void fieldRow() {
        it.hasField("r: int")
            .thatIs(INSTANCE_LEVEL, MODIFIABLE, VISIBLE_TO_SUBCLASSES)
            .thatHasNo(GETTER, SETTER);
    }

    @Test
    public void methodSetPos() {
        it.hasMethod("setPos", withParams( "int", "int" ))
            .thatIs(FULLY_IMPLEMENTED, INSTANCE_LEVEL, VISIBLE_TO_ALL)
            .thatReturnsNothing();
    }

    @Test
    public void methodGetPos() {
        it.hasMethod("getPos", withNoParams())
            .thatIs(FULLY_IMPLEMENTED, INSTANCE_LEVEL, VISIBLE_TO_ALL)
            .thatReturns("array of int");
    }

    @Test
    public void constructor() {
        it.hasConstructor(withArgs("chess.utils.Colors", "int", "int"))
            .thatIs(VISIBLE_TO_ALL);
    }

    @Test
    public void methodFieldName1() {
        it.hasMethod("fieldName", withParams("int", "int"))
            .thatIs(FULLY_IMPLEMENTED, INSTANCE_LEVEL, VISIBLE_TO_ALL)
            .thatReturns("String");
    }

    @Test
    public void methodFieldName2() {
        it.hasMethod("fieldName", withNoParams())
            .thatIs(FULLY_IMPLEMENTED, INSTANCE_LEVEL, VISIBLE_TO_NONE)
            .thatReturns("String");
    }
  
    @Test
    public void methodCheckMove() {
        it.hasMethod("checkMove", withParams("int", "int"))
            .thatIs(NOT_IMPLEMENTED, INSTANCE_LEVEL, VISIBLE_TO_ALL)
            .thatReturnsNothing();
    }

    @Test
    public void methodCheckPath() {
        it.hasMethod("checkPath", withParams("HashMap of String to Figure", "int", "int"))
            .thatIs(FULLY_IMPLEMENTED, INSTANCE_LEVEL, VISIBLE_TO_ALL)
            .thatReturnsNothing();
    }

    @Test
    public void methodCheckField() {
        it.hasMethod("checkField", withParams("HashMap of String to Figure", "int", "int"))
            .thatIs(FULLY_IMPLEMENTED, INSTANCE_LEVEL, VISIBLE_TO_ALL)
            .thatReturns( "chess.pieces.Figure" );
    }

    @Test
    public void methodMove() {
        it.hasMethod("move", withParams("HashMap of String to Figure", "int", "int"))
            .thatIs(FULLY_IMPLEMENTED, INSTANCE_LEVEL, VISIBLE_TO_ALL)
            .thatReturns("int");
    }




    @Test
    public void text() {
        it.has(TEXTUAL_REPRESENTATION);
    }
}
