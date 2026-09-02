package chess.tests;

import static check.CheckThat.*;
import static check.CheckThat.Condition.*;
import org.junit.jupiter.api.*;
import check.CheckThat;

import chess.pieces.Figure;
import java.util.HashMap;

public class ActionsStructureTest {
    @BeforeAll
    public static void init() {
        CheckThat.theInterface("chess.utils.Actions")
            .thatIs(VISIBLE_TO_ALL);
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
            .thatIs(NOT_IMPLEMENTED, INSTANCE_LEVEL, VISIBLE_TO_ALL)
            .thatReturnsNothing();
    }

    @Test
    public void methodCheckField() {
        it.hasMethod("checkField", withParams("HashMap of String to Figure", "int", "int"))
            .thatIs(NOT_IMPLEMENTED, INSTANCE_LEVEL, VISIBLE_TO_ALL)
            .thatReturns( "chess.pieces.Figure" );
    }

    @Test
    public void methodMove() {
        it.hasMethod("move", withParams("HashMap of String to Figure", "int", "int"))
            .thatIs(NOT_IMPLEMENTED, INSTANCE_LEVEL, VISIBLE_TO_ALL)
            .thatReturns( "int" );
    }
}

