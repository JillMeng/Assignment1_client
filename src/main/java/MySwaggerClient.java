import io.swagger.client.*;
import io.swagger.client.api.SkiersApi;
import io.swagger.client.auth.*;
import io.swagger.client.model.*;
import io.swagger.client.api.ResortsApi;

public class MySwaggerClient {

    public static void main(String[] args) {
        SkiersApi apiInstance = new SkiersApi();
        ApiClient client = apiInstance.getApiClient();
        client.setBasePath("http://localhost:8080/Assignment1_server_war_exploded/");

        Integer resortID = 12; // Integer | ID of the resort of interest
        String seasonID = "2019"; // Integer | ID of the resort of interest
        String dayID = "1"; // Integer | ID of the resort of interest
        Integer skierID = 56; // Integer | ID of the skier of interest
        LiftRide body = new LiftRide();

        try {
            ApiResponse response = apiInstance.writeNewLiftRideWithHttpInfo(body, resortID, seasonID, dayID, skierID);
            System.out.println(response.getStatusCode());
        } catch (ApiException e) {
            System.err.println("Exception when calling SkiersApi#writeNewLiftRideWithHttpInfo");
            e.printStackTrace();
        }
    }
}
