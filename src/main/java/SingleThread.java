import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.ApiResponse;
import io.swagger.client.api.SkiersApi;
import io.swagger.client.model.LiftRide;

import java.sql.Timestamp;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

public class SingleThread implements Runnable {

    private static final String BASE_PATH = "http://54.189.154.233:8080/Assignment1_server_war/";
//    private static final String BASE_PATH = "http://localhost:8080/Assignment1_server_war_exploded/";

    private Integer resortID;
    private String seasonID;
    private String dayID;
    private Integer skierIDStart;
    private Integer skierIDEnd;
    private Integer startTime;
    private Integer endTime;
    private Integer numLifts;
    private int numOfRequest;
    private CountDownLatch phaseCountDown;
    private CountDownLatch mainCountDown;
    private ResultData outputFile;

    public SingleThread(Integer resortID, String seasonID, String dayID, Integer skierIDStart, Integer skierIDEnd, Integer startTime, Integer endTime, Integer numLifts, int numOfRequest, CountDownLatch phaseCountDown, CountDownLatch mainCountDown, ResultData outputFile) {
        this.resortID = resortID;
        this.seasonID = seasonID;
        this.dayID = dayID;
        this.skierIDStart = skierIDStart;
        this.skierIDEnd = skierIDEnd;
        this.startTime = startTime;
        this.endTime = endTime;
        this.numLifts = numLifts;
        this.numOfRequest = numOfRequest;
        this.phaseCountDown = phaseCountDown;
        this.mainCountDown = mainCountDown;
        this.outputFile = outputFile;
    }

    public void run() {
        SkiersApi apiInstance = new SkiersApi();
        ApiClient client = apiInstance.getApiClient();
        client.setBasePath(BASE_PATH);

        for (int i = 0; i < numOfRequest; i++) {
            //generate random parameters
            Integer skierID = ThreadLocalRandom.current().nextInt(skierIDStart, skierIDEnd);
            Integer time = ThreadLocalRandom.current().nextInt(startTime, endTime);
            Integer liftID = ThreadLocalRandom.current().nextInt(1, numLifts);
            Integer waitTime = ThreadLocalRandom.current().nextInt(0, 10);

            LiftRide body = new LiftRide();
            body.time(time);
            body.liftID(liftID);
            body.waitTime(waitTime);

            //if response code is not200/201 try same request data up to 5 times
            int badRequest = 0;
            long timeStart = 0;
            long timeEnd = 0;
            long latency = 0;
            int respondCode = 0;
            while(badRequest < 5) {
                //take a timestamp before sending request
                timeStart = System.currentTimeMillis();
                try {
                    ApiResponse response = apiInstance.writeNewLiftRideWithHttpInfo(body, resortID, seasonID, dayID, skierID);
                    //after the response is received, take a timestamp
                    timeEnd = System.currentTimeMillis();
                    outputFile.addTotalReq(1);
                    respondCode = response.getStatusCode();
                    //successful requests
                    if(respondCode == 200 || respondCode == 201) {
                        outputFile.addSuccessfulReq(1);
                        System.out.println(respondCode);
                        break;
                    } else {
                        badRequest++;
                        System.out.println(respondCode);
                    }
                } catch (ApiException e) {
                    badRequest++;
                    System.err.println("Exception when calling SkiersApi#writeNewLiftRideWithHttpInfo");
                    respondCode = e.getCode();
                    e.printStackTrace();
                }
            }
            //if request failed 5 times, count as a failed request
            if(badRequest == 5) {
                outputFile.addFailedReq(1);
            }
            //single request latency
            latency = timeEnd - timeStart;

            //Write out a data to ResultData -> {start time, request type (ie POST), latency, response code} to csv
            Timestamp startTime = new Timestamp(timeStart);
            String[] dataLine = {startTime.toString(), "POST",
                    Long.toString(latency), Integer.toString(respondCode)};
            outputFile.addDataLine(dataLine);
        }
        phaseCountDown.countDown();
        mainCountDown.countDown();
    }
}