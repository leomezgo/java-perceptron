import java.util.ArrayList;
import java.util.List;

public class Store {
    private static Store _instance = null;
    private Point[] workingArea;
    private final List<Point> testPoints = new ArrayList<>();
    private final List<Point> trainPoints = new ArrayList<>();
    private Point[] linearDesiredOutput;
    private Point[] linearOutputPreTraining;
    private Point[] linearOutput;

    static public Store getInstance() {
        if (_instance == null) {
            _instance = new Store();
        }
        return _instance;
    }

    protected Store() {
    }

    public void setWorkingArea(Point[] workingArea) {
        this.workingArea = workingArea.clone();
    }
    public Point[] getWorkingArea() {
        return workingArea;
    }

    public void addPointToTestPoints(Point p) {
        testPoints.add(p);
    }
    public List<Point> getTestPoints() {
        return testPoints;
    }
    public Point getTestPointByIndex(int index) {
        return testPoints.get(index);
    }

    public void addPointToTrainPoints(Point p) {
        trainPoints.add(p);
    }
    public List<Point> getTrainPoints() {
        return trainPoints;
    }
    public Point getTrainPointByIndex(int index) {
        return trainPoints.get(index);
    }

    public void setLinearDesiredOutput(Point[] linearPoints){
        this.linearDesiredOutput = linearPoints.clone();
    }
    public Point[] getLinearDesiredOutput(){
        return linearDesiredOutput;
    }

    public void setLinearOutputPreTraining(Point[] linearPoints) {
        this.linearOutputPreTraining = linearPoints.clone();
    }
    public Point[] getLinearOutputPreTraining() {
        return linearOutputPreTraining;
    }

    public void setLinearOutput(Point[] linearPoints) {
        this.linearOutput = linearPoints.clone();
    }
    public Point[] getLinearOutput() {
        return linearOutput;
    }
}
