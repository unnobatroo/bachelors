package chess;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import chess.tests.PiecesSuite;

@Suite
@SelectClasses({
    PiecesSuite.class
    , MatchStructureTest.class
    , MatchTest.class
})
public class ChessSuite {}
