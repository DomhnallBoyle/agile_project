package uk.ac.qub.csc3045.api.integration;

import static org.junit.Assert.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.validation.Valid;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.restassured.response.Response;
import uk.ac.qub.csc3045.api.integration.util.RequestHelper;
import uk.ac.qub.csc3045.api.model.Account;
import uk.ac.qub.csc3045.api.model.Project;
import uk.ac.qub.csc3045.api.model.Roles;
import uk.ac.qub.csc3045.api.model.User;
import uk.ac.qub.csc3045.api.model.UserStory;

public class ProjectControllerIT {
	private Account account;
	private RequestHelper request;
	private Project validProject;
	private String authHeader;
	public static final String BASE_PATH = "/projects";
	public static final String AUTHENTICATION_PATH = "/authentication";
	public static final String REGISTER_PATH = AUTHENTICATION_PATH + "/register";
    public static final String LOGIN_PATH = AUTHENTICATION_PATH + "/login";
	
	@Before
    public void setup() throws IOException {
        request = new RequestHelper();
        setupTestAccount();
        setupTestProject();
        request.SendPostRequest(REGISTER_PATH, account);
		Response r = request.SendPostRequest(LOGIN_PATH, account);
		authHeader = r.getHeader("Authorization");
    }
	
	/**
     * Get Project Tests
     */
	@Test
	public void getProjectThatExistsShouldReturn200() {
		Response r = request.SendGetRequestWithAuthHeader("/project/" + validProject.getId(), authHeader);
		Project returnedProject = r.body().jsonPath().getObject("", Project.class);
		r.then().assertThat().statusCode(200);
		
		assertTrue(validProject.getName().equals(returnedProject.getName()));
		assertTrue(validProject.getDescription().equals(returnedProject.getDescription()));
		assertTrue(validProject.getProductOwner().getId().equals(returnedProject.getProductOwner().getId()));
		assertTrue(validProject.getManager().getId().equals(returnedProject.getManager().getId()));
	}
	
	@Test
	public void getProjectThatDoesntExistShouldReturn404() {
		Response r = request.SendGetRequestWithAuthHeader("/project/100", authHeader);
		r.then().assertThat().statusCode(404);
	}
	
	/**
     * Create project tests
     */
	@Test
	public void createProjectShouldReturn201() {
		Response r = request.SendPostRequestWithAuthHeader("/project", authHeader, validProject);
		Project returnedProject = r.body().jsonPath().getObject("", Project.class);
		r.then().assertThat().statusCode(201);
		
		assertTrue(validProject.getName().equals(returnedProject.getName()));
		assertTrue(validProject.getDescription().equals(returnedProject.getDescription()));
		assertTrue(validProject.getProductOwner().getId().equals(returnedProject.getProductOwner().getId()));
		assertTrue(validProject.getManager().getId().equals(returnedProject.getManager().getId()));
	}
	
	@Test
	public void createProjectWithInvalidOwnerShouldReturn404() {
		User invalidManager = new User();
		invalidManager.setId(2000l);
		validProject.setManager(invalidManager);
		Response r = request.SendPostRequestWithAuthHeader("/project", authHeader, validProject);
		r.then().assertThat().statusCode(404);
	}
	
	@Test
	public void createProjectWithInvalidManagerShouldReturn404() {
		User invalidManager = new User();
		invalidManager.setId(2000l);
		validProject.setManager(invalidManager);
		Response r = request.SendPostRequestWithAuthHeader("/project", authHeader, validProject);
		r.then().assertThat().statusCode(404);
	}
	@Test
	public void getProjectTeamThatDoesntExistsShouldReturn404() {
		double invalidNumber = 100000+validProject.getId();
		Response r = request.SendGetRequestWithAuthHeader("/team/" +invalidNumber, authHeader);
		r.then().assertThat().statusCode(404);
	}
	@Test
	public void getProjectTeamThatDoesExistShouldReturn200() {
		Response r = request.SendGetRequestWithAuthHeader("/project/team/"+validProject.getId(), authHeader);
		r.then().assertThat().statusCode(200);
		List<User> users = Arrays.asList(r.getBody().as(User[].class));
		assertEquals(users.size(), 2);
	}
	@Test
	public void addUsersToProjectTeamShouldReturn200() {
		User teamMember = new User("Ragnar", "Lothbrok", "ragnar.lothbrok@valhalla.odin", new Roles(true, true, false));
    	ArrayList<User> teamMembers = new ArrayList<User>();
    	teamMember.setId(1l);
    	teamMembers.add(teamMember);
    	validProject.setUsers(teamMembers);
		Response r = request.SendPostRequestWithAuthHeader("/project/team/", authHeader, validProject);
		r.then().assertThat().statusCode(200);
		assertEquals(r.getBody().as(Project.class).getName(), "Kingdom");
	}
	
	/**
     * Setup Test Data
     */

    public void setupTestProject() {
    //	Roles validRoles = new Roles();
    	User projectManager = new User();
    	projectManager.setId(1l);
    	User productOwner = new User();
    	productOwner.setId(2l);
    	User teamMember = new User("Ragnar", "Lothbrok", "ragnar.lothbrok@valhalla.odin", new Roles(true, true, false));
    	ArrayList<User> teamMembers = new ArrayList<User>();
    	teamMembers.add(teamMember);
    	
    	validProject = new Project();
    	validProject.setName("Kingdom");
    	validProject.setDescription("Command 7 kingdoms");
    	validProject.setId(3l);
    	validProject.setProductOwner(productOwner);
    	validProject.setUsers(teamMembers);
    	validProject.setManager(projectManager);
    }
    
    public void setupTestAccount() {
		Roles validRoles = new Roles();
		User validUser = new User("Forename", "Surname", generateEmail(), validRoles);
		account = new Account(validUser, generateUsername(), "Password1");
	}

	/**
	 * Generates a random username
	 * 
	 * @return the username generated
	 */
	private String generateUsername() {
		Random random = new Random();
		return "test" + random.nextInt(5000);
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
