package chess;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import chess.pieces.Figure;
import chess.pieces.Bishop;
import chess.pieces.Pawn;
import chess.pieces.Rook;
import chess.pieces.Queen;
import chess.utils.Colors;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Match
{
    public HashMap<String, Figure> figures;
    public ArrayList<ArrayList<Integer>> moves;
    private int size;
    public Match(String filename) {
        figures = new HashMap<>();
        moves = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            int state = -1;
            String line;
            while ((line = br.readLine()) != null) {
                if (line.length() == 0) continue;
                if (line.charAt(0) == '_') {
                    if (line.equals("_Chess_")) state = 0;
                    else if (line.equals("_Setup_")) state = 1;
                    else if (line.equals("_MOVES_")) state = 2;
                    continue;
                }
                if (state == -1) continue;
                if (state == 0) {
                    size = Integer.parseInt(line.split(" ")[1]);
                } else if (state == 1) {
                    String[] strs = line.split(";");
                    Colors color = Colors.valueOf(strs[0].toUpperCase());
                    int c = strs[2].charAt(0) - 'a', r = strs[2].charAt(1) - '1';
                    Figure f = null;
                    if (strs[1].equals("PAWN")) {
                        f = new Pawn(color, c, r);
                    } else if (strs[1].equals("BISHOP")) {
                        f = new Bishop(color, c, r);
                    } else if (strs[1].equals("QUEEN")) {
                        f = new Queen(color, c, r);
                    } else if (strs[1].equals("ROOK")) {
                        f = new Rook(color, c, r);
                    }
                    if (f != null && checkCoord(c) && checkCoord(r)) figures.put(f.fieldName(c, r), f);
                } else if (state == 2) {
                    int c1 = line.charAt(0) - 'a', r1 = line.charAt(1) - '1',
                        c2 = line.charAt(2) - 'a', r2 = line.charAt(3) - '1';
                    moves.add(new ArrayList<>(List.of(c1, r1, c2, r2)));
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public void play() {
        Colors current = Colors.LIGHT;
        for (ArrayList<Integer> move : moves) {
            String space = Figure.staticFieldName(move.get(0), move.get(1));
            if (!checkCoord(move.get(2)) || !checkCoord(move.get(3))) continue;
            Figure f = figures.get(space);
            if (f == null || f.getColor() != current) continue;
            if (f.move(figures, move.get(2), move.get(3)) == 0) continue;
            int[] pos = f.getPos();
            if (f instanceof Pawn && (f.getColor() == Colors.DARK && pos[1] == 0 ||
                    f.getColor() == Colors.LIGHT && pos[1] == size - 1))
                figures.put(f.fieldName(pos[0], pos[1]), new Queen(f.getColor(), pos[0], pos[1]));
            for (String s : figures.keySet()) System.out.print(figures.get(s).toString() + " ");
            System.out.println();
            current = current == Colors.LIGHT ? Colors.DARK : Colors.LIGHT;
        }
        System.out.println("Game over");
    }
    public boolean checkCoord(int x) { return x >= 0 && x < size; }
}