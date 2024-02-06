package Service;

import Rest.RestRequest;
import Rest.RestRequestType;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CommonAPIService {
    //serviciu comun ex. "Account" , "B
    //aceasta clasa contine metode pentru tipuri de requesturi cu diferiti parametrii
    //unele requesturi pot sa  aiba body, altele pot sa aiba body + auth etc

    public Response post(Object body, String URL){
        //un post ce contine un body + url
        //pun body-ul
                RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.body(body);

        APIServiceHelper.requestLogs(requestSpecification, URL, RestRequestType.REQUEST_POST ); //pentru logguri

        //trebuie facut response-ul
        Response response = performRequest(RestRequestType.REQUEST_POST, requestSpecification, URL);

        APIServiceHelper.responseLogs(response);
        return response;

    }

    public Response post(Object body, String URL, String token){
        //un post ce contine un body + url + auth
        //pun body-ul + pun tokenul
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.header("Authorization", "Bearer " + token);
        requestSpecification.body(body);



        //trebuie facut response-ul
        Response response = performRequest(RestRequestType.REQUEST_POST, requestSpecification, URL);
        return response;
    }

    //Facem o instanta de RestRequest care sa apeleze metoda de permormRequest
    private Response performRequest(String requestType, RequestSpecification requestSpecification, String URL){
        return new RestRequest().performRequest(requestType, requestSpecification, URL);
    }

    public Response get(String URL, String token){
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.header("Authorization", "Bearer " + token);

        APIServiceHelper.requestLogs(requestSpecification, URL, RestRequestType.REQUEST_GET);

        //trebuie facut response-ul
        Response response = performRequest(RestRequestType.REQUEST_GET, requestSpecification, URL);
        APIServiceHelper.responseLogs(response);
        return response;

    }

    public Response delete(String URL, String token){
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.header("Authorization", "Bearer " + token);

        APIServiceHelper.requestLogs(requestSpecification, URL, RestRequestType.REQUEST_DELETE);

        //trebuie facut response-ul
        Response response = performRequest(RestRequestType.REQUEST_DELETE, requestSpecification, URL);
        APIServiceHelper.responseLogs(response);
        return response;

    }

}
