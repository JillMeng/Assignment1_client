import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.ApiResponse;
import io.swagger.client.api.SkiersApi;
import io.swagger.client.model.LiftRide;

import java.util.ArrayList;
import java.util.List;

public class MyClientTest implements Runnable {

    private final long NUM_REQUEST = 10000;

    @Override
    public void run() {
        SkiersApi apiInstance = new SkiersApi();
        ApiClient client = apiInstance.getApiClient();
        client.setBasePath("http://54.189.154.233:8080/Assignment1_server_war/");
//        client.setBasePath("http://localhost:8080/Assignment1_server_war_exploded/");

        Integer resortID = 12; // Integer | ID of the resort of interest
        String seasonID = "2019"; // Integer | ID of the resort of interest
        String dayID = "1"; // Integer | ID of the resort of interest
        Integer skierID = 56; // Integer | ID of the skier of interest
        LiftRide body = new LiftRide();

        //send 10000 requests to server
        List<Long> latencyLst = new ArrayList<>();
        long sumLatency = 0;
        for (int i = 0; i < NUM_REQUEST; i++) {
            long startTime = System.currentTimeMillis();
            try {
                ApiResponse response = apiInstance.writeNewLiftRideWithHttpInfo(body, resortID, seasonID, dayID, skierID);
                System.out.println(response.getStatusCode());
            } catch (ApiException e) {
                System.err.println("Exception when calling SkiersApi#writeNewLiftRideWithHttpInfo");
                e.printStackTrace();
            }
            long endTime = System.currentTimeMillis();
            long aRequestLatency = endTime - startTime;
            sumLatency += aRequestLatency;
        }
        //cal average latency
        long meanLatency = sumLatency / NUM_REQUEST;

        System.out.println("The meanLatency is: " + meanLatency);
    }

    public static void main(String[] args) {
        MyClientTest myClientTest = new MyClientTest();
        myClientTest.run();
    }
}
