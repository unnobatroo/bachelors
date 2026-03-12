package Line;

import Point.Point;

/**
 * Represents an axis-aligned rectangle in 2D space.
 * The rectangle is defined by its bottom-right corner and its width and height.
 */
public class Rectangle {
    private Point bottomRight;
    private double width;
    private double height;

    /**
     * Creates a rectangle with the specified bottom-right corner, width, and
     * height.
     *
     * @param bottomRight the bottom-right corner of the rectangle
     * @param width       the width of the rectangle (non-negative)
     * @param height      the height of the rectangle (non-negative)
     */
    public Rectangle(Point bottomRight, double width, double height) {
        this.bottomRight = bottomRight;
        this.width = Math.max(0, width); // Ensure non-negative
        this.height = Math.max(0, height); // Ensure non-negative
    }

    /**
     * Returns the top-left corner of this rectangle.
     *
     * @return a Point representing the top-left corner
     */
    public Point topLeft() {
        return new Point(bottomRight.getX() - width, bottomRight.getY() + height);
    }

    /**
     * Returns the top-right corner of this rectangle.
     *
     * @return a Point representing the top-right corner
     */
    public Point topRight() {
        return new Point(bottomRight.getX(), bottomRight.getY() + height);
    }

    /**
     * Returns the bottom-left corner of this rectangle.
     *
     * @return a Point representing the bottom-left corner
     */
    public Point bottomLeft() {
        return new Point(bottomRight.getX() - width, bottomRight.getY());
    }

    /**
     * Returns the bottom-right corner of this rectangle.
     *
     * @return a Point representing the bottom-right corner
     */
    public Point getBottomRight() {
        return bottomRight;
    }

    /**
     * Gets the width of this rectangle.
     */
    public double getWidth() {
        return width;
    }

    /**
     * Gets the height of this rectangle.
     */
    public double getHeight() {
        return height;
    }

    /**
     * Calculates the intersection (overlap) of this rectangle with another
     * rectangle.
     * Returns the largest rectangle that is contained in both.
     *
     * @param other the other rectangle
     * @return a new Rectangle representing the intersection, or a rectangle with 0
     *         dimensions if no intersection
     */
    public Rectangle intersect(Rectangle other) {
        // Find the largest coordinates of the top-left corners
        Point thisTopLeft = this.topLeft();
        Point otherTopLeft = other.topLeft();

        double intersectLeft = Math.max(thisTopLeft.getX(), otherTopLeft.getX());
        double intersectTop = Math.min(thisTopLeft.getY(), otherTopLeft.getY());

        // Find the smallest coordinates of the bottom-right corners
        double intersectRight = Math.min(this.bottomRight.getX(), other.bottomRight.getX());
        double intersectBottom = Math.max(this.bottomRight.getY(), other.bottomRight.getY());

        // Calculate width and height
        double intersectWidth = Math.max(0, intersectRight - intersectLeft);
        double intersectHeight = Math.max(0, intersectTop - intersectBottom);

        // Create the intersection rectangle
        Point intersectBottomRight = new Point(intersectRight, intersectBottom);
        return new Rectangle(intersectBottomRight, intersectWidth, intersectHeight);
    }

    /**
     * Calculates the smallest rectangle that encloses (contains) both this
     * rectangle and another.
     *
     * @param other the other rectangle
     * @return a new Rectangle representing the smallest enclosing rectangle
     */
    public Rectangle enclosing(Rectangle other) {
        // Find the smallest coordinates of the top-left corners
        Point thisTopLeft = this.topLeft();
        Point otherTopLeft = other.topLeft();

        double enclosingLeft = Math.min(thisTopLeft.getX(), otherTopLeft.getX());
        double enclosingTop = Math.max(thisTopLeft.getY(), otherTopLeft.getY());

        // Find the largest coordinates of the bottom-right corners
        double enclosingRight = Math.max(this.bottomRight.getX(), other.bottomRight.getX());
        double enclosingBottom = Math.min(this.bottomRight.getY(), other.bottomRight.getY());

        // Calculate width and height
        double enclosingWidth = enclosingRight - enclosingLeft;
        double enclosingHeight = enclosingTop - enclosingBottom;

        // Create the enclosing rectangle
        Point enclosingBottomRight = new Point(enclosingRight, enclosingBottom);
        return new Rectangle(enclosingBottomRight, enclosingWidth, enclosingHeight);
    }

    /**
     * Returns a string representation of this rectangle.
     * Format: "(%x,%y)-(%x,%y)" where the first pair is bottom-left and second pair
     * is top-right
     */
    @Override
    public String toString() {
        Point tl = topLeft();
        Point br = bottomRight;
        return String.format("(%.0f,%.0f)-(%.0f,%.0f)",
                tl.getX(), tl.getY(),
                br.getX(), br.getY());
    }
}
