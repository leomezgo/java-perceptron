public class Point {
    private final double x;
    private final double y;

    public Point() {
        x = Math.random() * 2.0 - 1.0;
        y = Math.random() * 2.0 - 1.0;
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
