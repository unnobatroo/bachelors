package chess.pieces;

import chess.utils.Colors;
import chess.utils.IllegalMoveException;

public class Rook extends Figure {
    public Rook(Colors color, int c, int r) { super(color, c, r); }
    public void checkMove(int c, int r) throws IllegalMoveException {
        if (this.c == c && this.r == r || this.c != c && this.r != r)
            throw new IllegalMoveException("Illegal rook move");
    }
}