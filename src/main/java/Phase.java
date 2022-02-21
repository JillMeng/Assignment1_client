
import java.util.concurrent.CountDownLatch;

public class Phase {

    private Integer resortID;
    private String seasonID;
    private String dayID;
    private Integer startTime;
    private Integer endTime;
    private Integer numLifts;
    private int numOfRequest;
    private int numThreads;
    private int numSkiers;
    private CountDownLatch phaseCountDown;
    private CountDownLatch mainCountDown;
    private ResultData outputFile;


    public Phase(Integer resortID, String seasonID, String dayID, Integer startTime, Integer endTime, Integer numLifts, int numOfRequest, int numThreads, int numSkiers, CountDownLatch phaseCountDown, CountDownLatch mainCountDown, ResultData outputFile) {
        this.resortID = resortID;
        this.seasonID = seasonID;
        this.dayID = dayID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.numLifts = numLifts;
        this.numOfRequest = numOfRequest;
        this.numThreads = numThreads;
        this.numSkiers = numSkiers;
        this.phaseCountDown = phaseCountDown;
        this.mainCountDown = mainCountDown;
        this.outputFile = outputFile;
    }

    public void run() throws InterruptedException {
        for (int i = 0; i < numThreads; i++) {
            Integer skierIDStart = i * (numSkiers / (numThreads)) + 1;
            Integer SkierIDEnd = (i + 1) * (numSkiers / (numThreads));
            SingleThread thread = new SingleThread(resortID, seasonID, dayID, skierIDStart, SkierIDEnd,
                    startTime, endTime, numLifts, numOfRequest, phaseCountDown, mainCountDown, outputFile);
            new Thread(thread).start();
        }
    }
}