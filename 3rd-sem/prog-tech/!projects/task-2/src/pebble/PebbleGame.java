package pebble;

import java.util.Random;

public class PebbleGame {

    private final Board board;
    private final int maxTurns;
    private Cell currentPlayer;
    private int turnsPlayed;
    private Position selected;

    /** starts a game from a preset board size (3x3, 4x4, 6x6) */
    public PebbleGame(BoardSize preset) {
        this(preset.size(), new Random());
    }

    /** starts a random n x n game with 5n max turns */
    public PebbleGame(int size, Random rng) {
        this.board = new Board(size, rng);
        this.maxTurns = 5 * size;
        this.currentPlayer = Cell.WHITE;
    }

    /** starts a game from a fixed grid for deterministic tests */
    public PebbleGame(Cell[][] grid, Cell starter, int maxTurns) {
        if (starter != Cell.WHITE && starter != Cell.BLACK) {
            throw new IllegalArgumentException("starter must be WHITE or BLACK");
        }
        if (maxTurns <= 0) {
            throw new IllegalArgumentException("maxTurns must be > 0");
        }
        this.board = new Board(grid);
        this.maxTurns = maxTurns;
        this.currentPlayer = starter;
    }

    public int size() {
        return board.size();
    }

    public int maxTurns() {
        return maxTurns;
    }

    public int turnsPlayed() {
        return turnsPlayed;
    }

    public int remainingTurns() {
        return Math.max(0, maxTurns - turnsPlayed);
    }

    public Cell currentPlayer() {
        return currentPlayer;
    }

    public Position selected() {
        return selected;
    }

    public Cell cellAt(int r, int c) {
        return board.get(r, c);
    }

    /** true if pos holds the current player's pebble and the game is still running */
    public boolean canSelect(Position pos) {
        return !isGameOver() && board.inBounds(pos) && board.get(pos) == currentPlayer;
    }

    /** locks in pos as selected if it belongs to the active player */
    public boolean select(Position pos) {
        if (!canSelect(pos)) return false;
        selected = pos;
        return true;
    }

    /** drops the active selection */
    public void clearSelection() {
        selected = null;
    }

    /** pushes whichever pebble is currently selected in dir */
    public boolean moveSelected(Direction dir) {
        return selected != null && move(selected, dir);
    }

    /** moves the pebble at start 1 step in dir, pushing neighbours and flipping the turn */
    public boolean move(Position start, Direction dir) {
        if (dir == null || !canSelect(start) || !board.inBounds(start.step(dir))) {
            return false;
        }
        board.pushChain(start, dir);
        selected = null;
        turnsPlayed++;
        currentPlayer = currentPlayer.opposite();
        return true;
    }

    /** convenience overload for row/col moves */
    public boolean move(int r, int c, Direction dir) {
        return move(new Position(r, c), dir);
    }

    /** counts how many pebbles of this color are left */
    public int count(Cell color) {
        return board.count(color);
    }

    /** game ends at 5n turns or when a player gets wiped out */
    public boolean isGameOver() {
        return turnsPlayed >= maxTurns || count(Cell.WHITE) == 0 || count(Cell.BLACK) == 0;
    }

    /** returns the winning color, or EMPTY on a tie */
    public Cell winner() {
        int w = count(Cell.WHITE);
        int b = count(Cell.BLACK);
        return w > b ? Cell.WHITE : b > w ? Cell.BLACK : Cell.EMPTY;
    }
}
