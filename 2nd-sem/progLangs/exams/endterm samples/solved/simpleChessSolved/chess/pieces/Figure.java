package chess.pieces;

import chess.utils.Colors;
import chess.utils.Actions;
import chess.utils.IllegalMoveException;
import java.util.HashMap;

public abstract class Figure implements Actions
{
    protected final Colors color;
    public Colors getColor() { return color; }
    protected int c;
    protected int r;
    public void setPos(int c, int r) { this.c = c; this.r = r; }
    public int[] getPos() { return new int[] { c, r }; }
    public Figure(Colors color, int c, int r) {
        this.color = color;
        this.c = c;
        this.r = r;
    }
    public static String staticFieldName(int c, int r) { return Character.valueOf((char)('a'+c)).toString() + (1+r); }
    public String fieldName(int c, int r) { return staticFieldName(c, r); }
    private String fieldName() { return fieldName(this.c, this.r); }
    @Override
    public abstract void checkMove(int c, int r) throws IllegalMoveException;
    @Override
    public void checkPath(HashMap<String, Figure> figures, int c, int r) throws IllegalMoveException {
        int curc = this.c, curr = this.r;
        while (true) {
            //update the position towards the goal in both directions
            if (curc < c) curc++;
            else if (curc > c) curc--;
            if (curr < r) curr++;
            else if (curr > r) curr--;
            if (curc == c && curr == r) return; //checking if destination reached which is capture so return
            if (figures.containsKey(fieldName(curc, curr))) //check if along the route space is occupied
                throw new IllegalMoveException("Invalid move");
        }
        
    }
    @Override
    public Figure checkField(HashMap<String, Figure> figures, int c, int r) {
        return figures.getOrDefault(fieldName(c, r), null);
        /*for (String s : figures.keySet()) {
            Figure f = figures.get(s);
            if (f.c == c && f.r == r) return f;
        }
        return null;*/
    }
    @Override
    public int move(HashMap<String, Figure> figures, int c, int r) {
        try {
            checkMove(c, r);
            checkPath(figures, c, r);
            //check if pawn diagonal move that piece is present to capture
            if (this instanceof Pawn && this.c != c && !figures.containsKey(fieldName(c, r)))
                return 0;
            if (this instanceof Pawn && this.c == c && figures.containsKey(fieldName(c, r)))
                return 0;
        } catch (IllegalMoveException e) { return 0; }
        figures.remove(fieldName());
        this.c = c; this.r = r;
        figures.put(fieldName(), this);
        return 1;
    }
    @Override
    public String toString() {
        String s = "";
        if (this instanceof Pawn) s = "PAWN";
        else if (this instanceof Bishop) s = "BISHOP";
        else if (this instanceof Queen) s = "QUEEN";
        else if (this instanceof Rook) s = "ROOK";
        return color + ";" + s + ";" + fieldName();
    }

}