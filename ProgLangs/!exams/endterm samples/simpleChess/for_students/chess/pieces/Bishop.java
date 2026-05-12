package chess.pieces;

import chess.utils.Colors;
import chess.utils.IllegalMoveException;

public class Bishop extends Figure
{
    public Bishop(Colors color, int c, int r) { super(color, c, r); }
    public void checkMove(int c, int r) throws IllegalMoveException {
        if (this.c == c || this.r == r || Math.abs(this.c - c) != Math.abs(this.r - r))
            throw new IllegalMoveException("Illegal bishop move");
    }
}