import java.awt.*;
import java.util.ArrayList;

public class CanvasGraph extends Canvas{

    private ArrayList<Point> testPointList = new ArrayList<>(10);
    private ArrayList<Point> pointList = new ArrayList<Point>(10);
    private ArrayList<Point> correctlyEvaluatedList = new ArrayList<Point>(10);
    private ArrayList<Point> wrongEvaluatedList = new ArrayList<Point>(10);
    private int[] pointArray = new int[4];
    private int[] limits = new int[4];
    private int[] functionPoints = new int[2];
    private ArrayList<Integer> linesPoints = new ArrayList<Integer>(2);

    private boolean isLineLoaded = false;
    private double lineSlope;
    private double lineIntercept;
    private Color lineColor = Color.RED;

    public CanvasGraph() {
        super();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        RenderingHints renderQuality = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.addRenderingHints(renderQuality);

        g2.setColor(Color.BLACK);

        // Render HERE
        if (limits.length > 0)
            renderLimits(g2);

        if (!testPointList.isEmpty()) {
            renderTestPointList(g2);
        }

        if (functionPoints.length > 0) {
            renderFunction(g2);
        }

        if (linesPoints.size() > 0) {
            renderLines(g2);
        }
//        if (pointArray.length > 0) {
//            drawBaseLine(g2);
//        }
//        if (!pointList.isEmpty()) {
//            int diameter = 6;
//            for(int i = 0; i < pointList.size(); i++) {
//                Point pt = pointList.get(i);
//                if (Example.desiredOutput(pt) == 1) {
//                    g2.setColor(new Color(30, 215, 96));
//                } else {
//                    g2.setColor(new Color(255, 90, 95));
//                }
//                int posX = (int)((pt.x() + 1.0) * (double)(getWidth() / 2));
//                int posY = (int)((pt.y() + 1.0) * (double)(getHeight() / 2));
//                g2.drawOval(x((posX - diameter/2)), y((posY + diameter/2)), diameter, diameter);
//            }
//        }

//            drawPointList(g2);
//        }
//        if (!correctlyEvaluatedList.isEmpty()) {
//            drawCorrectlyEvaluatedList(g2);
//        }
//        if (!wrongEvaluatedList.isEmpty()) {
//            drawWrongEvaluatedList(g2);
//        }
//        if (isLineLoaded) {
////            System.out.println("Slope=" + lineSlope + "\tIntercept=" + lineIntercept);
//            drawLineFromSlopeAndIntercept(g2);
//        }
    }

    private void renderLimits(Graphics2D g2) {
        g2.setColor(new Color(255, 90, 95, 100));
        g2.setStroke(new BasicStroke(5));
        int xmin = translateX(limits[0]);
        int ymin = resetYCoordinates(translateY(limits[1]));
        int xmax = translateX(limits[2]);
        int ymax = resetYCoordinates(translateY(limits[3]));
        g2.drawLine(xmin, ymax, xmax, ymax);
        g2.drawLine(xmin, ymax, xmin, ymin);
        g2.drawLine(xmin, ymin, xmax, ymin);
        g2.drawLine(xmax, ymin, xmax, ymax);
        g2.setStroke(new BasicStroke(1));
    }

    private void renderFunction(Graphics2D g2) {
        int x1 = translateX(limits[0]);
        int y1 = resetYCoordinates(translateY(functionPoints[0]));
        int x2 = translateX(limits[2]);
        int y2 = resetYCoordinates(translateY(functionPoints[1]));
        g2.drawLine(x1, y1, x2, y2);
    }

    private void renderTestPointList(Graphics2D g2) {
        double diameter = 8.0;
        g2.setColor(Color.BLACK);
        for (int i = 0; i < testPointList.size(); i++) {
            g2.drawOval((translateX((int) (testPointList.get(i).x() - diameter/2))), (resetYCoordinates(translateY((int) (testPointList.get(i).y() + diameter/2)))), (int)diameter, (int)diameter);
        }
    }

    private void renderLines(Graphics2D g2) {
        g2.setColor(Color.GREEN);
        g2.drawLine(translateX(linesPoints.get(0)), resetYCoordinates(translateY(linesPoints.get(1))), translateX(linesPoints.get(2)), resetYCoordinates(translateY(linesPoints.get(3))));
        g2.setColor(Color.BLACK);
    }

    private void drawBaseLine(Graphics2D g2) {
        g2.setStroke(new BasicStroke(5));
        g2.drawLine(pointArray[0], y(pointArray[1]), pointArray[2], y(pointArray[3]));
        g2.setStroke(new BasicStroke(1));
    }

    private void drawLineFromSlopeAndIntercept(Graphics2D g2) {
        int y1 = (int)(lineIntercept * (double)getHeight());
        int y2 = (int)(lineSlope * getWidth() + lineIntercept * (double)getHeight());
        g2.setColor(lineColor);
        g2.drawLine(0, y(y1), getWidth(), y(y2));
    }

    private void drawCorrectlyEvaluatedList(Graphics2D g2) {
        int diameter = 8;
        g2.setColor(Color.GREEN);
        for(int i = 0; i < correctlyEvaluatedList.size(); i++) {
            Point pt = correctlyEvaluatedList.get(i);
            int posX = (int)((pt.x() + 1.0) * (double)(getWidth() / 2));
            int posY = (int)((pt.y() + 1.0) * (double)(getHeight() / 2));
            g2.drawOval(x((posX - diameter/2)), y((posY + diameter/2)), diameter, diameter);
        }
    }

    private void drawWrongEvaluatedList(Graphics2D g2) {
        int diameter = 8;
        g2.setColor(Color.ORANGE);
        for(int i = 0; i < wrongEvaluatedList.size(); i++) {
            Point pt = wrongEvaluatedList.get(i);
            int posX = (int)((pt.x() + 1.0) * (double)(getWidth() / 2));
            int posY = (int)((pt.y() + 1.0) * (double)(getHeight() / 2));
            g2.drawOval(x((posX - diameter/2)), y((posY + diameter/2)), diameter, diameter);
        }
    }

    private void drawPointList(Graphics2D g2) {
        int diameter = 4;
        g2.setColor(Color.BLACK);
        for(int i = 0; i < pointList.size(); i++) {
            Point pt = pointList.get(i);
            int posX = (int)((pt.x() + 1.0) * (double)(getWidth() / 2));
            int posY = (int)((pt.y() + 1.0) * (double)(getHeight() / 2));
            g2.drawOval(x((posX - diameter/2)), y((posY + diameter/2)), diameter, diameter);
        }
    }

    private int translateX(int x) {
        return (x + getWidth() / 2);
    }

    private int translateY(int y) {
        return (y + getHeight() / 2);
    }

    private int x(int _x) {
        return _x;
    }

    private int y(int _y) {
        return getHeight() - _y;
    }

    private int resetYCoordinates(int y) {
        return getHeight() - y;
    }

    public void setLimits(int[] limits) {
        this.limits = limits;
    }

    public void setFunctionPoints(int[] points) {
        functionPoints = points;
    }

    public void setLinesPoints(int[] points) {
        for (int i = 0; i < 4; i++) {
            linesPoints.add(points[i]);
        }
    }

    public void setPointList(Point[] points) {
        for(Point pt:points) {
            pointList.add(pt);
        }
    }

    public void setCorrectlyEvaluatedList(ArrayList<Point> cep) {
        correctlyEvaluatedList = cep;
    }

    public void setWrongEvaluatedList(ArrayList<Point> wep) {
        wrongEvaluatedList = wep;
    }

    // Equazione della retta mx + q
    public void updateSlopeIntercept(double m, double q) {
        isLineLoaded = true;
        lineSlope = m;
        lineIntercept = q;
    }

    public void setPointArray(int[] array) {
        pointArray = array.clone();
    }

    public void changeLineColor(Color color) {
        lineColor = color;
    }

    public void copyTestPoints(Point[] testPoints) {
        for (Point pt:testPoints) {
            testPointList.add(pt);
        }
    }

}
