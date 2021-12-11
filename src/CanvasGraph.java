import java.awt.*;
import java.util.ArrayList;

public class CanvasGraph extends Canvas {

    private ArrayList<Point> pointList = new ArrayList<Point>(10);
    private ArrayList<Point> correctlyEvaluatedList = new ArrayList<Point>(10);

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
        g2.drawLine(0,y(0),getWidth(),y(getHeight()));

        // Render HERE
        if (!pointList.isEmpty()) {
            drawPointList(g2);
        }
        if (!correctlyEvaluatedList.isEmpty()) {
            drawCorrectlyEvaluatedList(g2);
        }
        if (isLineLoaded) {
            System.out.println("Slope=" + lineSlope + "\tIntercept=" + lineIntercept);
            drawLineFromSlopeAndIntercept(g2);
        }
    }

    private void drawLineFromSlopeAndIntercept(Graphics2D g2) {
        int y1 = (int)lineIntercept;
        int y2;
        y2 = (int)(lineSlope * getWidth() + lineIntercept);
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
        repaint();
    }

    // Equazione della retta mx + q
    public void updateSlopeIntercept(double m, double q) {
        isLineLoaded = true;
        lineSlope = m;
        lineIntercept = q;
        repaint();
    }

    public void changeLineColor(Color color) {
        lineColor = color;
    }
}
