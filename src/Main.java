import java.util.ArrayList;

public class Main {

    static int numberOfTestPoints = 100;
    static int numberOfTrainPoints = 0;

    static double performacePercentage = 0;
    static double errorPercentage = 0;
    static int wrongAnswer = 0;
    static int givenQuestions = 0;

    public static void main(String[] args) {

        Data singletonico = Data.getInstance();

        // Inizializzazione grafico
        Graph graph = new Graph();

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
        graph.setTestPoints(testPoints);
        graph.setTrainPoints(trainPoints);


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
            System.out.println("x:" + testPoints[i].getX() + " y:" + testPoints[i].getY());
            System.out.println("Output rete -> " + networkResponse);
            System.out.println("Output atteso -> " + desiredResponse);
        }
        Point[] correctPoints = new Point[givenQuestions - wrongAnswer];
        for (int i = 0; i < correctPoints.length; i++) {
            correctPoints[i] = testPoints[pointIndex.get(i)];
        }
        graph.showEvaluatedPoint(correctPoints);



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
        graph.setThreeVector(perceptron.getNormalizedWeights());
    }
}
