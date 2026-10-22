package pebble;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestRunner {

    private static int passed = 0;
    private static int failed = 0;

    /** basic boolean check */
    private static void check(String name, boolean cond) {
        if (cond) {
            System.out.println("[PASS] " + name);
            passed++;
        } else {
            System.err.println("[FAIL] " + name);
            failed++;
        }
    }

    /** checks expected.equals(actual) */
    private static void eq(String name, Object expected, Object actual) {
        if (expected == null ? actual == null : expected.equals(actual)) {
            check(name, true);
        } else {
            System.err.println("[FAIL] " + name + " - expected: " + expected + ", got: " + actual);
            failed++;
        }
    }

    /** makes sure action throws expectedType */
    private static void throwsEx(
        String name,
        Class<? extends Throwable> expectedType,
        Runnable action
    ) {
        try {
            action.run();
            System.err.println("[FAIL] " + name + " - nothing thrown");
            failed++;
        } catch (Throwable t) {
            check(name, expectedType.isInstance(t));
        }
    }

    /** checks 3x3, 4x4, 6x6 presets and their 5n turn limits */
    private static void testPresetsAndInit() {
        eq("3x3 turns", 15, BoardSize.SMALL.maxTurns());
        eq("4x4 turns", 20, BoardSize.MEDIUM.maxTurns());
        eq("6x6 turns", 30, BoardSize.LARGE.maxTurns());

        for (BoardSize preset : BoardSize.values()) {
            int n = preset.size();
            PebbleGame g = new PebbleGame(n, new Random(42L));
            eq("size " + n, n, g.size());
            eq("white count " + n, n, g.count(Cell.WHITE));
            eq("black count " + n, n, g.count(Cell.BLACK));
            eq("empty count " + n, n * n - 2 * n, g.count(Cell.EMPTY));
            eq("white starts", Cell.WHITE, g.currentPlayer());
            check("not over initially", !g.isGameOver());
        }
    }

    /** checks single slide, chain push in all 4 directions, empty gap stop, and edge fall-off */
    private static void testMoveAndPushMechanics() {
        Cell[][] grid = {
            { Cell.WHITE, Cell.BLACK, Cell.EMPTY, Cell.BLACK },
            { Cell.EMPTY, Cell.WHITE, Cell.EMPTY, Cell.EMPTY },
            { Cell.EMPTY, Cell.BLACK, Cell.EMPTY, Cell.EMPTY },
            { Cell.EMPTY, Cell.WHITE, Cell.EMPTY, Cell.EMPTY },
        };
        PebbleGame g = new PebbleGame(grid, Cell.WHITE, 20);

        // (0,0) pushes (0,1) RIGHT into gap (0,2); (0,3) across the gap stays put
        check("push right into gap", g.move(0, 0, Direction.RIGHT));
        eq("(0,0) empty", Cell.EMPTY, g.cellAt(0, 0));
        eq("(0,1) white", Cell.WHITE, g.cellAt(0, 1));
        eq("(0,2) black", Cell.BLACK, g.cellAt(0, 2));
        eq("(0,3) untouched black", Cell.BLACK, g.cellAt(0, 3));
        eq("turn passed to black", Cell.BLACK, g.currentPlayer());

        // black at (0,2) pushes (0,3) RIGHT off the edge
        check("black pushes edge pebble off right", g.move(0, 2, Direction.RIGHT));
        eq("black count dropped to 2", 2, g.count(Cell.BLACK));
        eq("(0,3) is now the pusher", Cell.BLACK, g.cellAt(0, 3));

        // white at (1,1) pushes (2,1)=B and (3,1)=W DOWN; (3,1)=W falls off bottom edge
        check("white pushes vertical chain down off edge", g.move(1, 1, Direction.DOWN));
        eq("(1,1) empty", Cell.EMPTY, g.cellAt(1, 1));
        eq("(2,1) white", Cell.WHITE, g.cellAt(2, 1));
        eq("(3,1) black", Cell.BLACK, g.cellAt(3, 1));
        eq("white count dropped to 2", 2, g.count(Cell.WHITE));
    }

    /** checks selection, invalid moves, turn limit draw/win, and early wipeout */
    private static void testRulesAndEndConditions() {
        Cell[][] grid = {
            { Cell.WHITE, Cell.EMPTY, Cell.BLACK },
            { Cell.EMPTY, Cell.EMPTY, Cell.EMPTY },
            { Cell.EMPTY, Cell.EMPTY, Cell.EMPTY },
        };
        PebbleGame g = new PebbleGame(grid, Cell.WHITE, 2);

        check("reject empty cell", !g.move(0, 1, Direction.RIGHT));
        check("reject enemy pebble", !g.move(0, 2, Direction.LEFT));
        check("reject self-push off top edge", !g.move(0, 0, Direction.UP));
        check("reject null position", !g.move(null, Direction.DOWN));
        check("reject null direction", !g.move(0, 0, null));
        eq("failed moves don't eat turns", 0, g.turnsPlayed());

        // select + moveSelected
        check("select own pebble", g.select(new Position(0, 0)));
        check("moveSelected right", g.moveSelected(Direction.RIGHT));
        eq("selection cleared", null, g.selected());

        // black pushes white back left -> 2 turns reached -> draw
        check("black moves left", g.move(0, 2, Direction.LEFT));
        check("game over at maxTurns", g.isGameOver());
        eq("1v1 at turn limit is draw", Cell.EMPTY, g.winner());
        check("no moves after game over", !g.move(0, 0, Direction.DOWN));

        // early elimination win: white yeets the only black pebble off the right edge
        Cell[][] koGrid = {
            { Cell.EMPTY, Cell.WHITE, Cell.BLACK },
            { Cell.EMPTY, Cell.EMPTY, Cell.EMPTY },
            { Cell.EMPTY, Cell.EMPTY, Cell.EMPTY },
        };
        PebbleGame ko = new PebbleGame(koGrid, Cell.WHITE, 15);
        check("white knocks out last black", ko.move(0, 1, Direction.RIGHT));
        check("game ends immediately on wipeout", ko.isGameOver());
        eq("white wins", Cell.WHITE, ko.winner());

        throwsEx("size < 2 throws", IllegalArgumentException.class, () -> new PebbleGame(1, new Random()));
        throwsEx("null rng throws", IllegalArgumentException.class, () -> new PebbleGame(3, null));
        throwsEx("bad starter throws", IllegalArgumentException.class, () -> new PebbleGame(grid, Cell.EMPTY, 5));
        throwsEx("out of bounds throws", IndexOutOfBoundsException.class, () -> g.cellAt(9, 9));
    }

    /** fuzzes 1,000 full games across 3x3, 4x4, 6x6 verifying algebraic invariants on every step */
    private static void testAlgebraicStressFuzz() {
        Random rng = new Random(123456789L);
        BoardSize[] presets = BoardSize.values();
        Direction[] dirs = Direction.values();
        int totalMovesVerified = 0;

        for (int gameIdx = 0; gameIdx < 1000; gameIdx++) {
            BoardSize preset = presets[gameIdx % presets.length];
            int n = preset.size();
            PebbleGame g = new PebbleGame(n, rng);

            while (!g.isGameOver()) {
                Cell actor = g.currentPlayer();
                int turnsBefore = g.turnsPlayed();
                int whiteBefore = g.count(Cell.WHITE);
                int blackBefore = g.count(Cell.BLACK);

                // snapshot full board before move
                Cell[][] before = new Cell[n][n];
                List<Position> ownPebbles = new ArrayList<>();
                for (int r = 0; r < n; r++) {
                    for (int c = 0; c < n; c++) {
                        before[r][c] = g.cellAt(r, c);
                        if (before[r][c] == actor) {
                            ownPebbles.add(new Position(r, c));
                        }
                    }
                }

                // pick a random valid (pebble, direction) pair
                Position start = ownPebbles.get(rng.nextInt(ownPebbles.size()));
                List<Direction> validDirs = new ArrayList<>(4);
                for (Direction d : dirs) {
                    Position next = start.step(d);
                    if (next.row() >= 0 && next.row() < n && next.col() >= 0 && next.col() < n) {
                        validDirs.add(d);
                    }
                }
                Direction dir = validDirs.get(rng.nextInt(validDirs.size()));

                // trace expected chain length L before executing move
                List<Position> chain = new ArrayList<>();
                Position cur = start;
                while (
                    cur.row() >= 0 && cur.row() < n &&
                    cur.col() >= 0 && cur.col() < n &&
                    before[cur.row()][cur.col()] != Cell.EMPTY
                ) {
                    chain.add(cur);
                    cur = cur.step(dir);
                }
                Position afterTail = cur; // first empty cell or out-of-bounds cell
                boolean tailFallsOff =
                    afterTail.row() < 0 || afterTail.row() >= n ||
                    afterTail.col() < 0 || afterTail.col() >= n;
                Cell tailColor = before[chain.get(chain.size() - 1).row()][chain.get(chain.size() - 1).col()];

                if (!g.select(start) || !g.moveSelected(dir)) {
                    check("fuzz move must succeed at game " + gameIdx, false);
                    return;
                }

                // verify conservation / fall-off equation
                int expectedWhite = whiteBefore - (tailFallsOff && tailColor == Cell.WHITE ? 1 : 0);
                int expectedBlack = blackBefore - (tailFallsOff && tailColor == Cell.BLACK ? 1 : 0);
                if (g.count(Cell.WHITE) != expectedWhite || g.count(Cell.BLACK) != expectedBlack) {
                    check("fuzz pebble conservation failed at game " + gameIdx, false);
                    return;
                }

                // build expected board and compare every single cell
                Cell[][] expected = new Cell[n][n];
                for (int r = 0; r < n; r++) {
                    System.arraycopy(before[r], 0, expected[r], 0, n);
                }
                expected[start.row()][start.col()] = Cell.EMPTY;
                for (int i = 0; i < chain.size(); i++) {
                    Position dest = chain.get(i).step(dir);
                    if (dest.row() >= 0 && dest.row() < n && dest.col() >= 0 && dest.col() < n) {
                        Position src = chain.get(i);
                        expected[dest.row()][dest.col()] = before[src.row()][src.col()];
                    }
                }

                for (int r = 0; r < n; r++) {
                    for (int c = 0; c < n; c++) {
                        if (g.cellAt(r, c) != expected[r][c]) {
                            check("fuzz cell mismatch at (" + r + "," + c + ")", false);
                            return;
                        }
                    }
                }

                if (
                    g.turnsPlayed() != turnsBefore + 1 ||
                    g.currentPlayer() != actor.opposite() ||
                    g.selected() != null
                ) {
                    check("fuzz turn/selection invariant failed", false);
                    return;
                }
                totalMovesVerified++;
            }

            // verify end-of-game state
            int w = g.count(Cell.WHITE);
            int b = g.count(Cell.BLACK);
            Cell expectedWinner = w > b ? Cell.WHITE : b > w ? Cell.BLACK : Cell.EMPTY;
            if (
                g.winner() != expectedWinner ||
                g.turnsPlayed() > g.maxTurns() ||
                (g.turnsPlayed() < g.maxTurns() && w > 0 && b > 0)
            ) {
                check("fuzz terminal invariant failed at game " + gameIdx, false);
                return;
            }
        }
        check("1,000 random games (" + totalMovesVerified + " moves) algebraic invariants", true);
    }

    /** runs all checks and exits 1 on failure */
    public static void main(String[] args) {
        testPresetsAndInit();
        testMoveAndPushMechanics();
        testRulesAndEndConditions();
        testAlgebraicStressFuzz();
        System.out.println("=== Results: " + passed + " passed, " + failed + " failed ===");
        if (failed > 0) System.exit(1);
    }
}
