package uk.ac.qub.csc3045.api.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import io.restassured.response.Response;
import uk.ac.qub.csc3045.api.integration.util.RequestHelper;
import uk.ac.qub.csc3045.api.model.Account;
import uk.ac.qub.csc3045.api.model.Project;
import uk.ac.qub.csc3045.api.model.Roles;
import uk.ac.qub.csc3045.api.model.Sprint;
import uk.ac.qub.csc3045.api.model.User;

public class SprintControllerIT {

	private static final String BASE_PATH = "/project/1/sprint";

	private RequestHelper requestHelper;
	private String authHeader;

	private String sprintDoesNotExistErrorMessage = "Sprint does not exist";
	private String projectDoesNotExistInDatabaseErrorMessage = "Project Id does not exist in the database";
	private String scrumMasterDoesNotExistInDatabaseErrorMessage = "Scrum Master does not exist in the database";

	private Account account;
	private Sprint existingSprint;
	private Sprint newSprint;
	private Project existingProject;
	private User existingUser;
	private User existingScrum;

	@Before
	public void setup() throws IOException {
		requestHelper = new RequestHelper();

		setupTestAccount();
		setupBacklog();

		authHeader = requestHelper.getAuthHeader(account);
	}

	/**
	 * Create Sprint tests
	 */
	@Test
	public void createSprintShouldReturn201() {	
		newSprint.setScrumMaster(existingScrum);
		newSprint.setProject(existingProject);

		Response r = requestHelper.sendPostRequestWithAuthHeader(BASE_PATH, authHeader, newSprint);

		assertEquals(201, r.statusCode());
		assertEquals(newSprint.getName(), r.getBody().as(Sprint.class).getName());

	}

	@Test
	public void createSprintProjectDoesNotExistShouldReturn404() {
		Response r = requestHelper.sendPostRequestWithAuthHeader("/project/1000/sprint", authHeader, existingSprint);

		assertEquals(404, r.statusCode());
		assertEquals(projectDoesNotExistInDatabaseErrorMessage, r.body().asString());
	}
	
	@Test
	public void createSprintScrumMasterDoesNotExistShouldReturn400() {
		Sprint newSprint = new Sprint();
		newSprint.setName("SprintName");
		newSprint.setStartDate(LocalDateTime.of(2018, Month.JULY, 20, 19, 30, 40));
		newSprint.setEndDate(LocalDateTime.of(2018, Month.JULY, 29, 19, 30, 40));;
		User nonExistingScrumMaster = new User();
		nonExistingScrumMaster.setId(-1L);
	    newSprint.setScrumMaster(nonExistingScrumMaster);
	    
		Response r = requestHelper.sendPostRequestWithAuthHeader(BASE_PATH, authHeader, existingSprint);

		assertEquals(400, r.statusCode());
		assertEquals(scrumMasterDoesNotExistInDatabaseErrorMessage, r.body().asString());
	}

	/**
	 * Get Sprint tests
	 */
	@Test
	public void getSprintThatExistsShouldReturn200() {

		Response r = requestHelper.sendGetRequestWithAuthHeader(BASE_PATH + "/" + existingSprint.getId() , authHeader);
		Sprint returnedSprint = r.getBody().as(Sprint.class);

		r.then().assertThat().statusCode(200);
		assertTrue(existingSprint.getId() == returnedSprint.getId());
	}

	@Test
	public void getSprintThatDoesntExistShouldReturn404() {
		Response r = requestHelper.sendGetRequestWithAuthHeader(BASE_PATH + "/6500", authHeader);

		r.then().assertThat().statusCode(404);
		assertEquals(sprintDoesNotExistErrorMessage, r.getBody().asString());

	}
	
	@Test
	public void getSprintWhereProjectDoesntExistShouldReturn404() {
		Response r = requestHelper.sendGetRequestWithAuthHeader("/project/1000/sprint/6500", authHeader);

		r.then().assertThat().statusCode(404);
		assertEquals(projectDoesNotExistInDatabaseErrorMessage, r.getBody().asString());

	}

	private void setupBacklog() {
		existingUser = new User("Snoop", "Dogg", "snoop.dogg@shizzle.hold.up", new Roles(true, true, true));
		existingScrum = new User("Richard", "Hendrix", "r.hendrix@valley.com", new Roles(true, true, true));
		existingScrum.setId(1L);

		List<User> users = new ArrayList<>();
		users.add(existingUser);

		existingProject = new Project("Pied Piper", "Cloud storage using revolutionary middle-out compression.", existingUser, existingUser, users, users, new ArrayList<>());
		existingProject.setId(1L);
        existingProject.setManager(existingUser);

		existingSprint = new Sprint("SprintName", LocalDateTime.of(2018, Month.JULY, 20, 19, 30, 40), LocalDateTime.of(2018, Month.JULY, 29, 19, 30, 40));
		existingSprint.setProject(existingProject);
		existingSprint.setScrumMaster(existingUser);
		existingSprint.setId(1L);
		
		newSprint = new Sprint("SprintName", LocalDateTime.of(2018, Month.JULY, 20, 19, 30, 40), LocalDateTime.of(2018, Month.JULY, 29, 19, 30, 40) );
	}

	private void setupTestAccount() {
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
