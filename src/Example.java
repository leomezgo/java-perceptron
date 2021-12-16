public class Example {
    static int desiredOutput(Point pt) {
        if (pt.y() > f(pt.x())) {
            return 1;
        }
        return -1;
    }

    static double f(double x) {
//        double m = 1.8;
//        double q = -0.5;
        double m = 1.1;
        double q = -0.2;
        return (m * x + q);
    }
}
