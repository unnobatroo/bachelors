package chess.tests;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import chess.pieces.FigureTest;

@Suite
@SelectClasses({
    FigureStructureTest.class
    , BishopStructureTest.class
    , PawnStructureTest.class
    , QueenStructureTest.class
    , RookStructureTest.class

    , ActionsStructureTest.class
    , ColorsStructureTest.class
    , IllegalMoveExceptionStructureTest.class

    , FigureTest.class

})
public class PiecesSuite {}
