package Actions;

import Objects.RequestObject.RequestAccount;
import Objects.RequestObject.RequestAccountToken;
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





    
}

