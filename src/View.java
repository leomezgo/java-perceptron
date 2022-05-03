import javax.swing.*;
import java.awt.*;

public class View extends Canvas {
    private JFrame viewFrame;
    private int viewWidth = 900;
    private int viewHeight = 400;
    private Store store;

    public View() {
        viewFrame = new JFrame();
        store = Store.getInstance();
        viewFrame.setTitle("View");
        viewFrame.setPreferredSize(new Dimension(viewWidth, viewHeight));
        viewFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        viewFrame.add(this);

        viewFrame.setVisible(true);
        viewFrame.pack();
    }

    private void drawPointOnCanvas(Point p, Graphics2D g2) {
        final double pointDiameter = 5;
        final Point normalizedPoint = cartesianToPixelCoordinate(p);
        final int x = (int)(normalizedPoint.getX() - pointDiameter / 2);
        final int y = (int)(normalizedPoint.getY() - pointDiameter / 2);
        g2.drawOval(x, y, (int)pointDiameter, (int)pointDiameter);
    }

    private void drawLineOnCanvas(Point p1, Point p2, Graphics2D g2) {
        final Point normalizedP1 = cartesianToPixelCoordinate(p1);
        final Point normalizedP2 = cartesianToPixelCoordinate(p2);
        g2.drawLine((int) normalizedP1.getX(), (int) normalizedP1.getY(), (int) normalizedP2.getX(), (int) normalizedP2.getY());
    }

    private Point cartesianToPixelCoordinate(Point p) {
        final double xPixelCoordinate = p.getX() + getWidth() / 2;
        final double yPixelCoordinate = getHeight() / 2 - p.getY();
        return new Point(xPixelCoordinate, yPixelCoordinate);
    }

    public void update() {
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        RenderingHints renderQuality = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.addRenderingHints(renderQuality);

        // Rendering Limits Area
        g2.setColor(new Color(0, 0, 0, 30));
        g2.setStroke(new BasicStroke(5));
        final Point[] limits = store.getWorkingArea();
        drawLineOnCanvas(new Point(limits[0].getX(), limits[1].getY()), new Point(limits[0].getX(), limits[0].getY()), g2);
        drawLineOnCanvas(new Point(limits[0].getX(), limits[0].getY()), new Point(limits[1].getX(), limits[0].getY()), g2);
        drawLineOnCanvas(new Point(limits[1].getX(), limits[0].getY()), new Point(limits[1].getX(), limits[1].getY()), g2);
        drawLineOnCanvas(new Point(limits[0].getX(), limits[1].getY()), new Point(limits[1].getX(), limits[1].getY()), g2);

        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(1));
        store.getTestPoints().forEach(p -> drawPointOnCanvas(p, g2));

        g2.setColor(new Color(30, 215, 96));
        drawLineOnCanvas(store.getLinearDesiredOutput()[0], store.getLinearDesiredOutput()[1], g2);

        g2.setColor(new Color(230, 115, 96));
        g2.setStroke(new BasicStroke(5));
        drawLineOnCanvas(store.getLinearOutputPreTraining()[0], store.getLinearOutputPreTraining()[1], g2);

        g2.setStroke(new BasicStroke(1));
        // Draw training points
        g2.setColor(new Color(70, 145, 156));
        store.getTrainPoints().forEach(p -> drawPointOnCanvas(p, g2));

        g2.setColor(new Color(160, 200, 43));
        drawLineOnCanvas(store.getLinearOutput()[0], store.getLinearOutput()[1], g2);
    }
}
