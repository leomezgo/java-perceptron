import java.awt.*;

public class Perceptron {

    private double[] weights;
    private double epsilon= 0.1;

    public Perceptron(int numberOfInputs) {
        weights = new double[numberOfInputs + 1];
        randomizeWeights();
    }

    private void randomizeWeights() {
        for (int i = 0; i < weights.length; i++) {
            weights[i] = Math.random() * 2.0 - 1.0;
        }
    }

    // Gestire errori per lunghezze non congrue al calcolo
    private double dotProduct(double[] v1, double[] v2) {
        double product = 0;

        for (int i = 0; i < v1.length; i++) {
            product += v1[i] * v2[i];
        }

        return product;
    }

    public int response(Point point) {
        double[] input = { point.getX(), point.getY(), 1.0};
        double dotProductResult = dotProduct(input, weights);
        if (dotProductResult > 0) {
            return 1;
        }
        return -1;
    }

    public void train(Point point) {
        double[] input = { point.getX(), point.getY(), 1.0};
        if (response(point) != Example.desiredOutput(point)) {
            for (int i = 0; i < weights.length; i++) {
                weights[i] += 2 * epsilon * Example.desiredOutput(point) * input[i];
            }
        }
    }

    public void showWeights() {
        for (int i = 0; i < weights.length; i++) {
            System.out.println(i + " -> " + weights[i]);
        }
    }

    public double[] getNormalizedWeights() {
        double norm = 0;
        for (int i = 0; i < weights.length; i++) {
            norm += weights[i] * weights[i];
        }
        norm = Math.sqrt(norm);
        double[] normalizedWeights = {weights[0]/norm, weights[1]/norm, weights[2]/norm};
        return normalizedWeights;
    }

    public double[] getWeights() {
        return this.weights;
    }

    public void showPerceptronLineSeparation(CanvasGraph canvas) {
        System.out.print("Pesi non normalizzati: ");
        for (int i = 0; i < weights.length; i++) {
            System.out.print("w"+ i + "=" + weights[i] + "; ");
        }
        Graphics2D g2 = (Graphics2D) canvas.getGraphics();
        g2.drawLine(0, canvas.getHeight(), canvas.getWidth(), 0);
        System.out.println();
    }

}
