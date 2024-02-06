package Rest;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestRequest {
    //Trebuie sa fac o metoda care sa execute un request la un endpoint

    //ii specificam ce actiune vrem noi sa faca
    public Response performRequest(String requestType, RequestSpecification requestSpecification, String URL){
        switch (requestType){
            case RestRequestType.REQUEST_DELETE:
                return prepare(requestSpecification).delete(URL); // face un reguest de tipul delete, pe url-ul setat in metoda de mai jos
            case RestRequestType.REQUEST_GET:
                return prepare(requestSpecification).get(URL);
            case RestRequestType.REQUEST_POST:
                return prepare(requestSpecification).post(URL);
            case RestRequestType.REQUEST_PUT:
                return prepare(requestSpecification).put(URL);

        }
        return null; //in caz ca nu e nicio metoda, trebuie sa dea null

    }






    //Trebuie sa configurez setarile pentru client

    //RequestSpecification = creaza clientul si iti da posibilitatea sa mai adaugi ceva - de acceea se pune ca parametru la metoda
    public RequestSpecification prepare(RequestSpecification requestSpecification){
        //daca vin niste setari de la parametru - pe el faci actiunile de care ai nevoie
        requestSpecification.baseUri("https://demoqa.com"); // clientul sa porneasca din endpointul asta
        requestSpecification.contentType("application/json");
        return requestSpecification;

        //configuram setarile pe client - sa existe url-ul de baza asta si sa primeasca requesturi de tipul json
    }

}
