package uk.ac.qub.csc3045.api.integration;

import static org.junit.Assert.*;

import java.io.IOException;
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
import uk.ac.qub.csc3045.api.model.User;
import uk.ac.qub.csc3045.api.model.UserStory;

public class ProjectControllerIT {
    
	private Account account;
	private Project existingProject;
	private RequestHelper requestHelper;
	private String authHeader;
	
	private String projectDoesNotExistErrorMessage = "Project does not exist";
	private String referentialIntegrityErrorMessage = "Project Manager does not exist in the database";
	
	public static final String BASE_PATH = "/project";
	public static final String TEAM_PATH = BASE_PATH + "/team";
	
	@Before
    public void setup() throws IOException {
        requestHelper = new RequestHelper();
        
        setupTestAccount();
        setupTestProject();
        
		authHeader = requestHelper.getAuthHeader(account);
    }
	
	/**
     * Get Project Tests
     */
	@Test
	public void getProjectThatExistsShouldReturn200() {
		Response r = requestHelper.sendGetRequestWithAuthHeader(BASE_PATH + "/" + existingProject.getId(), authHeader);
		Project returnedProject = r.getBody().as(Project.class);
		
		r.then().assertThat().statusCode(200);
		assertTrue(existingProject.getId() == returnedProject.getId());
	}
	
	@Test
	public void getProjectThatDoesntExistShouldReturn404() {
		Response r = requestHelper.sendGetRequestWithAuthHeader(BASE_PATH + "/6500", authHeader);
		
		r.then().assertThat().statusCode(404);
		assertEquals(projectDoesNotExistErrorMessage, r.getBody().asString());
	}
	
	/**
     * Create project tests
     */
	@Test
	public void createProjectShouldReturn201() {
	    Project newProject = new Project();
	    newProject.setName("ProjectName5000");
	    newProject.setDescription("Project Description5000");
	    User newUser = new User();
	    newUser.setId(1L);
	    newProject.setManager(newUser);
	    
		Response r = requestHelper.sendPostRequestWithAuthHeader(BASE_PATH, authHeader, newProject);
		Project returnedProject = r.getBody().as(Project.class);

		r.then().assertThat().statusCode(201);
		assertEquals(newProject.getName(), returnedProject.getName());
		assertEquals(newProject.getDescription(), returnedProject.getDescription());
	}
	
	@Test
	public void createProjectWithInvalidUserReferenceShouldReturn404() {
		User invalidManager = new User();
		invalidManager.setId(2000l);
		existingProject.setManager(invalidManager);
		Response r = requestHelper.sendPostRequestWithAuthHeader(BASE_PATH, authHeader, existingProject);
		
		r.then().assertThat().statusCode(404);
		assertEquals(referentialIntegrityErrorMessage, r.getBody().asString());
	}
	
	@Test
	public void getProjectTeamThatDoesNotExistShouldReturn404() {
		Response r = requestHelper.sendGetRequestWithAuthHeader(TEAM_PATH + "/6500", authHeader);
		
		r.then().assertThat().statusCode(404);
		assertEquals(projectDoesNotExistErrorMessage, r.getBody().asString());
	}
	
	@Test
	public void getProjectTeamThatDoesExistShouldReturn200() {
		Response r = requestHelper.sendGetRequestWithAuthHeader(TEAM_PATH + "/" + existingProject.getId(), authHeader);
		
		r.then().assertThat().statusCode(200);
		List<User> users = Arrays.asList(r.getBody().as(User[].class));
		assertEquals(2, users.size());
	}
	
	@Test
	public void addUsersToProjectTeamShouldReturn200() {
	    User existingUser1 = new User("Forename1", "Surname1", "user1@email.com", new Roles(false, false, false));
	    existingUser1.setId(1L);
	    User existingUser2 = new User("Forename2", "Surname2", "user2@email.com", new Roles(false, false, false));
	    existingUser2.setId(2L);
	    User existingUser3 = new User("Forename3", "Surname3", "user3@email.com", new Roles(false, false, false));
	    existingUser3.setId(3L);
	    
	    ArrayList<User> projectTeam = new ArrayList<>();
	    projectTeam.add(existingUser1);
	    projectTeam.add(existingUser2);
	    projectTeam.add(existingUser3);
	    
	    existingProject.setUsers(projectTeam);
	    existingProject.setId(3L);

		Response r = requestHelper.sendPostRequestWithAuthHeader(TEAM_PATH, authHeader, existingProject);
		Project returnedProject = r.getBody().as(Project.class);
		List<User> returnedTeam = returnedProject.getUsers();
		
		r.then().assertThat().statusCode(200);
		for (int i = 0; i < returnedTeam.size(); i++) {
		    assertTrue(returnedTeam.get(i).equals(projectTeam.get(i)));
		}
	}
	
	/**
     * Setup Test Data
     */
    public void setupTestProject() {
        User existingUser = new User("Forename1", "Surname1", "user1@email.com", new Roles(false, false, false));
        List<User> users = new ArrayList<>();
        users.add(existingUser);
        existingProject = new Project("ProjectName1", "Project Description1", existingUser, existingUser, existingUser, users, new ArrayList<UserStory>());
        existingProject.setId(1L);
    }
    
    private void setupTestAccount() {
		Roles validRoles = new Roles();
		User validUser = new User("Forename", "Surname", generateEmail(), validRoles);
		account = new Account(validUser, generateUsername(), "Password1");
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
