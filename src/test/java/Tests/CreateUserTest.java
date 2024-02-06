package Tests;

import Actions.AccountActions;
import Hooks.Hooks;
import Objects.RequestObject.RequestAccount;
import Objects.RequestObject.RequestAccountToken;
import Objects.ResponseObject.ResponseSuccessAccount;
import Objects.ResponseObject.ResponseTokenSuccess;
import org.testng.annotations.Test;

public class CreateUserTest extends Hooks {
    public String userID;
    public String username;
    public String password; // le facem variabile globale, sa le poti folosi in toate requesturile si de create si de auth si de delete etc..

    public String token;
    public AccountActions accountActions;


    @Test
    public void TestMethod() {


        System.out.println("Step 1. Create User");
        createUser();
        System.out.println();
        System.out.println("Step 2. Generate Token");
        generateToken();
        System.out.println();
        System.out.println("Step 3. Obtain new user");
        interractNewUser();
        System.out.println();
        System.out.println("Step 4. Delete new user");
        deleteSpecificUser();
        System.out.println();
        System.out.println("Step 5. Obtain new user");
        interractNewUser();


    }

    public void createUser() {
        accountActions = new AccountActions();
        username = "Roxana" + System.currentTimeMillis();
        password = "Password1224!";

        RequestAccount requestAccount = new RequestAccount(username, password);
        ResponseSuccessAccount responseSuccessAccount = accountActions.createNewAccount(requestAccount);

        userID = responseSuccessAccount.getUserID();


    }

    // Facem un request  care ne genereaza un token (autentificare )
    public void generateToken() {
        accountActions = new AccountActions();
        RequestAccountToken requestAccountToken = new RequestAccountToken(username, password);
        ResponseTokenSuccess responseTokenSuccess = accountActions.generateToken(requestAccountToken);

        token = responseTokenSuccess.getToken();
    }
    // Facem un get pentru userul creat

    public void interractNewUser() {
        accountActions = new AccountActions();
        accountActions.obtainSpecificAccount(userID, token, username);
    }

    //Stergem noul user creat

    public void deleteSpecificUser(){
        accountActions = new AccountActions();
        accountActions.deleteSpecificAccount(userID, token);
    }

}

