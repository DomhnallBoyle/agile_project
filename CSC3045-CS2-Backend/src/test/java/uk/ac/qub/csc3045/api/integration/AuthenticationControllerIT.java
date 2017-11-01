package uk.ac.qub.csc3045.api.integration;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import io.restassured.response.Response;
import uk.ac.qub.csc3045.api.integration.util.RequestHelper;
import uk.ac.qub.csc3045.api.model.Account;
import uk.ac.qub.csc3045.api.model.Roles;
import uk.ac.qub.csc3045.api.model.User;

public class AuthenticationControllerIT {
    private RequestHelper request;

    private Account account;
    private String invalidPasswordErrorMessage = "The Password does not meet the requirements:\n\tPassword length must be between 4 and 25 characters\n\tPassword must contain at least 1 uppercase letter, 1 lowercase letter and 1 digit\n";
    private String invalidEmailErrorMessage = "The Email does not meet the requirements:\n\tEmail identifier must be at least 4 letters and have a valid domain\n\tEmail identifier can only contain the following special characters '.', '_', '%', '+', '$'\n";
    private String invalidUsernameErrorMessage = "The Username does not meet the requirements:\n\tUsername length must be between 3 and 15 characters\n\tUsername can only contain the following special characters '_', '-', '.'\n";
    private String usernameConflictErrorMessage = "This username already exists, please select another one";
    private String emailConflictErrorMessage = "This email already exists, please select another one";

    public static final String URI_CONTEXT = "/authentication/register";

    @Before
    public void setup() throws IOException {
        request = new RequestHelper();

        setupTestAccount();
    }

    /**
     * Successful Registration Tests
     */
    @Test
    public void registerAccountShouldReturn200() throws Exception {
        Response r = request.SendPostRequest(URI_CONTEXT, account);

        assertEquals(account.getUsername(), r.getBody().as(Account.class).getUsername());
        r.then().assertThat().statusCode(200);
    }

    @Test
    public void registerAccountAsProductOwnerRoleShouldReturn200() {
        Roles newRoles = new Roles();
        newRoles.setProductOwner(true);
        account.getUser().setRoles(newRoles);

        Response r = request.SendPostRequest(URI_CONTEXT, account);

        assertEquals(account.getUsername(), r.getBody().as(Account.class).getUsername());
        r.then().assertThat().statusCode(200);
    }

    @Test
    public void registerAccountAsScrumMasterRoleShouldReturn200() {
        Roles newRoles = new Roles();
        newRoles.setScrumMaster(true);
        account.getUser().setRoles(newRoles);

        Response r = request.SendPostRequest(URI_CONTEXT, account);

        assertEquals(account.getUsername(), r.getBody().as(Account.class).getUsername());
        r.then().assertThat().statusCode(200);
    }

    @Test
    public void registerAccountAsDeveloperRoleShouldReturn200() {
        Roles newRoles = new Roles();
        newRoles.setDeveloper(true);
        account.getUser().setRoles(newRoles);

        Response r = request.SendPostRequest(URI_CONTEXT, account);

        assertEquals(account.getUsername(), r.getBody().as(Account.class).getUsername());
        r.then().assertThat().statusCode(200);
    }

    @Test
    public void registerAccountWithMultipleRolesShouldReturn200() {
        Roles newRoles = new Roles();
        newRoles.setProductOwner(true);
        newRoles.setScrumMaster(true);
        newRoles.setDeveloper(true);
        account.getUser().setRoles(newRoles);

        Response r = request.SendPostRequest(URI_CONTEXT, account);
        
        assertEquals(account.getUsername(), r.getBody().as(Account.class).getUsername());
        r.then().assertThat().statusCode(200);
    }

    /**
     * Missing Field Tests
     */
    @Test
    public void missingUsernameShouldReturn400() {
        account.setUsername(null);
        
        Response r = request.SendPostRequest(URI_CONTEXT, account);
        
        r.then().assertThat().statusCode(400);
    }
    
    @Test
    public void missingPasswordShouldReturn400() {
        account.setPassword(null);
        
        Response r = request.SendPostRequest(URI_CONTEXT, account);
        
        r.then().assertThat().statusCode(400);
    }
    
    @Test
    public void missingEmailShouldReturn400() {
        account.getUser().setEmail(null);
        
        Response r = request.SendPostRequest(URI_CONTEXT, account);
        
        r.then().assertThat().statusCode(400);
    }
    
    @Test
    public void missingForenameShouldReturn400() {
        account.getUser().setForename(null);
        
        Response r = request.SendPostRequest(URI_CONTEXT, account);
        
        r.then().assertThat().statusCode(400);
    }
    
    @Test
    public void missingSurnameShouldReturn400() {
        account.getUser().setSurname(null);
        
        Response r = request.SendPostRequest(URI_CONTEXT, account);
        
        r.then().assertThat().statusCode(400);
    }
    
    @Test
    public void missingUserShouldReturn400() {
        account.setUser(null);
        
        Response r = request.SendPostRequest(URI_CONTEXT, account);
        
        r.then().assertThat().statusCode(400);
    }
    
    /**
     * Invalid Input Tests
     */
    @Test
    public void invalidUsernameShouldReturn400() {
        account.setUsername("sh");
        
        Response r = request.SendPostRequest(URI_CONTEXT, account);
        
        assertEquals(invalidUsernameErrorMessage, r.body().asString());
        r.then().assertThat().statusCode(400);
    }
    
    @Test
    public void invalidEmailShouldReturn400() {
        account.getUser().setEmail("wrong");
        
        Response r = request.SendPostRequest(URI_CONTEXT, account);
        
        assertEquals(invalidEmailErrorMessage, r.body().asString());
        r.then().assertThat().statusCode(400);
    }
    
    @Test
    public void invalidPasswordShouldReturn400() {
        account.setPassword("a");
        
        Response r = request.SendPostRequest(URI_CONTEXT, account);
        
        assertEquals(invalidPasswordErrorMessage, r.body().asString());
        r.then().assertThat().statusCode(400);
    }
    
    @Test
    public void multipleInvalidDetailsShouldReturn400WithMultipleErrors() {
        account.setPassword("a");
        account.getUser().setEmail("wrong");
        account.setUsername("sh");
        
        Response r = request.SendPostRequest(URI_CONTEXT, account);
        
        assertTrue(r.body().asString().contains(invalidUsernameErrorMessage));
        assertTrue(r.body().asString().contains(invalidEmailErrorMessage));
        assertTrue(r.body().asString().contains(invalidPasswordErrorMessage));
        r.then().assertThat().statusCode(400);
    }

    /**
     * Conflict Tests
     */
    @Test
    public void registerExistingAccountShouldReturn409() {
        request.SendPostRequest(URI_CONTEXT, account);
        Response r = request.SendPostRequest(URI_CONTEXT, account);
        
        assertEquals(usernameConflictErrorMessage, r.body().asString());
        r.then().assertThat().statusCode(409);
    }
    
    @Test
    public void registerExistingEmailShouldReturn409() {
        request.SendPostRequest(URI_CONTEXT, account);
        account.setUsername(generateUsername());
        
        Response r = request.SendPostRequest(URI_CONTEXT, account);
        
        assertEquals(emailConflictErrorMessage, r.body().asString());
        r.then().assertThat().statusCode(409);
    }
    
    /**
     * Setup Test Data
     */
    public void setupTestAccount() {
        account = new Account();
        account.setUsername(generateUsername());
        account.setPassword("Password1");
        User validUser = new User();
        validUser.setEmail(generateEmail());
        validUser.setForename("Forename");
        validUser.setSurname("Surname");
        Roles validRoles = new Roles();
        validUser.setRoles(validRoles);
        account.setUser(validUser);
    }

    /**
     * Generates a random username
     * @return the username generated
     */
    private String generateUsername() {
        Random random = new Random();
        return "test" + random.nextInt(5000);
    }

    /**
     * Generates a random email
     * @return the email generated
     */
    private String generateEmail() {
        Random random = new Random();
        return "testing" + random.nextInt(5000) + "@testing.com";
    }
}