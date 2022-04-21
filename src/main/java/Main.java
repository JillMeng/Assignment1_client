import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static final int MAX_NUM_THREADS = 1024;
    private static final int MAX_NUM_SKIERS = 100000;
    private static final int MAX_NUM_LIFTS = 60;
    private static final int MIN_NUM_LIFTS = 5;
    private static final int MAX_NUM_RUNS = 20 * MAX_NUM_SKIERS;

    //set resort data
    private static final Integer resortID = 56;
    private static final String seasonID = "2021";
    private static final String dayID = "56";

    public static void main(String[] args) throws InterruptedException {
//        Scanner sc= new Scanner(System.in);
//        System.out.println("Please enter command line as: \n" +
//                "numThreads < 1024, " +
//                "numSkiers < 100000, " +
//                "5 < numLifts < 60, " +
//                "numRuns < 20/Skier, " +
//                "port");
//        String inputStr = sc.next();
//        String[] strings = inputStr.split(",");
//
//        if(strings.length != 5 || Integer.parseInt(strings[0]) > MAX_NUM_THREADS
//                || Integer.parseInt(strings[1]) > MAX_NUM_SKIERS
//                || Integer.parseInt(strings[2]) > MAX_NUM_LIFTS
//                || Integer.parseInt(strings[2]) < MIN_NUM_LIFTS
//                || Integer.parseInt(strings[3]) > MAX_NUM_RUNS) {
//            System.out.println("Invalid commandLine, please enter: \n" +
//                    "numThreads < 1024, " +
//                    "numSkiers < 100000, " +
//                    "5 < numLifts < 60, " +
//                    "numRuns < 20/Skier, " +
//                    "port");
//            return;
//        }
//        CommandLine commandLine = new CommandLine(strings);
//        int NUM_THREADS = commandLine.getNumThreads();
//        int numRuns = commandLine.getNumRuns();
//        int numSkiers = commandLine.getNumSkiers();
//        int numLifts = commandLine.getNumLifts();

        int NUM_THREADS = 256;
        int numRuns = 10;
        int numSkiers = 20000;
        int numLifts = 40;

        AtomicInteger successfulReq = new AtomicInteger(0);
        AtomicInteger failedReq = new AtomicInteger(0);
        AtomicInteger totalReq = new AtomicInteger(0);
        List<String[]> outputData = Collections.synchronizedList(new ArrayList());

        ResultData outputFile = new ResultData(successfulReq, failedReq, totalReq, outputData);

        //Initialize phase1
        int numOfThreads_phase1 = NUM_THREADS/4;
        int numOfRequest_phase1 = (int) (numRuns * 0.2) * (numSkiers/numOfThreads_phase1);
        CountDownLatch phase1_latch = new CountDownLatch((int) (numOfThreads_phase1 * 0.2));
        //Initialize phase2
        int numOfThreads_phase2 = NUM_THREADS;
        int numOfRequest_phase2 = (int)(numRuns * 0.6) * (numSkiers/numOfThreads_phase2);
        CountDownLatch phase2_latch = new CountDownLatch((int) (numOfThreads_phase2 * 0.2));
        //Initialize phase3
        int numOfThreads_phase3 = (int) 0.1 * NUM_THREADS;
        int numOfRequest_phase3 = (int)(0.1 * numRuns);
        CountDownLatch phase3_latch = new CountDownLatch(numOfRequest_phase3);
        //overall countdown latch
        CountDownLatch  mainLatch = new CountDownLatch(numOfThreads_phase1+numOfThreads_phase2+numOfThreads_phase3);

        Phase phase1 = new Phase(resortID, seasonID, dayID, 1, 90, numLifts, numOfRequest_phase1,
                numOfThreads_phase1, numSkiers, phase1_latch,mainLatch, outputFile);
        Phase phase2 = new Phase(resortID, seasonID, dayID, 91, 360, numLifts, numOfRequest_phase2,
                numOfThreads_phase2, numSkiers, phase2_latch,mainLatch, outputFile);
        Phase phase3 = new Phase(resortID, seasonID, dayID, 361, 420, numLifts, numOfRequest_phase3,
                numOfThreads_phase3, numSkiers, phase3_latch,mainLatch, outputFile);

        long beforeRun = System.currentTimeMillis();
        phase1.run();
        phase1_latch.await();
        phase2.run();
        phase2_latch.await();
        phase3.run();
        mainLatch.await();
        long afterRun = System.currentTimeMillis();
        //close connection
        long wallTime = (afterRun - beforeRun)/1000;

        //Print Part One
        System.out.println("**********Output Part One**************");
        System.out.println("Number of successful requests sent is: " + outputFile.getSuccessfulReq().get());
        System.out.println("Number of unsuccessful requests sent is: " + outputFile.getFailedReq().get());
        System.out.println("Total run-time/wall-time is: " + wallTime + "s");
        System.out.println("Total throughput in requests per second: " + outputFile.getTotalReq().get()/wallTime);
        //write to csv file
        outputFile.writeToCsvFile();

        //Print Part Two
//        DataAnalysis dataAnalysis = new DataAnalysis(outputFile, wallTime);
//        System.out.println("**********Output Part Two**************");
//        System.out.println("Mean response is: " + dataAnalysis.meanRespTime());
//        System.out.println("Median response is: " + dataAnalysis.medianRespTime());
//        System.out.println("Throughput (requests/second) is: " + dataAnalysis.throughput());
//        System.out.println("p99 (99th percentile) response time is: " + dataAnalysis.p99RespTime());
//        System.out.println("Min response time is: " + dataAnalysis.minRespTime());
//        System.out.println("Max response time is: " + dataAnalysis.maxRespTime());
    }
}
