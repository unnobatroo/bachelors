package chess;

import chess.utils.Colors;
import chess.pieces.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class MatchTest
{
    @ParameterizedTest
    @CsvSource({"a5,DARK,ROOK", "b5,DARK,BISHOP", "d5,LIGHT,QUEEN", "c4,DARK,PAWN",
            "d4,DARK,PAWN", "a3,DARK,PAWN", "b2,LIGHT,PAWN", "c2,LIGHT,PAWN",
            "a1,LIGHT,ROOK", "b1,LIGHT,QUEEN", "d1,LIGHT,BISHOP"})
    public void test(String space, Colors color, String piece) {
        Match m = new Match("chess/Chess5x5.txt");
        m.play();
        assertEquals(m.figures.get(space).getColor(), color);
        assertEquals(piece.equals("PAWN"), m.figures.get(space) instanceof Pawn);
        assertEquals(piece.equals("BISHOP"), m.figures.get(space) instanceof Bishop);
        assertEquals(piece.equals("QUEEN"), m.figures.get(space) instanceof Queen);
        assertEquals(piece.equals("ROOK"), m.figures.get(space) instanceof Rook);
    }
}