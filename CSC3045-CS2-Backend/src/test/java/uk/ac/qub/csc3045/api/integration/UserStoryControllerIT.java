package uk.ac.qub.csc3045.api.integration;

import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import uk.ac.qub.csc3045.api.integration.util.RequestHelper;
import uk.ac.qub.csc3045.api.model.*;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserStoryControllerIT {

    private static final String STORY_BASE_PATH = "/story";
    private static final String GET_STORIES_PATH = STORY_BASE_PATH + "/project";
    private static final String UPDATE_BACKLOG_PATH = STORY_BASE_PATH + "/backlog/order";
    private static final String ACCEPTANCE_TEST_PATH_CRUMB = "/acceptancetest";

    private RequestHelper requestHelper;
    private String authHeader;

    private Account account;
    private Project existingProject;
    private List<UserStory> backlog;

    private String projectDoesNotExist = "Project does not exist";
    private String projectUserStoriesDontMatch = "These User Stories don't exist on the given Project";
    private String userStoryDoesNotExist = "User Story does not exist";
    private String acceptanceTestDoesNotExist = "Acceptance test does not exist";

    @Before
    public void setup() throws IOException {
        requestHelper = new RequestHelper();

        setupTestAccount();
        setupBacklog();

        authHeader = requestHelper.getAuthHeader(account);
    }

    /*
     * Successful tests
     */
    @Test
    public void createUserStoryShouldReturn201() {
        UserStory newStory = new UserStory("NewStory", "NewStoryDescription", 3, 30, existingProject);

        Response r = requestHelper.sendPostRequestWithAuthHeader("/project" + "/" + existingProject.getId() + "/story", authHeader, newStory);
        assertEquals(201, r.statusCode());
    }

    @Test
    public void getUserStoryByIdShouldReturn200() {
        Response r = requestHelper.sendGetRequestWithAuthHeader("/project" + "/" + existingProject.getId() + "/story" + "/" + backlog.get(0).getId(), authHeader);

        assertEquals(200, r.statusCode());
        assertTrue(backlog.get(0).equals(r.getBody().as(UserStory.class)));
    }

    @Test
    public void getUserStoriesShouldReturn200() {
        Response r = requestHelper.sendGetRequestWithAuthHeader("/project" + "/" + existingProject.getId() + "/story", authHeader);
        List<UserStory> userStories = Arrays.asList(r.getBody().as(UserStory[].class));

        assertEquals(200, r.statusCode());
        assertTrue(userStories.size() >= backlog.size());
        for (int i = 0; i < backlog.size(); i++) {
            assertTrue(backlog.get(i).equals(userStories.get(i)));
        }
    }

    @Test
    public void updateBacklogOrderShouldReturn200() {
        Collections.shuffle(backlog);

        Response r = requestHelper.sendPutRequestWithAuthHeader("/project" + "/" + existingProject.getId() + "/story", authHeader, backlog);
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
        Response r = requestHelper.sendGetRequestWithAuthHeader("/project" + "/" + existingProject.getId() + "/story" + "/1000", authHeader);

        assertEquals(404, r.statusCode());
        assertEquals(userStoryDoesNotExist, r.body().asString());
    }

    @Test
    public void createUserStoryProjectDoesNotExistShouldReturn404() {
        UserStory newStory = new UserStory("NewStory", "NewStoryDescription", 3, 30, new Project(1000));

        Response r = requestHelper.sendPostRequestWithAuthHeader("/project" + "/1000"  + "/story", authHeader, newStory);

        assertEquals(404, r.statusCode());
        assertEquals(projectDoesNotExist, r.body().asString());
    }

    @Test
    public void getAllStoriesProjectDoesNotExistShouldReturn404() {
        Response r = requestHelper.sendGetRequestWithAuthHeader("/project" + "/1000" + "/story", authHeader);

        assertEquals(404, r.statusCode());
        assertEquals(projectDoesNotExist, r.body().asString());
    }

    @Test
    public void updateBacklogOrderProjectDoesNotExistShouldReturn404() {
        backlog.get(0).getProject().setId(100L);

        Response r = requestHelper.sendPutRequestWithAuthHeader("/project" + "/1000" + "/story", authHeader, backlog);

        assertEquals(404, r.statusCode());
        assertEquals(projectDoesNotExist, r.body().asString());
    }

    @Test
    public void updateBacklogOrderProjectDoesNotContainUserStoriesShouldReturn404() {
        backlog.get(0).getProject().setId(2L);

        Response r = requestHelper.sendPutRequestWithAuthHeader("/project" + "/" + existingProject.getId() + "/story", authHeader, backlog);

        assertEquals(404, r.statusCode());
        assertEquals(projectUserStoriesDontMatch, r.body().asString());
    }

    @Test
    public void createAcceptanceTestForExistingUserStoryShouldReturn201() {
        AcceptanceTest acceptanceTest = new AcceptanceTest("This is an integration test", "The user sees this acceptance test", "It should work as any other");
        long storyId = 2L;

        Response r = requestHelper.sendPostRequestWithAuthHeader(STORY_BASE_PATH + "/" + storyId + ACCEPTANCE_TEST_PATH_CRUMB, authHeader, acceptanceTest);

        assertEquals(201, r.statusCode());
        assertEquals(acceptanceTest, r.body().as(AcceptanceTest.class));
    }

    @Test
    public void createAcceptanceTestForNonExistingUserStoryShouldReturn404() {
        AcceptanceTest acceptanceTest = new AcceptanceTest("This is an integration test", "The user sees this acceptance test", "It should work as any other");
        long storyId = -1L;

        Response r = requestHelper.sendPostRequestWithAuthHeader(STORY_BASE_PATH + "/" + storyId + ACCEPTANCE_TEST_PATH_CRUMB, authHeader, acceptanceTest);

        assertEquals(404, r.statusCode());
        assertEquals(userStoryDoesNotExist, r.body().asString());
    }

    @Test
    public void getAcceptanceTestForExistingUserStoryShouldReturn200() {
        long storyId = backlog.get(2).getId();
        Response r = requestHelper.sendGetRequestWithAuthHeader(STORY_BASE_PATH + "/" + storyId + ACCEPTANCE_TEST_PATH_CRUMB, authHeader);

        List<AcceptanceTest> responseAcceptanceTests = Arrays.asList(r.getBody().as(AcceptanceTest[].class));

        assertEquals(200, r.statusCode());
        assertEquals(backlog.get(2).getAcceptanceTests().size(), responseAcceptanceTests.size());
        assertEquals(backlog.get(2).getAcceptanceTests(), responseAcceptanceTests);
    }

    @Test
    public void getAcceptanceTestForNonExistingUserStoryShouldReturn404() {
        long storyId = -1L;
        Response r = requestHelper.sendGetRequestWithAuthHeader(STORY_BASE_PATH + "/" + storyId + ACCEPTANCE_TEST_PATH_CRUMB, authHeader);

        assertEquals(404, r.statusCode());
        assertEquals(userStoryDoesNotExist, r.body().asString());
    }

    @Test
    public void updateAcceptanceTestForExistingAcceptanceTestShouldReturn200() {
        AcceptanceTest acceptanceTest = new AcceptanceTest("There is internet connection", "The user has taken a photo and this was updated by a test", "The file should be automatically compressed and uploaded");
        acceptanceTest.setId(1L);

        Response r = requestHelper.sendPutRequestWithAuthHeader(STORY_BASE_PATH + ACCEPTANCE_TEST_PATH_CRUMB, authHeader, acceptanceTest);

        assertEquals(200, r.statusCode());
        assertEquals(acceptanceTest, r.body().as(AcceptanceTest.class));
    }

    @Test
    public void updateAcceptanceTestForNonExistingAcceptanceTestShouldReturn404() {
        AcceptanceTest acceptanceTest = new AcceptanceTest("There is internet connection", "The user has taken a photo and this was updated by a test", "The file should be automatically compressed and uploaded");
        acceptanceTest.setId(-1L);

        Response r = requestHelper.sendPutRequestWithAuthHeader(STORY_BASE_PATH + ACCEPTANCE_TEST_PATH_CRUMB, authHeader, acceptanceTest);

        assertEquals(404, r.statusCode());
        assertEquals(acceptanceTestDoesNotExist, r.body().asString());
    }

    private void setupBacklog() {
        User existingUser = new User("Forename1", "Surname1", "user1@email.com", new Roles(false, false, false));

        List<User> users = new ArrayList<>();
        users.add(existingUser);
        existingProject = new Project("ProjectName1", "Project Description1", existingUser, existingUser, users, users, new ArrayList<>());
        existingProject.setId(1L);

        UserStory story1 = new UserStory("Compress and upload a file", "Using the algorithm, a user should be able to upload a file to the cloud.", 8, 32, existingProject);
        story1.setId(1L);
        story1.setIndex(0);

        AcceptanceTest acceptanceTest1 = new AcceptanceTest("There is internet connection", "The user has taken a photo", "The file should be automatically compressed and uploaded");
        acceptanceTest1.setId(1L);

        List<AcceptanceTest> acceptanceTestsStory1 = new ArrayList<>();
        acceptanceTestsStory1.add(acceptanceTest1);
        story1.setAcceptanceTests(acceptanceTestsStory1);

        UserStory story2 = new UserStory("Download and decompress a file", "Using the algorithm, a user should be able to download a file from the cloud.", 8, 36, existingProject);
        story2.setId(2L);
        story2.setIndex(1);

        UserStory story3 = new UserStory("Auto-sync photos", "Auto-synchronisation should be an option to automatically upload photos taken on device.", 13, 55, existingProject);
        story3.setId(3L);
        story3.setIndex(2);

        AcceptanceTest acceptanceTest3 = new AcceptanceTest("There is internet connection", "The user has taken a photo", "The file should be automatically compressed and uploaded");
        acceptanceTest3.setId(3L);
        AcceptanceTest acceptanceTest4 = new AcceptanceTest("There is no internet connection", "The user has taken a photo", "The file should be added to a queue to be uploaded when connection is available");
        acceptanceTest3.setId(4L);

        List<AcceptanceTest> acceptanceTestsStory3 = new ArrayList<>();
        acceptanceTestsStory3.add(acceptanceTest3);
        acceptanceTestsStory3.add(acceptanceTest4);
        story3.setAcceptanceTests(acceptanceTestsStory3);

        UserStory story4 = new UserStory("Offline mode", "Saving files locally via the app to be access offline.", 5, 15, existingProject);
        story4.setId(4L);
        story4.setIndex(3);

        backlog = new ArrayList<>();
        backlog.add(story1);
        backlog.add(story2);
        backlog.add(story3);
        backlog.add(story4);
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
