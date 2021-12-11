import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.util.ArrayList;

public class CanvasGraph extends Canvas {

    private ArrayList<Point> pointList = new ArrayList<Point>(10);
    private ArrayList<Point> correctlyEvaluatedList = new ArrayList<Point>(10);
    private ArrayList<Point> wrongEvaluatedList = new ArrayList<Point>(10);
    private int[] pointArray = new int[4];

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
        if (pointArray.length > 0) {
            drawBaseLine(g2);
        }
        if (!pointList.isEmpty()) {
            int diameter = 6;
            for(int i = 0; i < pointList.size(); i++) {
                Point pt = pointList.get(i);
                if (Example.desiredOutput(pt) == 1) {
                    g2.setColor(new Color(30, 215, 96));
                } else {
                    g2.setColor(new Color(255, 90, 95));
                }
                int posX = (int)((pt.getX() + 1.0) * (double)(getWidth() / 2));
                int posY = (int)((pt.getY() + 1.0) * (double)(getHeight() / 2));
                g2.drawOval(x((posX - diameter/2)), y((posY + diameter/2)), diameter, diameter);
            }
        }

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
            int posX = (int)((pt.getX() + 1.0) * (double)(getWidth() / 2));
            int posY = (int)((pt.getY() + 1.0) * (double)(getHeight() / 2));
            g2.drawOval(x((posX - diameter/2)), y((posY + diameter/2)), diameter, diameter);
        }
    }

    private void drawWrongEvaluatedList(Graphics2D g2) {
        int diameter = 8;
        g2.setColor(Color.ORANGE);
        for(int i = 0; i < wrongEvaluatedList.size(); i++) {
            Point pt = wrongEvaluatedList.get(i);
            int posX = (int)((pt.getX() + 1.0) * (double)(getWidth() / 2));
            int posY = (int)((pt.getY() + 1.0) * (double)(getHeight() / 2));
            g2.drawOval(x((posX - diameter/2)), y((posY + diameter/2)), diameter, diameter);
        }
    }

    private void drawPointList(Graphics2D g2) {
        int diameter = 4;
        g2.setColor(Color.BLACK);
        for(int i = 0; i < pointList.size(); i++) {
            Point pt = pointList.get(i);
            int posX = (int)((pt.getX() + 1.0) * (double)(getWidth() / 2));
            int posY = (int)((pt.getY() + 1.0) * (double)(getHeight() / 2));
            g2.drawOval(x((posX - diameter/2)), y((posY + diameter/2)), diameter, diameter);
        }
    }

    private int x(int _x) {
        return _x;
    }

    private int y(int _y) {
        return getHeight() - _y;
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
}
