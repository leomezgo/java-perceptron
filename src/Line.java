public class Line {
    private Point startPoint;
    private Point endPoint;

    Line(Point startPoint, Point endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public Point getStartPoint() {
        return startPoint;
    }
    public Point getEndPoint() {
        return endPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }
    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }
}
