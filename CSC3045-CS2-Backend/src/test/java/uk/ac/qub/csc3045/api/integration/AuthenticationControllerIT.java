package uk.ac.qub.csc3045.api.integration;

import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import uk.ac.qub.csc3045.api.integration.util.RequestHelper;
import uk.ac.qub.csc3045.api.model.Account;
import uk.ac.qub.csc3045.api.model.Roles;
import uk.ac.qub.csc3045.api.model.User;

import java.io.IOException;
import java.util.Random;

import static org.junit.Assert.*;

public class AuthenticationControllerIT {
    private RequestHelper request;
    private Account account;
    private String invalidPasswordErrorMessage = "The Password does not meet the requirements:\n\tPassword length must be between 4 and 25 characters\n\tPassword must contain at least 1 uppercase letter, 1 lowercase letter and 1 digit\n";
    private String invalidEmailErrorMessage = "The Email does not meet the requirements:\n\tEmail identifier must be at least 4 letters and have a valid domain\n\tEmail identifier can only contain the following special characters '.', '_', '%', '+', '$'\n";
    private String emailConflictErrorMessage = "This email already exists, please select another one";
    private String loginBadCredentials = "Authentication Failed: Bad credentials";

    public static final String BASE_PATH = "/authentication";
    public static final String REGISTER_PATH = BASE_PATH + "/register";
    public static final String LOGIN_PATH = BASE_PATH + "/login";

    @Before
    public void setup() throws IOException {
        request = new RequestHelper();

        setupTestAccount();
    }

    /**
     * Successful Registration Tests
     */
    @Test
    public void registerAccountShouldReturn201() throws Exception {
        Response r = request.sendPostRequest(REGISTER_PATH, account);

        assertEquals(account.getUser().getEmail(), r.getBody().as(Account.class).getUser().getEmail());
        r.then().assertThat().statusCode(201);
    }

    @Test
    public void registerAccountAsProductOwnerRoleShouldReturn201() {
        Roles newRoles = new Roles();
        newRoles.setProductOwner(true);
        account.getUser().setRoles(newRoles);

        Response r = request.sendPostRequest(REGISTER_PATH, account);

        assertEquals(account.getUser().getEmail(), r.getBody().as(Account.class).getUser().getEmail());
        r.then().assertThat().statusCode(201);
    }

    @Test
    public void registerAccountAsScrumMasterRoleShouldReturn201() {
        Roles newRoles = new Roles();
        newRoles.setScrumMaster(true);
        account.getUser().setRoles(newRoles);

        Response r = request.sendPostRequest(REGISTER_PATH, account);

        assertEquals(account.getUser().getEmail(), r.getBody().as(Account.class).getUser().getEmail());
        r.then().assertThat().statusCode(201);
    }

    @Test
    public void registerAccountAsDeveloperRoleShouldReturn201() {
        Roles newRoles = new Roles();
        newRoles.setDeveloper(true);
        account.getUser().setRoles(newRoles);

        Response r = request.sendPostRequest(REGISTER_PATH, account);

        assertEquals(account.getUser().getEmail(), r.getBody().as(Account.class).getUser().getEmail());
        r.then().assertThat().statusCode(201);
    }

    @Test
    public void registerAccountWithMultipleRolesShouldReturn201() {
        Roles newRoles = new Roles();
        newRoles.setProductOwner(true);
        newRoles.setScrumMaster(true);
        newRoles.setDeveloper(true);
        account.getUser().setRoles(newRoles);

        Response r = request.sendPostRequest(REGISTER_PATH, account);

        assertEquals(account.getUser().getEmail(), r.getBody().as(Account.class).getUser().getEmail());
        r.then().assertThat().statusCode(201);
    }

    /**
     * Missing Field Tests
     */
    @Test
    public void missingPasswordShouldReturn400() {
        account.setPassword(null);

        Response r = request.sendPostRequest(REGISTER_PATH, account);

        r.then().assertThat().statusCode(400);
    }

    @Test
    public void missingEmailShouldReturn400() {
        account.getUser().setEmail(null);

        Response r = request.sendPostRequest(REGISTER_PATH, account);

        r.then().assertThat().statusCode(400);
    }

    @Test
    public void missingForenameShouldReturn400() {
        account.getUser().setForename(null);

        Response r = request.sendPostRequest(REGISTER_PATH, account);

        r.then().assertThat().statusCode(400);
    }

    @Test
    public void missingSurnameShouldReturn400() {
        account.getUser().setSurname(null);

        Response r = request.sendPostRequest(REGISTER_PATH, account);

        r.then().assertThat().statusCode(400);
    }

    @Test
    public void missingUserShouldReturn400() {
        account.setUser(null);

        Response r = request.sendPostRequest(REGISTER_PATH, account);

        r.then().assertThat().statusCode(400);
    }

    /**
     * Invalid Input Tests
     */

    @Test
    public void invalidEmailShouldReturn400() {
        account.getUser().setEmail("wrong");

        Response r = request.sendPostRequest(REGISTER_PATH, account);

        assertEquals(invalidEmailErrorMessage, r.body().asString());
        r.then().assertThat().statusCode(400);
    }

    @Test
    public void invalidPasswordShouldReturn400() {
        account.setPassword("a");

        Response r = request.sendPostRequest(REGISTER_PATH, account);

        assertEquals(invalidPasswordErrorMessage, r.body().asString());
        r.then().assertThat().statusCode(400);
    }

    @Test
    public void multipleInvalidDetailsShouldReturn400WithMultipleErrors() {
        account.setPassword("a");
        account.getUser().setEmail("wrong");

        Response r = request.sendPostRequest(REGISTER_PATH, account);;
        assertTrue(r.body().asString().contains(invalidEmailErrorMessage));
        assertTrue(r.body().asString().contains(invalidPasswordErrorMessage));
        r.then().assertThat().statusCode(400);
    }

    /**
     * Conflict Tests
     */
    @Test
    public void registerExistingEmailShouldReturn409() {
        request.sendPostRequest(REGISTER_PATH, account);

        Response r = request.sendPostRequest(REGISTER_PATH, account);

        assertEquals(emailConflictErrorMessage, r.body().asString());
        r.then().assertThat().statusCode(409);
    }

    /**
     * Successful Authentication Tests
     */
    @Test
    public void loginShouldReturn200() {
        Account existingAccount = new Account(new User("Forename1", "Surname1", "user1@email.com", new Roles(false, false, false)), "Passw0rd1");

        Response r = request.sendPostRequest(LOGIN_PATH, existingAccount);
        User returnedUser = r.getBody().as(User.class);

        r.then().assertThat().statusCode(200);
        assertTrue(r.getHeader("Authorization").matches("Bearer [a-zA-Z0-9_-]+.[a-zA-Z0-9_-]+.[a-zA-Z0-9_-]+"));
        existingAccount.getUser().setId(returnedUser.getId());
        existingAccount.getUser().setRoles(returnedUser.getRoles());
        assertTrue(existingAccount.getUser().equals(returnedUser));
    }

    /**
     * Unsuccessful Authentication Tests
     */
    @Test
    public void loginMissingEmailShouldReturn401() {
        account.getUser().setEmail(null);
        Response r = request.sendPostRequest(LOGIN_PATH, account);

        assertTrue(r.body().asString().contains(loginBadCredentials));
        r.then().assertThat().statusCode(401);
        assertEquals(r.getHeader("Authorization"), null);
    }

    @Test
    public void loginMissingPasswordShouldReturn401() {
        account.setPassword(null);
        Response r = request.sendPostRequest(LOGIN_PATH, account);

        assertTrue(r.body().asString().contains(loginBadCredentials));
        r.then().assertThat().statusCode(401);
        assertEquals(r.getHeader("Authorization"), null);
    }

    /**
     * Successful Authorization Tests
     */
    @Test
    public void successfulAuthorizationShouldNotReturn403() {
        Account existingAccount = new Account(new User("Forename1", "Surname1", "user1@gmail.com", new Roles(false, false, false)), "Passw0rd1");
        Response r = request.sendPostRequest(LOGIN_PATH, existingAccount);
        String authHeader = r.getHeader("Authorization");

        r = request.sendGetRequestWithAuthHeader("/project/1", authHeader);
        assertFalse(r.statusCode() == 403);
    }

    /**
     * Unsuccessful Authorization Tests
     */
    @Test
    public void unsuccessfulGetAuthorizationShouldReturn403() {
        Response r = request.sendGetRequest("/project");

        assertTrue(r.body().asString().contains("Access Denied"));
        r.then().assertThat().statusCode(403);
    }

    @Test
    public void unsuccessfulPostAuthorizationShouldReturn403() {
        Response r = request.sendPostRequest("/project", account);

        assertTrue(r.body().asString().contains("Access Denied"));
        r.then().assertThat().statusCode(403);
    }

    /**
     * Setup Test Data
     */
    public void setupTestAccount() {
        Roles validRoles = new Roles();
        User validUser = new User("Forename", "Surname", generateEmail(), validRoles);
        account = new Account(validUser, "Password1");
    }

    /**
     * Generates a random email
     *
     * @return the email generated
     */
    private String generateEmail() {
        Random random = new Random();
        return "testing" + random.nextInt(5000) + "@testing.com";
    }
}