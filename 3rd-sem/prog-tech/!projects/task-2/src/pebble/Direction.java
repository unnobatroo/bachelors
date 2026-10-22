package pebble;

public enum Direction {
    UP(-1, 0),
    DOWN(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1);

    private final int dr;
    private final int dc;

    Direction(int dr, int dc) {
        this.dr = dr;
        this.dc = dc;
    }

    public int dr() {
        return dr;
    }

    public int dc() {
        return dc;
    }

    /** maps a 1-step (dr, dc) offset to a direction, null if diagonal or >1 step */
    public static Direction fromDelta(int dr, int dc) {
        for (Direction d : values()) {
            if (d.dr == dr && d.dc == dc) return d;
        }
        return null;
    }
}
