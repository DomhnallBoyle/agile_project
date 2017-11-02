package uk.ac.qub.csc3045.api.integration;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import io.restassured.response.Response;
import uk.ac.qub.csc3045.api.integration.util.RequestHelper;
import uk.ac.qub.csc3045.api.model.Account;
import uk.ac.qub.csc3045.api.model.Project;
import uk.ac.qub.csc3045.api.model.Roles;
import uk.ac.qub.csc3045.api.model.User;

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
		Response r = request.SendGetRequestWithAuthHeader("/project/" + 1, authHeader);
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
	public void createProjectWithInvalidOwnerShouldReturn400() {
		User invalidManager = new User();
		invalidManager.setId(2000l);
		validProject.setManager(invalidManager);
		Response r = request.SendPostRequestWithAuthHeader("/project", authHeader, validProject);
		r.then().assertThat().statusCode(400);
	}
	
	@Test
	public void createProjectWithInvalidManagerShouldReturn400() {
		User invalidManager = new User();
		invalidManager.setId(2000l);
		validProject.setManager(invalidManager);
		Response r = request.SendPostRequestWithAuthHeader("/project", authHeader, validProject);
		r.then().assertThat().statusCode(400);
	}
	
	/**
     * Setup Test Data
     */

    public void setupTestProject() {
    	User projectManager = new User();
    	projectManager.setId(1l);
    	User productOwner = new User();
    	productOwner.setId(2l);
    	User teamMember = new User();
    	ArrayList<User> teamMembers = new ArrayList<User>();
    	teamMembers.add(teamMember);
    	
    	validProject = new Project();
    	validProject.setName("Kingdom");
    	validProject.setDescription("Command 7 kingdoms");
    	validProject.setId(1l);
    	validProject.setProductOwner(productOwner);
    	validProject.setUsers(teamMembers);
    	validProject.setManager(projectManager);
    }

}
