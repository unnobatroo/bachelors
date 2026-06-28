package Point;

/**
 * Calculates and displays the path of points with distance information.
 * Takes an even number of coordinate arguments (at least 4).
 */
public class PointPath {
    /**
     * Main entry point. Processes a sequence of points and calculates distances.
     * Outputs each point with cumulative and segment distances.
     *
     * @param args command line arguments as coordinate pairs (must be even number,
     *             at least 4)
     */
    public static void main(String[] args) {
        if (args.length < 4 || args.length % 2 != 0) {
            IO.println("Error: Please provide an even number of coordinates (at least 4).");
            return;
        }

        Point current = new Point(args, 0);
        double totalDistance = 0.0;
        IO.println(current.print());

        for (int i = 2; i < args.length; i += 2) {
            Point next = new Point(args, i);
            double segmentDistance = current.distance(next);
            totalDistance += segmentDistance;
            current = next;

            String output = "%s with total distance %2.1f, segment distance %2.1f from (%2.1f, %2.1f)"
                    .formatted(current.print(), totalDistance, segmentDistance, current.getX(), current.getY());
            IO.println(output);
        }
    }
}