package Actions;

import Objects.RequestObject.RequestAccount;
import Objects.RequestObject.RequestAccountToken;
import Objects.ResponseObject.ResponseAccountAuthorize;
import Objects.ResponseObject.ResponseAccountFailed;
import Objects.ResponseObject.ResponseSuccessAccount;
import Objects.ResponseObject.ResponseTokenSuccess;
import Rest.RestRequestStatus;
import Service.ServiceImplementation.AccountServiceImpl;
import io.restassured.response.Response;
import org.testng.Assert;

public class AccountActions {

    //untilul layer - actiunile au nevoie de serviciu sa poata fi apelate
    //account action are enevoie de un obiect de tipul service

    public AccountServiceImpl accountService;

    public ResponseSuccessAccount createNewAccount(RequestAccount requestAccount){
        accountService = new AccountServiceImpl();
        Response response = accountService.createAccount(requestAccount);
        Assert.assertEquals((int)RestRequestStatus.SC_CREATED, response.getStatusCode());

        ResponseSuccessAccount responseSuccessAccount = response.body().as(ResponseSuccessAccount.class);
        Assert.assertNotNull(responseSuccessAccount.getUserID());
        Assert.assertEquals(responseSuccessAccount.getUsername(), requestAccount.getUserName());
        Assert.assertNotNull(responseSuccessAccount.getBooks());


        return responseSuccessAccount;
    }

    public ResponseTokenSuccess generateToken(RequestAccountToken requestAccountToken){
        accountService= new AccountServiceImpl();
        Response response = accountService.generateToken(requestAccountToken);
        Assert.assertEquals(RestRequestStatus.SC_OK, response.getStatusCode());

        ResponseTokenSuccess responseTokenSuccess = response.body().as(ResponseTokenSuccess.class);

        Assert.assertNotNull(responseTokenSuccess.getToken());
        Assert.assertNotNull(responseTokenSuccess.getExpires());
        Assert.assertEquals(responseTokenSuccess.getStatus(), "Success");
        Assert.assertEquals(responseTokenSuccess.getResult(), "User authorized successfully.");

        return responseTokenSuccess;

    }

    public void obtainSpecificAccount(String userID, String token, String username){
        accountService = new AccountServiceImpl();
        Response response = accountService.getSpecificAccount(userID, token);

        if (response.getStatusCode() == RestRequestStatus.SC_OK) {

            Assert.assertEquals(response.getStatusCode(), RestRequestStatus.SC_OK);

            ResponseAccountAuthorize responseAccountAuthorize = response.body().as(ResponseAccountAuthorize.class);

            Assert.assertNotNull(responseAccountAuthorize.getUserId());
            Assert.assertEquals(responseAccountAuthorize.getUsername(), username);
            Assert.assertNotNull(responseAccountAuthorize.getBooks());
        }
        if (response.getStatusCode() == RestRequestStatus.SC_UNAUTHORIZED){
            Assert.assertEquals(response.getStatusCode(), RestRequestStatus.SC_UNAUTHORIZED);
            ResponseAccountFailed responseAccountFailed = response.body().as(ResponseAccountFailed.class);

            Assert.assertEquals(responseAccountFailed.getCode(),1207);
            Assert.assertEquals(responseAccountFailed.getMessage(), "User not found!");

        }
    }

    public void deleteSpecificAccount(String userID, String token){
        accountService =new AccountServiceImpl();
        Response response =accountService.deleteSpecificUser(userID, token);

        Assert.assertEquals(response.getStatusCode(), RestRequestStatus.SC_NOCONTENT);


    }





    
}

