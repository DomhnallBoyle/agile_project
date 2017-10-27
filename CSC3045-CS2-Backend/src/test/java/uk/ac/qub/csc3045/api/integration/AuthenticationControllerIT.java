package uk.ac.qub.csc3045.api.integration;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import io.restassured.response.Response;
import uk.ac.qub.csc3045.api.integration.util.RequestHelper;
import uk.ac.qub.csc3045.api.model.Account;
import uk.ac.qub.csc3045.api.model.User;

public class AuthenticationControllerIT {
	private RequestHelper request;

	Account validAccount;
	User validUser;
	String invalidPasswordErrorMessage = "The Password does not meet the requirements:\n\tPassword length must be between 4 and 25 characters\n\tPassword must contain at least 1 uppercase letter, 1 lowercase letter and 1 digit\n";
	String invalidEmailErrorMessage = "The Email does not meet the requirements:\n\tEmail identifier must be at least 4 letters and have a valid domain\n\tEmail identifier can only contain the following special characters '.', '_', '%', '+', '$'\n";
	String invalidUsernameErrorMessage = "The Username does not meet the requirements:\n\tUsername length must be between 3 and 15 characters\n\tUsername can only contain the following special characters '_', '-', '.'\n";
	String usernameConflictErrorMessage = "This username already exists, please select another one";
	String emailConflictErrorMessage = "This email already exists, please select another one";
	
	public static final String URI_CONTEXT = "/authentication/register";

	@Before
	public void setup() throws IOException {
		request = new RequestHelper();

		setupTestAccount();
	}

	//Successful registration tests
	@Test
	public void registerAccountShouldReturn200() throws Exception {
		validAccount.setUsername(generateUsername());
		validAccount.getUser().setEmail(generateEmail());
		Response r = request.SendPostRequest(URI_CONTEXT, validAccount);
		
		assertEquals("Registration Successful!", r.body().asString());
		r.then().assertThat().statusCode(200);
	}
	
	//Missing field tests
	@Test
	public void missingUsernameShouldReturn400() {
		Account invalidAccount = validAccount;
		invalidAccount.setUsername(null);
		Response r = request.SendPostRequest(URI_CONTEXT, invalidAccount);
		
		r.then().assertThat().statusCode(400);
	}
	
	@Test
	public void missingEmailShouldReturn400() {
		Account invalidAccount = validAccount;
		invalidAccount.getUser().setEmail(null);
		Response r = request.SendPostRequest(URI_CONTEXT, invalidAccount);
		
		r.then().assertThat().statusCode(400);
	}
	
	@Test
	public void missingForenameShouldReturn400() {
		Account invalidAccount = validAccount;
		invalidAccount.getUser().setForename(null);
		Response r = request.SendPostRequest(URI_CONTEXT, invalidAccount);
		
		r.then().assertThat().statusCode(400);
	}
	
	@Test
	public void missingSurnameShouldReturn400() {
		Account invalidAccount = validAccount;
		invalidAccount.getUser().setSurname(null);
		Response r = request.SendPostRequest(URI_CONTEXT, invalidAccount);
		
		r.then().assertThat().statusCode(400);
	}
	
	@Test
	public void missingUserShouldReturn400() {
		Account invalidAccount = validAccount;
		invalidAccount.setUser(null);
		Response r = request.SendPostRequest(URI_CONTEXT, invalidAccount);
		
		r.then().assertThat().statusCode(400);
	}
	
	//Invalid input tests
	@Test
	public void invalidUsernameShouldReturn400() throws Exception {
		Account invalidAccount = validAccount;
		invalidAccount.setUsername("sh");
		Response r = request.SendPostRequest(URI_CONTEXT, invalidAccount);
		
		assertEquals(r.body().asString(), invalidUsernameErrorMessage);
		r.then().assertThat().statusCode(400);
	}
	
	@Test
	public void invalidEmailShouldReturn400() throws Exception {
		Account invalidAccount = validAccount;
		invalidAccount.getUser().setEmail("wrong");
		Response r = request.SendPostRequest(URI_CONTEXT, invalidAccount);
		
		assertEquals(r.body().asString(), invalidEmailErrorMessage);
		r.then().assertThat().statusCode(400);
	}
	
	@Test
	public void invalidPasswordShouldReturn400() throws Exception {
		Account invalidAccount = validAccount;
		invalidAccount.setPassword("a");
		Response r = request.SendPostRequest(URI_CONTEXT, invalidAccount);
		
		assertEquals(r.body().asString(), invalidPasswordErrorMessage);
		r.then().assertThat().statusCode(400);
	}
	
	@Test
	public void multipleInvalidDetailsShouldReturn400WithMultipleErrors() throws Exception {
		Account invalidAccount = validAccount;
		invalidAccount.setPassword("a");
		invalidAccount.getUser().setEmail("wrong");
		invalidAccount.setUsername("sh");
		Response r = request.SendPostRequest(URI_CONTEXT, invalidAccount);
		
		assertTrue(r.body().asString().contains(invalidUsernameErrorMessage));
		assertTrue(r.body().asString().contains(invalidEmailErrorMessage));
		assertTrue(r.body().asString().contains(invalidPasswordErrorMessage));

		r.then().assertThat().statusCode(400);
	}
	
	
	//Conflict tests
	@Test
	public void registerExistingAccountShouldReturn409() throws Exception {
		request.SendPostRequest(URI_CONTEXT, validAccount);
		Response r = request.SendPostRequest(URI_CONTEXT, validAccount);
		
		assertEquals(r.body().asString(), usernameConflictErrorMessage);
		r.then().assertThat().statusCode(409);
	}
	
	@Test
	public void registerExistingEmailShouldReturn409() throws Exception {
		validAccount.setUsername(generateUsername());
		request.SendPostRequest(URI_CONTEXT, validAccount);
		validAccount.setUsername(generateUsername());
		Response r = request.SendPostRequest(URI_CONTEXT, validAccount);
		
		assertEquals(r.body().asString(), emailConflictErrorMessage);
		r.then().assertThat().statusCode(409);
	}
	
	// Setup test data
	public void setupTestAccount() {
		validAccount = new Account();
		validAccount.setUsername("username");
		validAccount.setPassword("Password1");
		validUser = new User();
		validUser.setEmail("test@email.com");
		validUser.setForename("Forename");
		validUser.setSurname("Surname");
		validAccount.setUser(validUser);
	}
	
	private String generateUsername() {
		Random random = new Random();
		return "test" + random.nextInt();
	}

    private String generateEmail() {
        Random random = new Random();
        return "test" + random.nextInt() + "@testing.com";
    }
}