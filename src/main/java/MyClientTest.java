import io.swagger.client.*;
import io.swagger.client.api.SkiersApi;
import io.swagger.client.model.*;

public class MyClientTest implements Runnable{

    @Override
    public void run() {
        SkiersApi apiInstance = new SkiersApi();
        ApiClient client = apiInstance.getApiClient();
        client.setBasePath("http://54.218.248.15:8080/Assignment1_server_war/");

        Integer resortID = 12; // Integer | ID of the resort of interest
        String seasonID = "2019"; // Integer | ID of the resort of interest
        String dayID = "1"; // Integer | ID of the resort of interest
        Integer skierID = 56; // Integer | ID of the skier of interest
        LiftRide body = new LiftRide();

        //send 10000
        long startTime = System.currentTimeMillis();
        for(int i = 0; i<10000; i++) {
            long beforeSend = 0;
            long getResponse = 0;
            try {
                beforeSend = System.currentTimeMillis();
                ApiResponse response = apiInstance.writeNewLiftRideWithHttpInfo(body, resortID, seasonID, dayID, skierID);
                getResponse = System.currentTimeMillis();
                System.out.println(response.getStatusCode());
            } catch (ApiException e) {
                System.err.println("Exception when calling SkiersApi#writeNewLiftRideWithHttpInfo");
                e.printStackTrace();
            }
            long latency = getResponse - beforeSend;
        }
        long endTime = System.currentTimeMillis();
        long wallTime = endTime - startTime;
        long throughput = 10000/wallTime;
        System.out.println("The expected throughput is: " + throughput);
    }

    public static void main(String[] args) {
        MyClientTest myClientTest = new MyClientTest();
        myClientTest.run();

    }
}
