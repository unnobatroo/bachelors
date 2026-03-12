public class Point {
    /*
    Create a Point class containing two fields, x and y of type double.
    Create a simple constructor for it that takes two doubles and sets up the fields using them.
     */
    // double x, y;
    double x;
    double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /* To simplify the creation of Points, create one more constructor that takes all args 
    as a single parameter and an idx that indexes an element of it.
    Let it create the point made up from the number at idx (its x coordinate) and at idx+1 (its y coordinate).*/
    public Point(String[] args, int idx) {
        this.x = Double.parseDouble(args[idx]);
        this.y = Double.parseDouble(args[idx=+1]);
    }

    /* Create the method print() that creates a printout like this: At (10.0, 20.0).
    Use .formatted to make your printout and %2.1f as the format style.
    Search the overview slideshow for a sample use of .formatted. */
    String print() {
        return "At (%2.1f, %2.1f)".formatted(x, y);
    }

    /* To support the longer printout, create another version of print() (keep the old one, too!) that takes two parameters: a text and a Point.
    Make the second printout using it following this schema: At <the current point> <the parameter message> <the parameter point>*/
    String print(String message, Point p) {
        return "%s %s (%2.1f, %2.1f)".formatted(this.print(), message, p.x, p.y);
    }

    /* Create a method shift(dx,dy) which can change a point’s coordinates by dx and dy.
    Here, d indicates that these parameters are “differences” or “deltas.”
    At (10.0, 20.0)
    At (11.2, 16.6) after being shifted by (1.2, -3.4)*/
    void shift(double dx, double dy) {
        // this.x = this.x + dx;
        this.x += dx;
        this.y += dy;
    }
}
