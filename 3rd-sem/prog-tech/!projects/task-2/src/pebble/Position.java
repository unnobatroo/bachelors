package pebble;

public record Position(int row, int col) {

    /** next cell over in dir */
    public Position step(Direction dir) {
        if (dir == null) throw new IllegalArgumentException("dir cannot be null");
        return new Position(row + dir.dr(), col + dir.dc());
    }

    /** direction to target if orthogonally adjacent, else null */
    public Direction directionTo(Position target) {
        if (target == null) return null;
        return Direction.fromDelta(target.row - row, target.col - col);
    }

    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }
}
