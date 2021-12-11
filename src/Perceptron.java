import java.awt.*;

public class Perceptron {

    private double[] weights;
    private double epsilon;

    public Perceptron(int numberOfInputs, double _constant) {
        weights = new double[numberOfInputs + 1];
        epsilon = _constant;
        randomizeWeights();
    }

    private double random(double min, double max) {
        double fullRange = Math.abs(min) + Math.abs(max);
        double shiftedRandom = Math.random() * fullRange;
        double rangedRandom = shiftedRandom - fullRange/2;
        return rangedRandom;
    }

    private void randomizeWeights() {
        for (int i = 0; i < weights.length; i++) {
            weights[i] = random(-1,1);
        }
    }

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

    public double[] getWeights() {
        return this.weights;
    }

}
