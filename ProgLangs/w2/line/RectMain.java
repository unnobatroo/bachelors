package Line;

import Point.Point;

/**
 * Main program that processes rectangles from command line arguments.
 * Each set of 4 numbers defines a rectangle as (x1,y1)-(x2,y2) format.
 * 
 * The program:
 * - Reads rectangles as command line arguments (4 numbers per rectangle
 * minimum)
 * - Calculates the cumulative intersection (bounding) rectangle
 * - Calculates the cumulative enclosing rectangle
 */
public class RectMain {

    public static void main(String[] args) {
        if (args.length < 4) {
            System.err.println("Error: Please provide at least 4 arguments (one rectangle)");
            System.err.println("Usage: java RectMain x1 y1 x2 y2 [x3 y3 x4 y4 ...]");
            return;
        }

        // Process first rectangle
        double x1 = Double.parseDouble(args[0]);
        double y1 = Double.parseDouble(args[1]);
        double x2 = Double.parseDouble(args[2]);
        double y2 = Double.parseDouble(args[3]);

        Rectangle current = createRectangle(x1, y1, x2, y2);
        Rectangle currentIntersect = current;
        Rectangle currentEnclosing = current;

        System.out.println("Rectangle #1 is " + current.toString());

        int rectCount = 1;

        // Process remaining rectangles
        for (int i = 4; i + 3 < args.length; i += 4) {
            rectCount++;

            double nx1 = Double.parseDouble(args[i]);
            double ny1 = Double.parseDouble(args[i + 1]);
            double nx2 = Double.parseDouble(args[i + 2]);
            double ny2 = Double.parseDouble(args[i + 3]);

            Rectangle next = createRectangle(nx1, ny1, nx2, ny2);

            // Update cumulative intersection and enclosing
            currentIntersect = currentIntersect.intersect(next);
            currentEnclosing = currentEnclosing.enclosing(next);

            System.out.println("Rectangle #" + rectCount + " is " + next.toString() +
                    ", current bounding: " + currentIntersect.toString() +
                    ", current enclosing: " + currentEnclosing.toString());
        }
    }

    /**
     * Helper method to create a Rectangle from two opposite corners.
     * Automatically determines which is the bottom-right corner.
     *
     * @param x1 x-coordinate of first corner
     * @param y1 y-coordinate of first corner
     * @param x2 x-coordinate of second corner
     * @param y2 y-coordinate of second corner
     * @return a Rectangle with proper bottom-right corner and dimensions
     */
    private static Rectangle createRectangle(double x1, double y1, double x2, double y2) {
        double minX = Math.min(x1, x2);
        double maxX = Math.max(x1, x2);
        double minY = Math.min(y1, y2);
        double maxY = Math.max(y1, y2);

        Point bottomRight = new Point(maxX, minY);
        double width = maxX - minX;
        double height = maxY - minY;

        return new Rectangle(bottomRight, width, height);
    }
}
