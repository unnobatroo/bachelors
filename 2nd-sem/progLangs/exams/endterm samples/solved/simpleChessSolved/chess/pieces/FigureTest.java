package chess.pieces;

import org.junit.jupiter.api.Test;

import chess.utils.IllegalMoveException;
import chess.utils.Colors;

public class FigureTest
{
    @Test
    public void test() {
        new Bishop(Colors.DARK, 0, 0);
        new Pawn(Colors.DARK, 0, 0);
        new Queen(Colors.DARK, 0, 0);
        new Rook(Colors.DARK, 0, 0);
    }
}