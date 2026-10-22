package pebble;

public enum Cell {
    EMPTY("Empty"),
    WHITE("White"),
    BLACK("Black");

    private final String label;

    Cell(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }

    /** flips white <-> black, empty stays empty */
    public Cell opposite() {
        if (this == WHITE) return BLACK;
        if (this == BLACK) return WHITE;
        return EMPTY;
    }
}
