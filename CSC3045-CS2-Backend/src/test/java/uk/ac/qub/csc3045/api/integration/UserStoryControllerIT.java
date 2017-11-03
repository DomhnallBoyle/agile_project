package uk.ac.qub.csc3045.api.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;
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
	
	private RequestHelper requestHelper;
	private UserStory userStory;
	private Account account;
	private String projectDoesNotExist = "Project does not exist";
	private String userStoryDoesNotExist = "User Story does not exist";
	
	@Before
	public void setup() throws IOException {
		requestHelper = new RequestHelper();
		User user = new User("Forename", "Surname", "fake_email@hotmail.com", new Roles());
		account = new Account(user, "Username1", "Password1");
		userStory = new UserStory("Create UI", "Create UI using WPF", 10, 200, false, new Project(1));
	}

	/*
	 * Successful tests
	 */
	@Test
	public void createUserStoryShouldReturn201() {
		String authHeader = requestHelper.GetAuthHeader(account);
		
		Response r = requestHelper.SendPostRequestWithAuthHeader(STORY_BASE_PATH, authHeader, userStory);
		assertEquals(r.statusCode(), 201);
		assertEquals(r.getBody().as(UserStory.class).getName(), userStory.getName());
	}
	
	@Test
	public void getUserStoryByIdShouldReturn200() {
		String authHeader = requestHelper.GetAuthHeader(account);

		Response r = requestHelper.SendGetRequestWithAuthHeader(STORY_BASE_PATH + "/1", authHeader);
		assertEquals(r.statusCode(), 200);
		assertEquals(r.getBody().as(UserStory.class).getName(), "Light Wildfire");
	}
	
	@Test
	public void getUserStoriesShouldReturn200() {
		String authHeader = requestHelper.GetAuthHeader(account);

		Response r = requestHelper.SendGetRequestWithAuthHeader(GET_STORIES_PATH + "/2", authHeader);
		List<UserStory> userStories = Arrays.asList(r.getBody().as(UserStory[].class));

		assertEquals(r.statusCode(), 200);
		assertEquals(userStories.size(), 2);
		for (int i=0; i<userStories.size(); i++) {
			assertEquals(userStories.get(i).getProject().getId().intValue(), 2);
			assertTrue(userStories.get(i).getName().contains("Wildfire"));
		}
	}
	
	/*
	 * Unsuccessful tests
	 */
	@Test
	public void getUserStoryByIdDoesNotExistShouldReturn200() {
		String authHeader = requestHelper.GetAuthHeader(account);

		Response r = requestHelper.SendGetRequestWithAuthHeader(STORY_BASE_PATH + "/100", authHeader);
		
		assertEquals(r.statusCode(), 404);
		assertEquals(r.body().asString(), userStoryDoesNotExist);
	}
	
	@Test
	public void createUserStoryProjectDoesNotExistShouldReturn404() {
		String authHeader = requestHelper.GetAuthHeader(account);
		userStory.getProject().setId((long) 100);
		
		Response r = requestHelper.SendPostRequestWithAuthHeader(STORY_BASE_PATH, authHeader, userStory);
		
		assertEquals(r.statusCode(), 404);
		assertEquals(r.body().asString(), projectDoesNotExist);
	}
	
	@Test
	public void getAllStoriesProjectDoesNotExistShouldReturn404() {
		String authHeader = requestHelper.GetAuthHeader(account);

		Response r = requestHelper.SendGetRequestWithAuthHeader(GET_STORIES_PATH + "/100", authHeader);

		assertEquals(r.statusCode(), 404);
		assertEquals(r.body().asString(), projectDoesNotExist);
	}
}
