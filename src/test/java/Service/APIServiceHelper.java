package Service;

import LoggerUtility.LoggerUtility;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.internal.RequestSpecificationImpl;
import io.restassured.specification.RequestSpecification;
import com.fasterxml.jackson.databind.ObjectMapper;
public class APIServiceHelper {
    //clasa contine metode care afiseaza informatii despre request si response

    public static void requestLogs(RequestSpecification requestSpecification, String path, String methodType){
        LoggerUtility.info("====== Request Info ======");
        LoggerUtility.info(getRequestUrl(path));
        LoggerUtility.info(getRequestMethod(methodType));
        LoggerUtility.info(getRequestBody(requestSpecification));
    }

    private static String getRequestUrl(String path){
        return "Request URL: " + "https://demoqa.com" + path;

    }
    private static String getRequestMethod(String method){
        return "Request Method: " + method;

    }
    private static String getRequestBody(RequestSpecification requestSpecification){
        String requestBody="Request Body: \n";
        Object object = ((RequestSpecificationImpl)requestSpecification).getBody();
        if (object != null)
        {
            ObjectMapper mapper = new ObjectMapper();
            try {
                requestBody=requestBody+mapper.readTree(object.toString()).toPrettyString();
            } catch (JsonProcessingException e) {

            }

        }
        return requestBody;
    }

}
