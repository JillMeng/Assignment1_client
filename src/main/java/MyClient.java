import io.swagger.client.*;
import io.swagger.client.model.*;
import io.swagger.client.api.ResortsApi;

public class MyClient {

    public static void main(String[] args) {
        if(args.length != 5) {
            System.out.println("Command is not valid");
        }

        ResortsApi apiInstance = new ResortsApi();
        ApiClient client = apiInstance.getApiClient();
        client.setBasePath("http://localhost:8080/Assignment1_server_war_exploded/");

        Integer resortID = 56; // Integer | ID of the resort of interest
        Integer seasonID = 56; // Integer | ID of the resort of interest
        Integer dayID = 56; // Integer | ID of the resort of interest
        try {
            ResortSkiers result = apiInstance.getResortSkiersDay(resortID, seasonID, dayID);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling ResortsApi#getResortSkiersDay");
            e.printStackTrace();
        }
    }
}
