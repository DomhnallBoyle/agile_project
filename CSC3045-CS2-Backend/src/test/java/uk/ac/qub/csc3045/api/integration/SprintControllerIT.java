package uk.ac.qub.csc3045.api.integration;

import static org.junit.Assert.assertEquals;
import static uk.ac.qub.csc3045.api.setup.UnitTestObjectGenerator.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import io.restassured.response.Response;
import uk.ac.qub.csc3045.api.integration.util.RequestHelper;
import uk.ac.qub.csc3045.api.model.Account;
import uk.ac.qub.csc3045.api.model.Project;
import uk.ac.qub.csc3045.api.model.Sprint;
import uk.ac.qub.csc3045.api.model.User;
import uk.ac.qub.csc3045.api.model.UserStory;

public class SprintControllerIT {

	private int nonExistingId = -1;

	private RequestHelper requestHelper;
	private String authHeader;

	private String sprintDoesNotExistErrorMessage = "Sprint does not exist in the database";
	private String projectDoesNotExistInDatabaseErrorMessage = "Project does not exist in the database";
	private String scrumMasterDoesNotExistInDatabaseErrorMessage = "Scrum Master does not exist in the database";
	private String sprintAndUserStoryHaveDifferentProjects = "Sprint and user story aren't in same Project";
	private String projectHasNoSprints = "There are currently no sprints in this project";

	private String existingProjectFirstSprintName;
	
	private Account account;
	private Sprint sprint;
	
	private Project existingProject;
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
		Response r = requestHelper.sendPostRequestWithAuthHeader("/project/" + existingProject.getId() + "/sprint", authHeader, sprint);

		assertEquals(201, r.statusCode());
		assertEquals(sprint.getName(), r.getBody().as(Sprint.class).getName());
	}

	@Test
	public void createSprintProjectDoesNotExistShouldReturn404() {
		Response r = requestHelper.sendPostRequestWithAuthHeader("/project/" + nonExistingId + "/sprint", authHeader, sprint);

		assertEquals(404, r.statusCode());
		assertEquals(projectDoesNotExistInDatabaseErrorMessage, r.body().asString());
	}
	
	@Test
	public void createSprintScrumMasterDoesNotExistShouldReturn400() {
		User nonExistingScrumMaster = new User();
		nonExistingScrumMaster.setId(-1L);
		sprint.setScrumMaster(nonExistingScrumMaster);
	    
		Response r = requestHelper.sendPostRequestWithAuthHeader("/project/" + existingProject.getId() + "/sprint", authHeader, sprint);

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
		
		assertEquals(existingProjectFirstSprintName, returnedSprints.get(0).getName());
	}
	
	@Test
	public void getProjectSprintsThatDontExistShouldReturn404() {
		Project projectNoSprints = generateProject();
		projectNoSprints.setId(5L);
		projectNoSprints.setManager(existingUser);
		
		Response r = requestHelper.sendGetRequestWithAuthHeader("/project/" + projectNoSprints.getId() + "/sprint", authHeader);
		
		r.then().assertThat().statusCode(404);
		assertEquals(projectHasNoSprints, r.getBody().asString());
	}
	
	@Test
	public void getProjectSprintsWhereProjectDoesNotExistShouldReturn404() {
		Response r = requestHelper.sendGetRequestWithAuthHeader("/project/" + nonExistingId + "/sprint", authHeader);
		
		r.then().assertThat().statusCode(404);
		assertEquals(projectDoesNotExistInDatabaseErrorMessage, r.getBody().asString());
	}
	
	/**
	 * Get Sprint Tests
	 */
	
	@Test
	public void getSprintThatExistsShouldReturn200() {
		Response r = requestHelper.sendGetRequestWithAuthHeader("/project/" + existingProject.getId() + "/sprint/" + sprint.getId() , authHeader);
		Sprint returnedSprint = r.getBody().as(Sprint.class);

		r.then().assertThat().statusCode(200);
		assertEquals(sprint.getId(), returnedSprint.getId());
	}

	@Test
	public void getSprintWhereProjectDoesntExistShouldReturn404() {
		Response r = requestHelper.sendGetRequestWithAuthHeader("/project/" + nonExistingId + "/sprint/" + sprint.getId(), authHeader);

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
		sprint.setUserStories(sprintBacklog);
		
		assertEquals(sprint.getUserStories().size(), 1);
		Response r = requestHelper.sendPutRequestWithAuthHeader("/project/" + existingProject.getId() + "/sprint/" + sprint.getId() + "/story", authHeader, sprint);
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
		sprint.setUserStories(sprintBacklog);
		
		assertEquals(sprint.getUserStories().size(), 1);
		Response r = requestHelper.sendPutRequestWithAuthHeader("/project/" + existingProject.getId() + "/sprint/" + sprint.getId() + "/story", authHeader, sprint);
		
		r.then().assertThat().statusCode(404);
		assertEquals(r.body().asString(), sprintAndUserStoryHaveDifferentProjects);
	}
	
	@Test
	public void updateSprintBacklogSprintDoesNotExistShouldReturn404() {
		sprint.setId(-1l);
		
		Response r = requestHelper.sendPutRequestWithAuthHeader("/project/" + existingProject.getId() + "/sprint/" + sprint.getId() + "/story", authHeader, sprint);
		
		r.then().assertThat().statusCode(404);
		assertEquals(r.body().asString(), sprintDoesNotExistErrorMessage);
	}
	
	@Test
	public void updateSprintBacklogProjectDoesNotExistShouldReturn404() {
		Response r = requestHelper.sendPutRequestWithAuthHeader("/project/-1/sprint/" + sprint.getId() + "/story", authHeader, sprint);
		
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
        
		sprint = generateSprint();
		sprint.setProject(existingProject);
		sprint.setScrumMaster(existingUser);
		sprint.setId(1L);
		
		setupValidAssertProperties();
	}

	private void setupValidAssertProperties() {
		existingProjectFirstSprintName = "Sprint 1 - Compression";
	}
	
	private void setupTestAccount() {
		User validUser = generateUser();
		account = new Account(validUser, "Password1");
	}
}
