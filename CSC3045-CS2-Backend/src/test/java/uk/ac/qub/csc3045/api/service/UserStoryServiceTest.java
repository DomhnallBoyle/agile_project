package uk.ac.qub.csc3045.api.service;
import org.junit.Before;
import org.junit.Test;
import uk.ac.qub.csc3045.api.exception.ResponseErrorException;
import uk.ac.qub.csc3045.api.mapper.ProjectMapper;
import uk.ac.qub.csc3045.api.mapper.UserStoryMapper;
import uk.ac.qub.csc3045.api.model.AcceptanceTest;
import uk.ac.qub.csc3045.api.model.Project;
import uk.ac.qub.csc3045.api.model.Sprint;
import uk.ac.qub.csc3045.api.model.UserStory;
import static uk.ac.qub.csc3045.api.setup.UnitTestObjectGenerator.*;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserStoryServiceTest {
	
	private UserStoryMapper userStoryMapper;
	private ProjectMapper projectMapper;
	private UserStoryService userStoryService;
	private UserStory userStory;
	private Project project;
	private Sprint sprint;
	private AcceptanceTest acceptanceTest;
	private List<UserStory>userStories;

	@Before
	public void setup(){
		userStory = generateUserStory();
		sprint = generateSprint();
		project = generateProject();
		acceptanceTest = new AcceptanceTest();
		acceptanceTest.setId(1l);
		userStory.setSprint(sprint);
		userStory.setProject(project);
		userStories = new ArrayList<>();
        userStories.add(userStory);
		project.setUserStories(userStories);
    	projectMapper = mock(ProjectMapper.class);
    	userStoryMapper = mock(UserStoryMapper.class);
        userStoryService = new UserStoryService(userStoryMapper, projectMapper);

	}
	
	@Test
    public void testCreateUserStoryReturnsCreatedUserStory() {
		when(projectMapper.getProjectById(project.getId())).thenReturn(project);
	    when(userStoryMapper.getUserStoriesByProject(project.getId())).thenReturn(userStories);
	    when(userStoryMapper.getUserStoryById(userStory.getId())).thenReturn(userStory);
	    UserStory response = userStoryService.create(userStory);
	
	    assertTrue(response.equals(userStory));
	}
	@Test()
	public void testCreateUserStoryThrowsExceptionWhenProjectNull() {
		when(projectMapper.getProjectById(project.getId())).thenReturn(null);
	     try {
				userStoryService.create(userStory);
			}
		    catch(ResponseErrorException e) {
		    	assertTrue(e.getMessage() == "Project does not exist");
		    }
	
	}
	@Test
	public void testGetUserStoryReturnsAUserStoryById() {
		when(projectMapper.getProjectById(project.getId())).thenReturn(project);
		when(userStoryMapper.getUserStoryById(userStory.getSprint().getId())).thenReturn(userStory);
		UserStory response = userStoryService.getUserStory(project.getId(), userStory.getSprint().getId());
		
		assertTrue(response.equals(userStory));
	}
	@Test()
	public void testGetUserStoryThrowsExceptionWhenProjectNull() {
		 when(projectMapper.getProjectById(project.getId())).thenReturn(null);
		 when(userStoryMapper.getUserStoryById(userStory.getSprint().getId())).thenReturn(userStory);
		 
		 try {
			 userStoryService.getUserStory(project.getId(), userStory.getSprint().getId());
			}
		 catch(ResponseErrorException e) {
		    	assertTrue(e.getMessage() == "Project does not exist");
		    }

	}
	@Test()
	public void testGetUserStoryThrowsExceptionWhenStoryNull() {
		when(projectMapper.getProjectById(project.getId())).thenReturn(project);
		when(userStoryMapper.getUserStoryById(userStory.getSprint().getId())).thenReturn(null);
		 try {
			 userStoryService.getUserStory(project.getId(), userStory.getSprint().getId());
			}
		 catch(ResponseErrorException e) {
		    	assertTrue(e.getMessage() == "The User story does not exist");
		    }
		
	}
	@Test
	public void testAddAcceptanceTestReturnsAcceptanceTestById() {
		when(projectMapper.getProjectById(project.getId())).thenReturn(project);
		when(userStoryMapper.getUserStoryById(userStory.getId())).thenReturn(userStory);
		when(userStoryMapper.getAcceptanceTestById(acceptanceTest.getId())).thenReturn(acceptanceTest);
		AcceptanceTest response = userStoryService.addAcceptanceTest(project.getId(), userStory.getId(), acceptanceTest);
		assertTrue(response.equals(acceptanceTest));
	}
	@Test ()
	public void testAddAcceptanceTestThrowsExceptionWhenProjectNull() {
		when(projectMapper.getProjectById(project.getId())).thenReturn(null);
		 try {
			 userStoryService.addAcceptanceTest(project.getId(), userStory.getId(), acceptanceTest);
			}
		 catch(ResponseErrorException e) {
		    	assertTrue(e.getMessage() == "Project does not exist");
		    }

	}
	@Test ()
	public void testAddAcceptanceTestThrowsExceptionWhenUserStoryNull() {
		when(projectMapper.getProjectById(project.getId())).thenReturn(project);
		when(userStoryMapper.getUserStoryById(userStory.getId())).thenReturn(null);
		 try {
			 userStoryService.addAcceptanceTest(project.getId(), userStory.getId(), acceptanceTest);
			}
		 catch(ResponseErrorException e) {
			 	System.out.print(e.getMessage());
		    	assertTrue(e.getMessage() == "The User story does not exist");
		    }
	}

	@Test ()
	public void testAddAcceptanceTestThrowsExceptionWhenAcceptanceTestNull() {
		when(projectMapper.getProjectById(project.getId())).thenReturn(project);
		when(userStoryMapper.getUserStoryById(userStory.getId())).thenReturn(null);
		when(userStoryMapper.getAcceptanceTestById(acceptanceTest.getId())).thenReturn(null);
		
		 try {
			 userStoryService.addAcceptanceTest(project.getId(), userStory.getId(), acceptanceTest);
			}
		 catch(ResponseErrorException e) {
			 	System.out.print(e.getMessage());
		    	assertTrue(e.getMessage() == "Project does not exist");
		    }
	}
	
	@Test
	public void testGetAllUserStoriesReturnsAllUserStoriesFromProject() {
		when(projectMapper.getProjectById(project.getId())).thenReturn(project);
        when(userStoryMapper.getUserStoriesByProject(project.getId())).thenReturn(userStories);
        List<UserStory> response = userStoryService.getAllUserStories(project.getId());

        assertTrue(response.equals(userStories));
	}
	@Test()
	 public void testGetAllUserStoriesThrowsExceptionWhenProjectNull() {
		 when(projectMapper.getProjectById(project.getId())).thenReturn(null);
	     
		 try {
			 userStoryService.getAllUserStories(project.getId());
			}
		 catch(ResponseErrorException e) {
			 	System.out.print(e.getMessage());
		    	assertTrue(e.getMessage() == "Project does not exist");
		 }  
	 }
	 @Test
	 public void testGetAvailableUserStoriesReturnsStoriesThatAreAvailable() {
			when(projectMapper.getProjectById(project.getId())).thenReturn(project);
			when(userStoryMapper.getAvailableUserStories(project.getId())).thenReturn(userStories);
			List<UserStory> response = userStoryService.getAvailableUserStories(project.getId());
			assertTrue(response.equals(userStories));
	 }
	 @Test()
	 public void testGetAvailableUserStoriesThrowsExceptionWhenProjectDoesntExist() {
		when(projectMapper.getProjectById(project.getId())).thenReturn(null);
		when(userStoryMapper.getAvailableUserStories(project.getId())).thenReturn(userStories);
	
		 try {
			 userStoryService.getAvailableUserStories(project.getId());
			}
		 catch(ResponseErrorException e) {
			 	System.out.print(e.getMessage());
		    	assertTrue(e.getMessage() == "Project does not exist");
		 }  
	 }
	 @Test()
	 public void testGetAvailableUserStoriesThrowsExceptionWhenNoAvailableUserStories() {
		 	when(projectMapper.getProjectById(project.getId())).thenReturn(project);
			when(userStoryMapper.getAvailableUserStories(project.getId())).thenReturn(null);
			try {
				userStoryService.getAvailableUserStories(project.getId());
				}
			 catch(ResponseErrorException e) {
			    	assertTrue(e.getMessage() == "The User story does not exist");
			    }
	 }

}

	 