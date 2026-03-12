package Line;

import Point.Point;

/**
 * Represents a line segment in 2D space defined by two endpoints.
 */
public class Segment {
    private Point p1;
    private Point p2;

    /**
     * Creates a segment with the two given endpoints.
     *
     * @param p1 the first endpoint
     * @param p2 the second endpoint
     */
    public Segment(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    /**
     * Returns the Line object representing the infinite line that covers this
     * segment.
     *
     * @return a Line object passing through both endpoints
     */
    public Line line() {
        return new Line(p1, p2);
    }

    /**
     * Determines whether a point lies on this segment.
     * A point is on the segment if:
     * 1. It lies on the line defined by the two endpoints
     * 2. It is within the bounding box of the segment
     *
     * @param p the point to check
     * @return true if the point is on the segment, false otherwise
     */
    public boolean contains(Point p) {
        Line l = line();
        if (!l.contains(p)) {
            return false;
        }

        // Check if point is within the bounding box of the segment
        double x = p.getX();
        double y = p.getY();
        double minX = Math.min(p1.getX(), p2.getX());
        double maxX = Math.max(p1.getX(), p2.getX());
        double minY = Math.min(p1.getY(), p2.getY());
        double maxY = Math.max(p1.getY(), p2.getY());

        return x >= minX - 1e-9 && x <= maxX + 1e-9 &&
                y >= minY - 1e-9 && y <= maxY + 1e-9;
    }

    /**
     * Determines the orientation of point p with respect to the segment.
     * Uses the cross product to determine if the point is:
     * - to the right (positive): clockwise orientation
     * - to the left (negative): counter-clockwise orientation
     * - on the line (zero): collinear
     *
     * @param p the point to check
     * @return positive if p is to the right, negative if to the left, 0 if
     *         collinear
     */
    public double orientation(Point p) {
        double x1 = p1.getX();
        double y1 = p1.getY();
        double x2 = p2.getX();
        double y2 = p2.getY();
        double x = p.getX();
        double y = p.getY();

        // Cross product: (p2 - p1) × (p - p1)
        // = (x2 - x1)(y - y1) - (y2 - y1)(x - x1)
        return (x2 - x1) * (y - y1) - (y2 - y1) * (x - x1);
    }

    /**
     * Determines whether this segment intersects with another segment.
     * Uses the orientation method based on the algorithm from:
     * http://www.dcs.gla.ac.uk/~pat/52233/slides/Geometry1x1.pdf
     *
     * Two segments intersect if:
     * 1. General case: endpoints of each segment are on opposite sides of the other
     * segment
     * 2. Special case: endpoints are collinear and overlap
     *
     * @param seg2 the other segment
     * @return true if the segments intersect, false otherwise
     */
    public boolean intersects(Segment seg2) {
        Point p3 = seg2.p1;
        Point p4 = seg2.p2;

        double o1 = orientation(p3);
        double o2 = orientation(p4);
        double o3 = seg2.orientation(p1);
        double o4 = seg2.orientation(p2);

        final double EPSILON = 1e-9;

        // General case: opposite orientations
        if ((o1 > EPSILON && o2 < -EPSILON) || (o1 < -EPSILON && o2 > EPSILON)) {
            if ((o3 > EPSILON && o4 < -EPSILON) || (o3 < -EPSILON && o4 > EPSILON)) {
                return true;
            }
        }

        // Special cases: collinear and overlapping
        if (Math.abs(o1) < EPSILON && onSegment(p3))
            return true;
        if (Math.abs(o2) < EPSILON && onSegment(p4))
            return true;
        if (Math.abs(o3) < EPSILON && seg2.onSegment(p1))
            return true;
        if (Math.abs(o4) < EPSILON && seg2.onSegment(p2))
            return true;

        return false;
    }

    /**
     * Helper method to check if a point lies on this segment (for collinear case).
     * Assumes the point is already known to be collinear.
     *
     * @param p the point to check
     * @return true if p is on this segment, false otherwise
     */
    private boolean onSegment(Point p) {
        double x = p.getX();
        double y = p.getY();
        double minX = Math.min(p1.getX(), p2.getX());
        double maxX = Math.max(p1.getX(), p2.getX());
        double minY = Math.min(p1.getY(), p2.getY());
        double maxY = Math.max(p1.getY(), p2.getY());

        final double EPSILON = 1e-9;
        return x >= minX - EPSILON && x <= maxX + EPSILON &&
                y >= minY - EPSILON && y <= maxY + EPSILON;
    }

    /**
     * Gets the first endpoint of this segment.
     */
    public Point getP1() {
        return p1;
    }

    /**
     * Gets the second endpoint of this segment.
     */
    public Point getP2() {
        return p2;
    }
}
