package pebble;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Board {

    private final int size;
    private final Cell[][] grid;

    /** sets up an n x n board with n white and n black pebbles placed randomly */
    public Board(int size, Random rng) {
        if (size < 2) throw new IllegalArgumentException("size < 2: " + size);
        if (rng == null) throw new IllegalArgumentException(
            "rng cannot be null"
        );
        this.size = size;
        this.grid = new Cell[size][size];

        List<Position> spots = new ArrayList<>(size * size);
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                grid[r][c] = Cell.EMPTY;
                spots.add(new Position(r, c));
            }
        }
        Collections.shuffle(spots, rng);
        for (int i = 0; i < size; i++) {
            grid[spots.get(i).row()][spots.get(i).col()] = Cell.WHITE;
            grid[spots.get(i + size).row()][spots.get(i + size).col()] =
                Cell.BLACK;
        }
    }

    /** builds a board from a preset 2d grid for deterministic tests */
    public Board(Cell[][] src) {
        if (src == null || src.length < 2) {
            throw new IllegalArgumentException("grid must be >= 2x2");
        }
        this.size = src.length;
        this.grid = new Cell[size][size];
        for (int r = 0; r < size; r++) {
            if (src[r] == null || src[r].length != size) {
                throw new IllegalArgumentException("grid must be square");
            }
            for (int c = 0; c < size; c++) {
                grid[r][c] = src[r][c] == null ? Cell.EMPTY : src[r][c];
            }
        }
    }

    public int size() {
        return size;
    }

    public Cell get(Position p) {
        if (p == null) throw new IllegalArgumentException(
            "position cannot be null"
        );
        return get(p.row(), p.col());
    }

    public Cell get(int r, int c) {
        if (!inBounds(r, c)) {
            throw new IndexOutOfBoundsException("(" + r + ", " + c + ")");
        }
        return grid[r][c];
    }

    /** true if (r, c) is on the board */
    public boolean inBounds(int r, int c) {
        return r >= 0 && r < size && c >= 0 && c < size;
    }

    /** true if p is non-null and on the board */
    public boolean inBounds(Position p) {
        return p != null && inBounds(p.row(), p.col());
    }

    /** shoves the contiguous pebble chain 1 step in dir; tail falls off if pushed past the edge */
    public void pushChain(Position start, Direction dir) {
        if (dir == null) throw new IllegalArgumentException(
            "dir cannot be null"
        );
        if (!inBounds(start)) throw new IndexOutOfBoundsException(
            "out of bounds: " + start
        );
        if (get(start) == Cell.EMPTY) throw new IllegalArgumentException(
            "empty start: " + start
        );
        if (!inBounds(start.step(dir))) throw new IllegalArgumentException(
            "step out of bounds"
        );

        Position end = start;
        while (inBounds(end.step(dir)) && get(end.step(dir)) != Cell.EMPTY) {
            end = end.step(dir);
        }

        Position after = end.step(dir);
        if (inBounds(after)) {
            grid[after.row()][after.col()] = get(end);
        }

        for (Position cur = end; !cur.equals(start); ) {
            Position prev = new Position(
                cur.row() - dir.dr(),
                cur.col() - dir.dc()
            );
            grid[cur.row()][cur.col()] = get(prev);
            cur = prev;
        }
        grid[start.row()][start.col()] = Cell.EMPTY;
    }

    /** counts remaining pebbles of the given color */
    public int count(Cell color) {
        int n = 0;
        for (Cell[] row : grid) {
            for (Cell cell : row) {
                if (cell == color) n++;
            }
        }
        return n;
    }
}
