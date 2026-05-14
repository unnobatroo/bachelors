package chess.utils;

import java.util.HashMap;
import chess.pieces.Figure;

public interface Actions
{
    public void checkMove(int c, int r) throws IllegalMoveException;
    public void checkPath(HashMap<String, Figure> figures, int c, int r) throws IllegalMoveException;
    public Figure checkField(HashMap<String, Figure> figures, int c, int r);
    public int move(HashMap<String, Figure> figures, int c, int r);
}