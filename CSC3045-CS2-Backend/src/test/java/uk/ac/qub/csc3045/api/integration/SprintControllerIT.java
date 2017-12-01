package uk.ac.qub.csc3045.api.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static uk.ac.qub.csc3045.api.setup.UnitTestObjectGenerator.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

	private int nonExistingId = -1;

	private RequestHelper requestHelper;
	private String authHeader;

	private String sprintDoesNotExistErrorMessage = "Sprint does not exist in the database";
	private String projectDoesNotExistInDatabaseErrorMessage = "Project does not exist in the database";
	private String scrumMasterDoesNotExistInDatabaseErrorMessage = "Scrum Master does not exist in the database";
	private String sprintAndUserStoryHaveDifferentProjects = "Sprint and user story aren't in same Project";
	private String projectHasNoSprints = "There are currently no sprints in this project";
	
	private Account account;
	private Sprint sprint;
	private ArrayList<Sprint> sprintList = new ArrayList<>();
	private ArrayList<User> userList = new ArrayList<>();
	
	private Project existingProject;
	private User existingUser;

	@Before
	public void setup() throws IOException {
		requestHelper = new RequestHelper();

		setupTestAccount();
		setupSprints();

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
	 * Get Sprints For Project Tests
	 */
	
	@Test
	public void getProjectSprintsThatExistShouldReturn200() {
		Response r = requestHelper.sendGetRequestWithAuthHeader("/project/" + existingProject.getId() + "/sprint", authHeader);
		List<Sprint> returnedSprints = Arrays.asList(r.getBody().as(Sprint[].class));
		
		r.then().assertThat().statusCode(200);
		
		for (int i = 0; i < returnedSprints.size() - 1; i++) {
		    assertEquals(returnedSprints.get(i).getName(), sprintList.get(i).getName());
		    assertEquals(returnedSprints.get(i).getId(), sprintList.get(i).getId());
		}
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
	 * Get Sprint Team Tests
	 */
	
    @Test
    public void getSprintTeamShouldReturn200() {
        Response r = requestHelper.sendGetRequestWithAuthHeader("/project/" + existingProject.getId() + "/sprint/" + sprint.getId() + "/user", authHeader);
        List<User> returnedUsers = Arrays.asList(r.getBody().as(User[].class));
        
        r.then().assertThat().statusCode(200);
        
        for (int i = 0; i < returnedUsers.size() - 1; i++) {
            assertEquals(returnedUsers.get(i).getForename() + " " + returnedUsers.get(i).getForename(), userList.get(i).getForename() + " " + userList.get(i).getForename());
            assertEquals(returnedUsers.get(i).getId(), userList.get(i).getId());
        }
    }
	
    @Test
    public void getSprintTeamProjectDoesNotExistShouldReturn404() {
        Response r = requestHelper.sendGetRequestWithAuthHeader("/project/" + nonExistingId + "/sprint/" + sprint.getId() + "/user", authHeader);
        
        r.then().assertThat().statusCode(404);
        assertEquals(projectDoesNotExistInDatabaseErrorMessage, r.getBody().asString());
    }
    
    @Test
    public void getSprintTeamSprintDoesNotExistShouldReturn404() {
        Response r = requestHelper.sendGetRequestWithAuthHeader("/project/" + existingProject.getId() + "/sprint/" + nonExistingId + "/user", authHeader);
        
        r.then().assertThat().statusCode(404);
        assertEquals(sprintDoesNotExistErrorMessage, r.getBody().asString());
    }
    
    /**
     * Get Sprint Backlog
     */
    
    @Test
    public void getSprintBacklogShouldReturn200() {
        Response r = requestHelper.sendGetRequestWithAuthHeader("/project/" + existingProject.getId() + "/sprint/" + sprint.getId() + "/story", authHeader);
        List<UserStory> returnedStories = Arrays.asList(r.getBody().as(UserStory[].class));
        
        r.then().assertThat().statusCode(200);
        
        for (int i = 0; i < returnedStories.size() - 1; i++) {
            assertTrue(returnedStories.get(i).equals(sprint.getUserStories().get(i)));
        }
    }
    
    @Test
    public void getSprintBacklogProjectDoesNotExistShouldReturn404() {
        Response r = requestHelper.sendGetRequestWithAuthHeader("/project/" + nonExistingId + "/sprint/" + sprint.getId() + "/story", authHeader);
        
        r.then().assertThat().statusCode(404);
        assertEquals(projectDoesNotExistInDatabaseErrorMessage, r.getBody().asString());
    }
    
    @Test
    public void getSprintBacklogSprintDoesNotExistShouldReturn404() {
        Response r = requestHelper.sendGetRequestWithAuthHeader("/project/" + existingProject.getId() + "/sprint/" + nonExistingId + "/story", authHeader);
        
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
        Response r = requestHelper.sendPutRequestWithAuthHeader("/project/" + nonExistingId + "/sprint/" + sprint.getId() + "/story", authHeader, sprint);
       
        r.then().assertThat().statusCode(404);
        assertEquals(r.body().asString(), projectDoesNotExistInDatabaseErrorMessage);
    }

    /**
     * Get Available Developers Tests
     */
    
    @Test
    public void getAvailableDevelopersShouldReturn200() {
        
    }
    
    @Test
    public void getAvailableDevelopersProjectDoesNotExistShouldReturn404() {
        Response r = requestHelper.sendGetRequestWithAuthHeader("/project/" + nonExistingId + "/sprint/" + sprint.getId() + "/user", authHeader);
    }
    
    @Test
    public void getAvailableDevelopersSprintDoesNotExistShouldReturn404() {
        
    }
    
    @Test
    public void getAvailableDevelopersNodevelopersOnSprintShouldReturn404() {
        
    }
    
    /**
     * Update Sprint Team Tests
     */
    
    @Test
    public void updateSprintTeamShouldReturn200() {
        
    }
    
    @Test
    public void updateSprintTeamProjectDoesNotExistShouldReturn404() {
        
    }
    
	private void setupSprints() {
		existingUser = new User("Snoop", "Dogg", "snoop.dogg@shizzle.hold.up", "snoop.jpg", new Roles(true, true, true));
		existingUser.setId(1L);

		List<User> users = new ArrayList<>();
		users.add(existingUser);
		
		UserStory existingUserStory = new UserStory("Compress and upload a file", "Using the algorithm, a user should be able to upload a file to the cloud.", 8, 32, existingProject);
		List<UserStory> userStories = new ArrayList<>();
		userStories.add(existingUserStory);
		
		existingProject = new Project("Pied Piper", "Cloud storage using revolutionary middle-out compression.", existingUser, existingUser, users, users, userStories);
		existingProject.setId(1L);
        existingProject.setManager(existingUser);
        
		sprint = new Sprint("Sprint 1 - Compression", LocalDateTime.now(), LocalDateTime.now().plusDays(14));
		sprint.setProject(existingProject);
		sprint.setScrumMaster(existingUser);
		sprint.setUserStories(userStories);
		sprint.setId(1L);
		sprintList.add(sprint);
		
		Sprint sprint2 = new Sprint("Sprint 1 - Cloud", LocalDateTime.now(), LocalDateTime.now().plusDays(14));
		sprint2.setProject(existingProject);
        sprint2.setScrumMaster(existingUser);
        sprint2.setId(2L);
        sprintList.add(sprint2);
        
		Sprint sprint3 = new Sprint("Sprint 2 - Compression", LocalDateTime.now(), LocalDateTime.now().plusDays(14));
		sprint3.setProject(existingProject);
        sprint3.setScrumMaster(existingUser);
        sprint3.setId(3L);
        sprintList.add(sprint3);
        
        User user2 = new User("Richard", "Hendrix", "r.hendrix@valley.com", "richard_hendrix.jpg", new Roles(true, true, true));
        user2.setId(2L);
        userList.add(user2);
        
        User user3 = new User("Bertram", "Gilfoyle", "b.gilfoyle@valley.com", "bertram_gilfoyle.jpg", new Roles(true, true, false));
        user3.setId(3L);
        userList.add(user3);
        
        User user4 = new User("Jian", "Yang", "j.yang@valley.com", "jian_yang.jpg", new Roles(true, false, false));
        user4.setId(8L);
        userList.add(user4);
	}
	
	private void setupTestAccount() {
		User validUser = generateUser();
		account = new Account(validUser, "Password1");
	}
}
