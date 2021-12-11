import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Main {

    static int numberOfTestPoints = 200;
    static int numberOfTrainPoints = 100;

    static double performacePercentage = 0;
    static double errorPercentage = 0;
    static int wrongAnswer = 0;
    static int givenQuestions = 0;

    public static void main(String[] args) {

        Data singletonico = Data.getInstance();

        // Inizializzazione grafico
        CanvasGraph canvas = new CanvasGraph();
        JFrame frame = new JFrame();
        frame.setTitle("Grafico");
        frame.setPreferredSize(new Dimension(500, 500));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(canvas);
        frame.setVisible(true);
        frame.pack();

        // Inizializzazione rete neurale
        Perceptron perceptron = new Perceptron(2);

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
        System.out.println("Vettore pesi");
        System.out.println("w1: " + perceptron.getNormalizedWeights()[0]);
        System.out.println("w2: " + perceptron.getNormalizedWeights()[1]);
        System.out.println("w3: " + perceptron.getNormalizedWeights()[2]);
        // w1 x + w2 y + w3 bias = 0
        //y = -w1/w2 x - w3/w2

        // Training della rete
        int stepDiApprendimento = 50;
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
        for (int i = 0; i < testPoints.length; i++) {
            int networkResponse = perceptron.response(testPoints[i]);
            int desiredResponse = Example.desiredOutput(testPoints[i]);
            givenQuestions++;
            if (networkResponse != desiredResponse) {
                wrongAnswer++;
            } else {
                correctlyEvaluatedPoints.add(testPoints[i]);
                pointIndex.add(i);
            }
        }
        canvas.setCorrectlyEvaluatedList(correctlyEvaluatedPoints);



        errorPercentage = (double) wrongAnswer / givenQuestions * 100.0;
        performacePercentage = (double) (givenQuestions - wrongAnswer) / givenQuestions * 100.0;
        System.out.println("Risposte sbagliate -> " + wrongAnswer);
        System.out.println("Domande totali -> " + givenQuestions);
        System.out.println("Performance della rete -> " + String.format("%.2f", performacePercentage) + "%");
        System.out.println("Percentuale errore della rete -> " + String.format("%.2f", errorPercentage) + "%");
    }

    public static double evaluateSlope(double[] weights) {
        return ((-1) * weights[0] / weights[1]);
    }

    public static double evaluateIntercept(double[] weights) {
        return ((-1) * weights[2] / weights[1]);
    }
}
