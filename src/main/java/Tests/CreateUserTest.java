package Tests;

import RequestObject.RequestAccount;
import RequestObject.RequestAccountToken;
import RequestObject.ResponseAccountFailed;
import ResponseObject.ResponseAccountAuthorize;
import ResponseObject.ResponseSuccessAccount;
import ResponseObject.ResponseTokenSuccess;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CreateUserTest {
    public String userID;
    public String username;
    public String password; // le facem variabile globale, sa le poti folosi in toate requesturile si de create si de auth si de delete etc..

    public String token;


    @Test
    public void TestMethod(){

        System.out.println("Step 1. Create User");
        createUser();
        System.out.println("Step 2. Generate Token");
        generateToken();
        System.out.println("Step 3. Obtain new user");
        interractNewUser();
        System.out.println("Step 4. creat a user which exists");
        createUserFail();



        //Definim caracteristicile clientului
        //client -> se seteaza un URL de baza
//        RequestSpecification requestSpecification = RestAssured.given(); // asa se cogigureaza clientul cu anumite specificatii
//        requestSpecification.baseUri("https://demoqa.com"); //url-ul de baza din swagger (client)
//        requestSpecification.contentType("application/json"); //body-ul va fi de tipul json
//
//        //Configuram request-ul
//        String username = "Roxana" + System.currentTimeMillis();
//
////        JSONObject requestBody = new JSONObject();
////        requestBody.put("userName", username);
////        requestBody.put("password", "Roxana1235!");
//        RequestAccount requestAccount = new RequestAccount(username, "Password1224!");
//
//        //cand se face requestul trebuie sa se respecte un anumit body, adica cel de mai sus
//        requestSpecification.body(requestAccount);
//
//
//        //Accesam response-ul
//        //trimitand un request de tip ex. post
//        Response response = requestSpecification.post("/Account/v1/User");
//
//        //accesam informatia
//        ResponseBody body = response.getBody();
//        body.prettyPrint();
//
//        //validam statusul request-ului
//        Assert.assertEquals(response.getStatusCode(), 201);
//
//        //validam response body
//        ResponseSuccessAccount responseSuccessAccount = response.body().as(ResponseSuccessAccount.class); //response-ul trebuie sa fie de tipul classei resonse SuccessAccount
//        Assert.assertNotNull(responseSuccessAccount.getUserID());  // se verifica ca exista o valoare pentru fieldul asta
//        Assert.assertEquals(responseSuccessAccount.getUsername(), username); // se verifica ca uername-ul are valoarea din request
//        Assert.assertNotNull(responseSuccessAccount.getBooks()); // se verifica ca lista este initializata, chiar daca e empty

    }

    public void createUser(){

        RequestSpecification requestSpecification = RestAssured.given(); // asa se cogigureaza clientul cu anumite specificatii
        requestSpecification.baseUri("https://demoqa.com"); //url-ul de baza din swagger (client)
        requestSpecification.contentType("application/json"); //body-ul va fi de tipul json

        username = "Roxana" + System.currentTimeMillis();
        password = "Password1224!";

        RequestAccount requestAccount = new RequestAccount(username, password);

        requestSpecification.body(requestAccount);

        Response response = requestSpecification.post("/Account/v1/User");

        ResponseBody body = response.getBody();
        body.prettyPrint();

        Assert.assertEquals(response.getStatusCode(), 201);

        ResponseSuccessAccount responseSuccessAccount = response.body().as(ResponseSuccessAccount.class); //response-ul trebuie sa fie de tipul classei resonse SuccessAccount
        Assert.assertNotNull(responseSuccessAccount.getUserID());  // se verifica ca exista o valoare pentru fieldul asta
        Assert.assertEquals(responseSuccessAccount.getUsername(), username); // se verifica ca uername-ul are valoarea din request
        Assert.assertNotNull(responseSuccessAccount.getBooks());
        userID = responseSuccessAccount.getUserID(); // scoti userId si il salvezi sa il folosesti mai departe in alte request-uri

    }

    // Facem un request  care ne genereaza un token (autentificare )
    public void generateToken() {
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://demoqa.com");
        requestSpecification.contentType("application/json");

        RequestAccountToken requestAccountToken = new RequestAccountToken(username, password);
        requestSpecification.body(requestAccountToken);

        Response response = requestSpecification.post("/Account/v1/GenerateToken");

        ResponseBody body = response.getBody();
        body.prettyPrint();

        Assert.assertEquals(response.getStatusCode(), 200);
        ResponseTokenSuccess responseTokenSuccess = response.body().as(ResponseTokenSuccess.class);

        Assert.assertNotNull(responseTokenSuccess.getToken());
        Assert.assertNotNull(responseTokenSuccess.getExpires());
        Assert.assertEquals(responseTokenSuccess.getStatus(), "Success");
        Assert.assertEquals(responseTokenSuccess.getResult(), "User authorized successfully.");
        token = responseTokenSuccess.getToken(); // ca sa salveze valoarea tokenului si se foloseste apoi ca valoarea a variabilelei globale
    }

        // Facem un get pentru userul creat

        public void interractNewUser(){
            RequestSpecification requestSpecification = RestAssured.given();
            requestSpecification.baseUri("https://demoqa.com");
            requestSpecification.contentType("application/json");
            requestSpecification.header("Authorization", "Bearer " + token);  // auth se pune intotdeauna in zona de header

            Response response = requestSpecification.get("/Account/v1/User/"+ userID);
            Assert.assertEquals(response.getStatusCode(), 200);

            ResponseAccountAuthorize responseAccountAuthorize = response.body().as(ResponseAccountAuthorize.class);

            Assert.assertNotNull(responseAccountAuthorize.getUserId());
            Assert.assertEquals(responseAccountAuthorize.getUsername(), username);
            Assert.assertNotNull(responseAccountAuthorize.getBooks());

        }
        public void createUserFail(){
        //create a user which already exists
        //Step 1. Request:
            RequestSpecification requestSpecification = RestAssured.given(); // asa se configureaza clientul cu anumite specificatii
            requestSpecification.baseUri("https://demoqa.com"); //url-ul de baza din swagger (client)
            requestSpecification.contentType("application/json");

            //Stept 2. Body of request
            username="Roxana";
            password="Password1224!";
            //folosim obiectul care creaza body-ul
            RequestAccount requestAccount = new RequestAccount(username, password);
            requestSpecification.body(requestAccount);

            //Step 3. Accesam  response-ul

            Response response = requestSpecification.post("/Account/v1/User");
            ResponseBody body=response.getBody();
            body.prettyPrint();

            //validam statusul requestului
            Assert.assertEquals(response.getStatusCode(), 406);

            //Validam response body-ul requestului

            ResponseAccountFailed responseAccountFailed = response.body().as(ResponseAccountFailed.class);

            Assert.assertNotNull(responseAccountFailed.getCode());
            Assert.assertEquals(responseAccountFailed.getMessage(), "User exists!");





        }

    }

