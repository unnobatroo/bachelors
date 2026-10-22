package pebble;

public enum BoardSize {
    SMALL(3),
    MEDIUM(4),
    LARGE(6);

    private final int size;

    BoardSize(int size) {
        this.size = size;
    }

    public int size() {
        return size;
    }

    public int maxTurns() {
        return 5 * size;
    }

    @Override
    public String toString() {
        return size + "x" + size + " (" + maxTurns() + " turns)";
    }
}
