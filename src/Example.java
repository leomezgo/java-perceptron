public class Example {
    static int desiredOutput(Point pt) {
        if (pt.getY() > f(pt.getX()))
            return 1;
//        if (pt.y() > f(pt.x())) {
//            return 1;
//        }
        return -1;
    }

    static double f(double x) {
//        double m = 1.8;
//        double q = -0.5;
        double m = 0.2;
        double q = 100.0;
        return (m * x + q);
    }
}
