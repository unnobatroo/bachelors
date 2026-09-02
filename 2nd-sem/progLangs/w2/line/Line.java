package Line;

import Point.Point;

/**
 * Represents a line in 2D space using the implicit equation: ax + by + c = 0
 * Note: vertical lines (where b = 0) can be represented, but the contains
 * method
 * should work correctly for all non-vertical lines.
 */
public class Line {
    private double a;
    private double b;
    private double c;
    private static final double EPSILON = 1e-9;

    /**
     * Creates a line with the given coefficients in the equation ax + by + c = 0
     *
     * @param a the coefficient of x
     * @param b the coefficient of y
     * @param c the constant term
     */
    public Line(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    /**
     * Creates a line from two points.
     * The line equation is derived from two points (x1, y1) and (x2, y2)
     *
     * @param p1 the first point
     * @param p2 the second point
     */
    public Line(Point p1, Point p2) {
        double x1 = p1.getX();
        double y1 = p1.getY();
        double x2 = p2.getX();
        double y2 = p2.getY();

        // Calculate line coefficients from two points
        // (y2 - y1)(x - x1) = (x2 - x1)(y - y1)
        // (y2 - y1)x - (x2 - x1)y + (x2 - x1)y1 - (y2 - y1)x1 = 0
        this.a = y2 - y1;
        this.b = -(x2 - x1);
        this.c = (x2 - x1) * y1 - (y2 - y1) * x1;
    }

    /**
     * Determines whether a point lies on this line.
     * A point (x, y) is on the line if ax + by + c = 0 (within floating point
     * tolerance)
     *
     * @param p the point to check
     * @return true if the point is on the line, false otherwise
     */
    public boolean contains(Point p) {
        double result = a * p.getX() + b * p.getY() + c;
        return Math.abs(result) < EPSILON;
    }

    /**
     * Determines whether this line is parallel to another line.
     * Two lines are parallel if their normal vectors are parallel,
     * i.e., if (a1, b1) and (a2, b2) are proportional.
     * This means a1 * b2 - a2 * b1 = 0
     *
     * @param line the other line
     * @return true if the lines are parallel, false otherwise
     */
    public boolean isParallelWith(Line line) {
        double cross = a * line.b - line.a * b;
        return Math.abs(cross) < EPSILON;
    }

    /**
     * Determines whether this line is orthogonal (perpendicular) to another line.
     * Two lines are orthogonal if their normal vectors are orthogonal,
     * i.e., if (a1, b1) · (a2, b2) = 0
     * This means a1 * a2 + b1 * b2 = 0
     *
     * @param line the other line
     * @return true if the lines are orthogonal, false otherwise
     */
    public boolean isOrthogonalTo(Line line) {
        double dot = a * line.a + b * line.b;
        return Math.abs(dot) < EPSILON;
    }

    /**
     * Gets the coefficient a of the line equation ax + by + c = 0
     */
    public double getA() {
        return a;
    }

    /**
     * Gets the coefficient b of the line equation ax + by + c = 0
     */
    public double getB() {
        return b;
    }

    /**
     * Gets the coefficient c of the line equation ax + by + c = 0
     */
    public double getC() {
        return c;
    }
}
