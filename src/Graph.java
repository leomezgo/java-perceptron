import javax.swing.*;
import java.awt.*;

public class Graph extends JFrame {

    private int windowSize = 500;

    private Point[] testPoints;
    private Point[] trainPoints;
    private Point[] correctEvaluatedPoints;

    private Color testPointColor = new Color(250, 37, 61);
    private Color trainPointColor = new Color(49, 165, 242);
    private Color correctPointColor = new Color(30, 208, 93);

    private double[] weightsVector;

    private JComponent canvas = new JComponent() {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.addRenderingHints(qualityHints);

            g2.setColor(Color.BLACK);
            int ovalDiameter = 6;
//            g2.drawOval(getRealX(getWidth()/2 - ovalDiameter / 2), getRealY(getRealHeight()/2 + ovalDiameter/2), ovalDiameter, ovalDiameter);

            drawCenteredOval(g2, getWidth()/2, getRealHeight()/2, ovalDiameter);
            // Drawing test points
            drawTestPoints(g2);

//            if (trainPoints != null) {
//                drawEmptyCircle(g2, trainPoints, trainPointColor);
//            }
            if (correctEvaluatedPoints != null && correctEvaluatedPoints.length > 0) {
                drawFilledCircle(g2, correctEvaluatedPoints, correctPointColor);
            }
            if(weightsVector != null) {
                drawLineFromVector(g2);
            }
            g2.setColor(Color.black);

            g2.drawLine(0,getRealY(getRealHeight()/2), getWidth(), getRealY(getRealHeight()/2));
            g2.drawLine(getWidth()/2,getRealY(0), getWidth()/2, getRealY(getRealHeight()));
        }

        private void drawTestPoints(Graphics2D g2) {
            if (testPoints != null) {
                drawEmptyCircle(g2, testPoints, testPointColor);
            }
        }

        private void drawCenteredOval(Graphics2D g2, int x, int y, int diameter) {
            g2.drawOval(getRealX(x - diameter / 2), getRealY(y + diameter / 2), diameter, diameter);
        }

        private void drawLineFromVector(Graphics2D g2) {
            //y = -w1/w2 x - w3/w2
            double m = -1 * weightsVector[0] / weightsVector[1];
            double q = -1 * weightsVector[2] / weightsVector[1];

//            double Y = m * getWidth() + q;
            double y1 = (-1) * m * getRealHeight() + q;
            double y2 = m * getRealHeight() + q;

            g2.setColor(Color.BLACK);
            g2.drawLine(-(1) * getWidth(), (int)y1, getWidth(), (int)y2);
//            g2.drawLine(getWidth() / 2, (int)((q) + getRealHeight() / 2), getWidth(), (int)y2);
        }

        private void drawEmptyCircle(Graphics2D g2, Point[] points, Color color) {
            if (points.length > 0) {
                for (Point point:points) {
                    g2.setColor(color);
                    int _x = (int)Math.round(((point.getX() + 1.0) / 2.0) * getWidth());
                    int _y = (int)Math.round(((point.getY() + 1.0) / 2.0) * getRealHeight());
                    drawCenteredOval(g2, getRealX(_x), getRealY(_y), 6);
                }
            }
        }

        private void drawFilledCircle(Graphics2D g2, Point[] points, Color color) {
            if (points.length > 0) {
                for (Point point:points) {
                    g2.setColor(color);
                    int _x = (int)Math.round(((point.getX() + 1.0) / 2.0) * getWidth());
                    int _y = (int)Math.round(((point.getY() + 1.0) / 2.0) * getRealHeight());
                    drawCenteredOval(g2, getRealX(_x), getRealY(_y), 10);
                }
            }
        }
    };

    public Graph() {
        super("Graph");
        setPreferredSize(new Dimension(windowSize,windowSize));
        setVisible(true);
        canvas.setPreferredSize(new Dimension(windowSize, windowSize));
        add(canvas);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private int getRealX(int x) {
        return (x);
    }

    private int getRealY(int y) {
        return (getRealHeight() - y);
    }

    private int getRealHeight() {
        return (getHeight() + 28);
    }

    public void setTestPoints(Point[] pts) {
        testPoints = pts;
        canvas.repaint();
    }

    public void setTrainPoints(Point[] pts) {
        trainPoints = pts;
        canvas.repaint();
    }

    public void showEvaluatedPoint(Point[] pts) {
        correctEvaluatedPoints = pts;
        canvas.repaint();
    }

    public void setThreeVector(double[] weights) {
        weightsVector = weights;
        canvas.repaint();
    }

}
