package chess.pieces;

import chess.utils.Colors;
import chess.utils.IllegalMoveException;

public class Queen extends Figure {
    public Queen(Colors color, int c, int r) { super(color, c, r); }
    public void checkMove(int c, int r) throws IllegalMoveException {
        boolean badDiagonal = this.c == c || this.r == r || Math.abs(this.c - c) != Math.abs(this.r - r);
        boolean badStraight = this.c == c && this.r == r || this.c != c && this.r != r;
        if (this.c == c && this.r == r || badDiagonal && badStraight)
            throw new IllegalMoveException("Illegal queen move");
    }
}