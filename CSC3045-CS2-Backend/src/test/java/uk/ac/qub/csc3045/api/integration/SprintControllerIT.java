package uk.ac.qub.csc3045.api.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static uk.ac.qub.csc3045.api.setup.UnitTestObjectGenerator.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
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
import uk.ac.qub.csc3045.api.model.UserStory;

public class SprintControllerIT {

	private long nonExistingId = -1L;

	private RequestHelper requestHelper;
	private String authHeader;

	private String sprintDoesNotExistErrorMessage = "Sprint does not exist in the database";
	private String projectDoesNotExistInDatabaseErrorMessage = "Project does not exist in the database";
	private String scrumMasterDoesNotExistInDatabaseErrorMessage = "Scrum Master does not exist in the database";
	private String sprintAndUserStoryHaveDifferentProjects = "Sprint and user story aren't in same Project";

	private Account account;
	private Project existingProject;
	private Sprint existingSprint;
	private Sprint newSprint;
	private User existingUser;

	@Before
	public void setup() throws IOException {
		requestHelper = new RequestHelper();

		setupTestAccount();
		setupBacklog();

		authHeader = requestHelper.getAuthHeader(account);
	}

	/**
	 * Create Sprint Tests
	 */
	@Test
	public void createSprintShouldReturn201() {	
		newSprint.setScrumMaster(existingUser);
		newSprint.setProject(existingProject);

		Response r = requestHelper.sendPostRequestWithAuthHeader("/project/" + existingProject.getId() + "/sprint", authHeader, newSprint);

		assertEquals(201, r.statusCode());
		assertEquals(newSprint.getName(), r.getBody().as(Sprint.class).getName());
	}

	@Test
	public void createSprintProjectDoesNotExistShouldReturn404() {
		Response r = requestHelper.sendPostRequestWithAuthHeader("/project/" + nonExistingId + "/sprint", authHeader, newSprint);

		assertEquals(404, r.statusCode());
		assertEquals(projectDoesNotExistInDatabaseErrorMessage, r.body().asString());
	}
	
	@Test
	public void createSprintScrumMasterDoesNotExistShouldReturn400() {
		User nonExistingScrumMaster = new User();
		nonExistingScrumMaster.setId(-1L);
	    newSprint.setScrumMaster(nonExistingScrumMaster);
	    
		Response r = requestHelper.sendPostRequestWithAuthHeader("/project/" + existingProject.getId() + "/sprint", authHeader, newSprint);

		assertEquals(400, r.statusCode());
		assertEquals(scrumMasterDoesNotExistInDatabaseErrorMessage, r.body().asString());
	}

	/**
	 * Get Sprints For Project Tests
	 */
	@Test
	public void getProjectSprintsThatExistShouldReturn200() {
		Response r = requestHelper.sendGetRequestWithAuthHeader("/project/" + existingProject.getId() + "/sprint", authHeader);
		List<Sprint> returnedSprints = Arrays.asList(r.getBody().as(Sprint[].class));
		
		r.then().assertThat().statusCode(200);
	}
	
	/**
	 * Get Sprint Tests
	 */
	@Test
	public void getSprintThatExistsShouldReturn200() {
		Response r = requestHelper.sendGetRequestWithAuthHeader("/project/" + existingProject.getId() + "/sprint/" + existingSprint.getId() , authHeader);
		Sprint returnedSprint = r.getBody().as(Sprint.class);

		r.then().assertThat().statusCode(200);
		assertEquals(existingSprint.getId(), returnedSprint.getId());
	}

	@Test
	public void getSprintWhereProjectDoesntExistShouldReturn404() {
		Response r = requestHelper.sendGetRequestWithAuthHeader("/project/" + nonExistingId + "/sprint/" + existingSprint.getId(), authHeader);

		r.then().assertThat().statusCode(404);
		assertEquals(projectDoesNotExistInDatabaseErrorMessage, r.getBody().asString());
	}
	
	@Test
	public void getSprintThatDoesntExistShouldReturn404() {
		Response r = requestHelper.sendGetRequestWithAuthHeader("/project/" + existingProject.getId() + "/sprint/" + nonExistingId, authHeader);

		r.then().assertThat().statusCode(404);
		assertEquals(sprintDoesNotExistErrorMessage, r.getBody().asString());
	}
	
	/**
	 * Update Sprint Backlog Tests
	 */
	@Test
	public void updateSprintBacklogShouldReturn200() {
		UserStory userStory = new UserStory("Compress and upload a file",
				"Using the algorithm, a user should be able to upload a file to the cloud.", 
				10, 100, existingProject);
		userStory.setId(1l);
		List<UserStory> sprintBacklog = new ArrayList<UserStory>();
		sprintBacklog.add(userStory);
		existingSprint.setUserStories(sprintBacklog);
		
		assertEquals(existingSprint.getUserStories().size(), 1);
		Response r = requestHelper.sendPutRequestWithAuthHeader("/project/" + existingProject.getId() + "/sprint/" + existingSprint.getId() + "/story", authHeader, existingSprint);
		List<UserStory> updatedUserStories = Arrays.asList(r.getBody().as(UserStory[].class));
		
		r.then().assertThat().statusCode(200);
		assertEquals(updatedUserStories.size(), 1);
		assertEquals(updatedUserStories.get(0).getName(), "Compress and upload a file");
	}
	
	@Test
	public void updateSprintBacklogDifferentProjectsShouldReturn404() {
		UserStory userStory = new UserStory("Compress and upload a file",
				"Using the algorithm, a user should be able to upload a file to the cloud.", 
				10, 100, new Project());
		userStory.setId(1l);
		List<UserStory> sprintBacklog = new ArrayList<UserStory>();
		sprintBacklog.add(userStory);
		existingSprint.setUserStories(sprintBacklog);
		
		assertEquals(existingSprint.getUserStories().size(), 1);
		Response r = requestHelper.sendPutRequestWithAuthHeader("/project/" + existingProject.getId() + "/sprint/" + existingSprint.getId() + "/story", authHeader, existingSprint);
		
		r.then().assertThat().statusCode(404);
		assertEquals(r.body().asString(), sprintAndUserStoryHaveDifferentProjects);
	}
	
	@Test
	public void updateSprintBacklogSprintDoesNotExistShouldReturn404() {
		existingSprint.setId(-1l);
		
		Response r = requestHelper.sendPutRequestWithAuthHeader("/project/" + existingProject.getId() + "/sprint/" + existingSprint.getId() + "/story", authHeader, existingSprint);
		
		r.then().assertThat().statusCode(404);
		assertEquals(r.body().asString(), sprintDoesNotExistErrorMessage);
	}
	
	@Test
	public void updateSprintBacklogProjectDoesNotExistShouldReturn404() {
		Response r = requestHelper.sendPutRequestWithAuthHeader("/project/-1/sprint/" + existingSprint.getId() + "/story", authHeader, existingSprint);
		
		r.then().assertThat().statusCode(404);
		assertEquals(r.body().asString(), projectDoesNotExistInDatabaseErrorMessage);
	}

	private void setupBacklog() {
		existingUser = generateUser();
		existingUser.setId(1L);

		List<User> users = new ArrayList<>();
		users.add(existingUser);

		existingProject = generateProject();
		existingProject.setId(1L);
        existingProject.setManager(existingUser);

		existingSprint = generateSprint();
		existingSprint.setProject(existingProject);
		existingSprint.setScrumMaster(existingUser);
		existingSprint.setId(1L);
		
		newSprint = generateSprint();
	}

	private void setupTestAccount() {
		User validUser = generateUser();
		account = new Account(validUser, "Password1");
	}
}
