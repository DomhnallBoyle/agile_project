package uk.ac.qub.csc3045.api.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import io.restassured.response.Response;
import uk.ac.qub.csc3045.api.integration.util.RequestHelper;
import uk.ac.qub.csc3045.api.model.Account;
import uk.ac.qub.csc3045.api.model.Project;
import uk.ac.qub.csc3045.api.model.Roles;
import uk.ac.qub.csc3045.api.model.User;
import uk.ac.qub.csc3045.api.model.UserStory;

public class UserStoryControllerIT {
	
	private static final String STORY_BASE_PATH = "/story";
	private static final String GET_STORIES_PATH = STORY_BASE_PATH + "/project";
	private static final String UPDATE_BACKLOG_PATH = STORY_BASE_PATH + "/backlog/order";
	
	private RequestHelper requestHelper;
	private String authHeader;
	
	private Account account;
	private List<UserStory> backlog;
	private UserStory userStory;

	private String projectDoesNotExist = "Project does not exist";
	private String projectUserStoriesDontMatch = "These User Stories don't exist on the given Project";
	private String userStoryDoesNotExist = "User Story does not exist";
	
	@Before
	public void setup() throws IOException {
		requestHelper = new RequestHelper();
		
		User user = new User("Forename", "Surname", "fake_email@hotmail.com", new Roles());
		account = new Account(user, "Username1", "Password1");
		userStory = new UserStory("Create UI", "Create UI using WPF", 10, 200, false, new Project(1));
		
		authHeader = requestHelper.GetAuthHeader(account);
		
		setupBacklog();
	}

	/*
	 * Successful tests
	 */
	@Test
	public void createUserStoryShouldReturn201() {
		Response r = requestHelper.SendPostRequestWithAuthHeader(STORY_BASE_PATH, authHeader, userStory);
		assertEquals(201, r.statusCode());
		assertEquals(userStory.getName(), r.getBody().as(UserStory.class).getName());
	}
	
	@Test
	public void getUserStoryByIdShouldReturn200() {
		Response r = requestHelper.SendGetRequestWithAuthHeader(STORY_BASE_PATH + "/1", authHeader);
		assertEquals(200, r.statusCode());
		assertEquals("Light Wildfire", r.getBody().as(UserStory.class).getName());
	}
	
	@Test
	public void getUserStoriesShouldReturn200() {
		Response r = requestHelper.SendGetRequestWithAuthHeader(GET_STORIES_PATH + "/2", authHeader);
		List<UserStory> userStories = Arrays.asList(r.getBody().as(UserStory[].class));

		assertEquals(200, r.statusCode());
		assertEquals(3, userStories.size());
		for (int i=0; i<userStories.size(); i++) {
			assertEquals(userStories.get(i).getProject().getId().intValue(), 2);
			assertTrue(userStories.get(i).getName().contains("Wildfire"));
		}
	}
	
	@Test
	public void updateBacklogOrderShouldReturn200() {
		Collections.shuffle(backlog);
		
		Response r = requestHelper.SendPutRequestWithAuthHeader(UPDATE_BACKLOG_PATH, authHeader, backlog);
		List<UserStory> returnedBacklog = Arrays.asList(r.getBody().as(UserStory[].class));
		
		assertEquals(200, r.statusCode());
		for (int i = 0; i < backlog.size(); i++) {
			assertTrue(backlog.get(i).equals(returnedBacklog.get(i)));
		}
	}
	
	/*
	 * Unsuccessful tests
	 */
	@Test
	public void getUserStoryByIdDoesNotExistShouldReturn404() {
		Response r = requestHelper.SendGetRequestWithAuthHeader(STORY_BASE_PATH + "/100", authHeader);
		
		assertEquals(404, r.statusCode());
		assertEquals(userStoryDoesNotExist, r.body().asString());
	}
	
	@Test
	public void createUserStoryProjectDoesNotExistShouldReturn404() {
		userStory.getProject().setId(100L);
		
		Response r = requestHelper.SendPostRequestWithAuthHeader(STORY_BASE_PATH, authHeader, userStory);
		
		assertEquals(404, r.statusCode());
		assertEquals(projectDoesNotExist, r.body().asString());
	}
	
	@Test
	public void getAllStoriesProjectDoesNotExistShouldReturn404() {
		Response r = requestHelper.SendGetRequestWithAuthHeader(GET_STORIES_PATH + "/100", authHeader);

		assertEquals(404, r.statusCode());
		assertEquals(projectDoesNotExist, r.body().asString());
	}
	
	@Test
	public void updateBacklogOrderProjectDoesNotExistShouldReturn404() {
		backlog.get(0).getProject().setId(100L);
		
		Response r = requestHelper.SendPutRequestWithAuthHeader(UPDATE_BACKLOG_PATH, authHeader, backlog);
		
		assertEquals(404, r.statusCode());
		assertEquals(projectDoesNotExist, r.body().asString());
	}
	
	@Test
	public void updateBacklogOrderProjectDoesNotContainUserStoriesShouldReturn404() {
		backlog.get(0).getProject().setId(2L);
		
		Response r = requestHelper.SendPutRequestWithAuthHeader(UPDATE_BACKLOG_PATH, authHeader, backlog);
		
		assertEquals(404, r.statusCode());
		assertEquals(projectUserStoriesDontMatch, r.body().asString());
	}
	
	private void setupBacklog() {	
		Roles roles = new Roles();		
		User user = new User("Daenerys", "Targaryen", "dany.targaryen@got.wes", roles);
		
		Project project = new Project();
		project.setId(1L);
		project.setName("Winter is Coming");
		project.setDescription("Defend Westeros from the White Walkers");
		project.setManager(user);
		project.setProductOwner(user);
		
		UserStory story1 = new UserStory("Defend the Nights Watch", "The wall must be defended", 15L, 40L, false, project);
		story1.setId(4L);
		story1.setIndex(0);
		UserStory story2 = new UserStory("Kill wights", "Wights must be burned", 10L, 21L, false, project);
		story2.setId(5L);
		story2.setIndex(1);
		UserStory story3 = new UserStory("Kill White Walkers", "Walkers must be killed with dragonglass", 5L, 30L, false, project);
		story3.setId(6L);
		story3.setIndex(2);
		
		backlog = new ArrayList<>();
		backlog.add(story1);
		backlog.add(story2);
		backlog.add(story3);
	}
}
