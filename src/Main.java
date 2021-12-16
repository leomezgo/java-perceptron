import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Main {

    static int numberOfTestPoints = 50;
    static int numberOfTrainPoints = 10;

    static double performacePercentage = 0;
    static double errorPercentage = 0;

    public static void main(String[] args) {


        // Inizializzazione grafico
        CanvasGraph canvas = new CanvasGraph();
        JFrame frame = new JFrame();
        frame.setTitle("Grafico");
        frame.setPreferredSize(new Dimension(600, 300));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(canvas);
        frame.setVisible(true);
        frame.pack();

        int xmin = 0;
        int xmax = 400;
        int ymin = 0;
        int ymax = 100;


        Point[] testPoints = new Point[numberOfTestPoints];
        Point[] trainPoints = new Point[numberOfTrainPoints];

        for (int i = 0; i < numberOfTestPoints; i++) {
            double xValue = rangedRandom(xmin, xmax);
            double yValue = rangedRandom(ymin, ymax);
            testPoints[i] = new Point(xValue, yValue);
        }

        for (int i = 0; i < numberOfTrainPoints; i++) {
            double xValue = rangedRandom(xmin, xmax);
            double yValue = rangedRandom(ymin, ymax);
            trainPoints[i] = new Point(xValue, yValue);
        }

        canvas.copyTestPoints(testPoints);
        Perceptron perceptron = new Perceptron(2, 0.1);

        int errors = 0;
        int questions = 0;
        testPerformance(testPoints, perceptron, errors, questions);

        System.out.println("INIZIO APPRENDIMENTO");
        for (Point pt:trainPoints) {
            perceptron.train(pt);
        }
        System.out.println("FINE APPRENDIMENTO");

        errors = 0;
        questions = 0;
        testPerformance(testPoints, perceptron, errors, questions);

        /*
        // Visualizzazione funzione da approssimare
//        double m = 1.8;
//        double q = -0.5;
        double m = 1.1;
        double q = -0.2;
        double q_modificato = q * (double)canvas.getHeight();
        int x1 = 0;
        int y1 = (int)((q + 0.25) * (double)canvas.getHeight());
        int x2 = canvas.getWidth();
        int y2 = (int)(m * x2 + (q + 0.25) * (double)canvas.getHeight());
        int[] pointArray = {x1, y1, x2, y2};
        canvas.setPointArray(pointArray);


        // Inizializzazione rete neurale
        Perceptron perceptron = new Perceptron(2, 0.05);

        // Inizializzazione esempi per test e apprendimento
        Point[] testPoints = new Point[numberOfTestPoints];
        Point[] trainPoints = new Point[numberOfTrainPoints];

        for (int i = 0; i < testPoints.length; i++) {
            testPoints[i] = new Point();
        }
        for (int i = 0; i < trainPoints.length; i++) {
            trainPoints[i] = new Point();
        }

        // Visualizzare punti di test e apprendimento
        canvas.setPointList(testPoints);

        double[] perceptronWeights = perceptron.getWeights();
        double slope = evaluateSlope(perceptronWeights);
        double intercept = evaluateIntercept(perceptronWeights);
        System.out.println("slope=" + slope + "\tintercept=" + intercept);
        canvas.updateSlopeIntercept(slope, intercept);


        // Test della rete senza apprendimento
        ArrayList<Integer> pointIndex = new ArrayList<>(testPoints.length);
        for (int i = 0; i < testPoints.length; i++) {
            int networkResponse = perceptron.response(testPoints[i]);
            int desiredResponse = Example.desiredOutput(testPoints[i]);
            givenQuestions++;
            if (networkResponse != desiredResponse) {
                wrongAnswer++;
            } else {
                pointIndex.add(i);
            }
//            System.out.println("x:" + testPoints[i].getX() + " y:" + testPoints[i].getY());
//            System.out.println("Output rete -> " + networkResponse);
//            System.out.println("Output atteso -> " + desiredResponse);
        }
//        Point[] correctPoints = new Point[givenQuestions - wrongAnswer];
//        for (int i = 0; i < correctPoints.length; i++) {
//            correctPoints[i] = testPoints[pointIndex.get(i)];
//        }



        // Performance della rete
        errorPercentage = (double) wrongAnswer / givenQuestions * 100.0;
        performacePercentage = (double) (givenQuestions - wrongAnswer) / givenQuestions * 100.0;
        System.out.println("Risposte sbagliate -> " + wrongAnswer);
        System.out.println("Domande totali -> " + givenQuestions);
        System.out.println("Performance della rete -> " + String.format("%.2f", performacePercentage) + "%");
        System.out.println("Percentuale errore della rete -> " + String.format("%.2f", errorPercentage) + "%");

        System.out.println();
        System.out.println();
        System.out.println();
        // w1 x + w2 y + w3 bias = 0
        //y = -w1/w2 x - w3/w2

        // Training della rete
        int stepDiApprendimento = 100;
        for (int i = 0; i < stepDiApprendimento; i++) {
            int randomPointSelector = (int)(Math.random() * numberOfTrainPoints);
            perceptron.train(trainPoints[randomPointSelector]);
        }

        perceptronWeights = perceptron.getWeights();
        slope = evaluateSlope(perceptronWeights);
        intercept = evaluateIntercept(perceptronWeights);
        canvas.changeLineColor(Color.RED);
        canvas.updateSlopeIntercept(slope, intercept);

        givenQuestions = 0;
        wrongAnswer = 0;
        System.out.println("--- OUTPUT POST TRAINING ---");
        ArrayList<Point> correctlyEvaluatedPoints = new ArrayList<>(numberOfTestPoints);
        ArrayList<Point> wrongEvaluatedPoints = new ArrayList<>(numberOfTestPoints);
        for (int i = 0; i < testPoints.length; i++) {
            int networkResponse = perceptron.response(testPoints[i]);
            int desiredResponse = Example.desiredOutput(testPoints[i]);
            givenQuestions++;
            if (networkResponse != desiredResponse) {
                wrongEvaluatedPoints.add(testPoints[i]);
                wrongAnswer++;
            } else {
                correctlyEvaluatedPoints.add(testPoints[i]);
                pointIndex.add(i);
            }
        }
        canvas.setCorrectlyEvaluatedList(correctlyEvaluatedPoints);
        canvas.setWrongEvaluatedList(wrongEvaluatedPoints);



        errorPercentage = (double) wrongAnswer / givenQuestions * 100.0;
        performacePercentage = (double) (givenQuestions - wrongAnswer) / givenQuestions * 100.0;
        System.out.println("Risposte sbagliate -> " + wrongAnswer);
        System.out.println("Domande totali -> " + givenQuestions);
        System.out.println("Performance della rete -> " + String.format("%.2f", performacePercentage) + "%");
        System.out.println("Percentuale errore della rete -> " + String.format("%.2f", errorPercentage) + "%");

         */
    }

    private static void testPerformance(Point[] testPoints, Perceptron perceptron, int errors, int questions) {
        for (Point pt:testPoints) {
            questions++;
            int response = perceptron.response(pt);
            if (response != Example.desiredOutput(pt))
                errors++;
        }

        System.out.println("Domande=" + questions);
        System.out.println("Errori=" + errors);
        System.out.println("Performance=" + ((double)(questions - errors)/(double)questions) + "%");
    }

    public static double rangedRandom(double min, double max) {
        double fullRange = Math.abs(min) + Math.abs(max);
        double shiftedRandom = Math.random() * fullRange;
        double rangedRandom = shiftedRandom;
        if (min < 0) {
            rangedRandom = shiftedRandom - fullRange/2;
        }
        return rangedRandom;
    }

    public static double evaluateSlope(double[] weights) {
        return ((-1) * weights[0] / weights[1]);
    }

    public static double evaluateIntercept(double[] weights) {
        return ((-1) * weights[2] / weights[1]);
    }
}
