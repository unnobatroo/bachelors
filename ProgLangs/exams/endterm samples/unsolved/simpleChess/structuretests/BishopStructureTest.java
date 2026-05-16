package chess.tests;

import static check.CheckThat.*;
import static check.CheckThat.Condition.*;
import org.junit.jupiter.api.*;
import check.CheckThat;

public class BishopStructureTest {
    @BeforeAll
    public static void init() {
        CheckThat.theClass("chess.pieces.Bishop", withParent("chess.pieces.Figure"))
            .thatIs(FULLY_IMPLEMENTED, INSTANCE_LEVEL, VISIBLE_TO_ALL);
    }

    @Test
    public void constructor() {
        it.hasConstructor(withArgs("chess.utils.Colors", "int", "int"))
            .thatIs(VISIBLE_TO_ALL);
    }

    @Test
    public void methodCheckMove() {
        it.hasMethod("checkMove", withParams("int", "int"))
            .thatIs(FULLY_IMPLEMENTED, INSTANCE_LEVEL, VISIBLE_TO_ALL)
            .thatReturnsNothing();
    }

}

