package chess.pieces;

import chess.utils.Colors;
import chess.utils.IllegalMoveException;

public class Pawn extends Figure {
    public Pawn(Colors color, int c, int r) { super(color, c, r); }
    public void checkMove(int c, int r) throws IllegalMoveException {
        if ((color != Colors.LIGHT || this.r != r+1) &&
            (color != Colors.DARK || this.r == r-1) && Math.abs(this.c-c) > 1)
            throw new IllegalMoveException("Illegal pawn move");
    }
}