import java.util.Locale;

public class PointMain {

    static final double END_INDICATOR = -123456;

    public static void main(String[] args) {
        //Point p = new Point(10.0, 20.0);

        /*Locale.setDefault(Locale.of("en"));
        IO.println(p.print());

        Locale.setDefault(Locale.of("hu"));
        IO.println(p.print());

        Locale.setDefault(Locale.of("ru"));
        IO.println(p.print());*/

        /* The first two describe the initial point. What follows is groups of two numbers: each describes a shift.
        Use Double.parseDouble to convert the texts.
        Print every change.
        Example: if the input is 10.0 20.0 1.2 -3.4 10.0 10.0 -5.11 7.77 -4.321 1.234 then the output will be the following.
        At (10.0, 20.0)
        At (30.0, 21.2) at step #1 after being shifted by (20.0, 1.2)
        At (26.6, 31.2) at step #2 after being shifted by (-3.4, 10.0)
        At (36.6, 26.1) at step #3 after being shifted by (10.0, -5.1)
        At (44.4, 21.8) at step #4 after being shifted by (7.8, -4.3)*/

        if (args.length == 0) {
            args = "10.0 20.0 1.2 -3.4 10.0 10.0".split(" ");
        }

        Point p = new Point(Double.parseDouble(args[0]), Double.parseDouble(args[1]));
        IO.println(p.print());
        for (int i = 1; i < args.length - 1; i+=2) {
            // Point p = new Point(args, i);
            double dx = Double.parseDouble(args[i]);
            double dy = Double.parseDouble(args[i+1]);
            //IO.println(dx);
            //IO.println(dy);
            Point offset = new Point(dx, dy);
            p.shift(dx, dy);
            IO.println(p.print("At step #%d", offset).formatted(i/2 + 1));
        }
    }
}