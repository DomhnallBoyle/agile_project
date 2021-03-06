package uk.ac.qub.csc3045.api.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static uk.ac.qub.csc3045.api.setup.UnitTestObjectGenerator.generateProject;
import static uk.ac.qub.csc3045.api.setup.UnitTestObjectGenerator.generateSprint;
import static uk.ac.qub.csc3045.api.setup.UnitTestObjectGenerator.generateUserStory;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import uk.ac.qub.csc3045.api.exception.ResponseErrorException;
import uk.ac.qub.csc3045.api.mapper.ProjectMapper;
import uk.ac.qub.csc3045.api.mapper.SprintMapper;
import uk.ac.qub.csc3045.api.mapper.TaskMapper;
import uk.ac.qub.csc3045.api.mapper.UserStoryMapper;
import uk.ac.qub.csc3045.api.model.Project;
import uk.ac.qub.csc3045.api.model.Sprint;
import uk.ac.qub.csc3045.api.model.Task;
import uk.ac.qub.csc3045.api.model.UserStory;
import static uk.ac.qub.csc3045.api.setup.UnitTestObjectGenerator.*;

public class TaskServiceTest {
	
    private String projectDoesNotExistErrorMessage = "Project does not exist in the database";
    private String userStoryDoesNotExistErrorMessage ="User Story does not exist in the database";
    private String taskDoesNotExistErrorMessage = "Task does not exist in the database";
    
	private Task task;
	private TaskService taskService;
	
	private ProjectMapper projectMapperMock;
	private SprintMapper sprintMapperMock;
	private TaskMapper taskMapperMock;
	private UserStoryMapper userStoryMapperMock;
	
	private List<Task> tasks;
	private Project project;
	private Sprint sprint;
	private UserStory userStory;

	@Before
	public void setup(){	
    	projectMapperMock = mock(ProjectMapper.class);
    	sprintMapperMock = mock(SprintMapper.class);
		taskMapperMock = mock(TaskMapper.class);
    	userStoryMapperMock = mock(UserStoryMapper.class);
    	taskService = new TaskService(taskMapperMock, projectMapperMock, userStoryMapperMock, sprintMapperMock);
		
		project = generateProject();
		sprint = generateSprint();
		task = generateTask();
		userStory = generateUserStory();
		
		task.setUserStory(userStory);
        tasks = new ArrayList<Task>();
    	tasks.add(task);

		userStory.setSprint(sprint);
		userStory.setProject(project);
    	userStory.setTasks(tasks);
	}
	
	@Test
    public void testCreateTaskReturnsCreatedTask() {
		// Arrange
		when(projectMapperMock.getProjectById(project.getId())).thenReturn(project);
	    when(userStoryMapperMock.getUserStoryById(userStory.getId())).thenReturn(userStory);
	    when(taskMapperMock.getTaskById(task.getId())).thenReturn(task);
	    when(sprintMapperMock.getSprintById(sprint.getId())).thenReturn(sprint);
	    
	    // Act
	    Task response = taskService.create(project.getId(), userStory.getId(), task);
	
	    // Assert
	    assertTrue(response.equals(task));    
	}
	
	@Test
	public void testCreateTaskThrowsExceptionWhenProjectIsNull() {
		// Arrange
		when(projectMapperMock.getProjectById(project.getId())).thenReturn(null);
		
		// Act
		try {
		    taskService.create(project.getId(), userStory.getId(), task);
		    
		    fail();
		} catch (ResponseErrorException e) {
		    assertEquals(projectDoesNotExistErrorMessage, e.getMessage());
		}
	}
	
	@Test
	public void testCreateTaskThrowsExceptionWhenUserStoryIsNull() {
		// Arrange
		when(projectMapperMock.getProjectById(project.getId())).thenReturn(project);
	    when(userStoryMapperMock.getUserStoryById(userStory.getId())).thenReturn(null);
	    
	    // Act
	    try {
	        taskService.create(project.getId(), userStory.getId(), task);
	        
	        fail();
	    } catch (ResponseErrorException e) {
	        assertEquals(userStoryDoesNotExistErrorMessage, e.getMessage());
	    }
	}
	
	@Test
	public void testGetUserStoryTasksReturnsTasks() {
		// Arrange
		when(projectMapperMock.getProjectById(project.getId())).thenReturn(project);
	    when(userStoryMapperMock.getUserStoryById(userStory.getId())).thenReturn(userStory);
	    when(taskMapperMock.getUserStoryTasks(userStory.getId())).thenReturn(tasks);
	    
	    // Act
	    List<Task> response = taskService.getUserStoryTasks(userStory.getProject().getId(), userStory.getId());
	    
	    // Assert
	    assertTrue(response.equals(tasks));
	}
	
	@Test
	public void testGetUserStoryTasksThrowsExceptionWhenProjectIsNull() {
		// Arrange
		when(projectMapperMock.getProjectById(project.getId())).thenReturn(null);
	    
	    // Act
		try {
		    taskService.getUserStoryTasks(userStory.getProject().getId(), userStory.getId());
		} catch (ResponseErrorException e) {
		    assertEquals(projectDoesNotExistErrorMessage, e.getMessage());
		}
	}
	
	@Test
	public void testGetUserStoryTasksThrowsExceptionWhenUserStoryIsNull() {
		// Arrange
		when(projectMapperMock.getProjectById(project.getId())).thenReturn(project);
	    when(userStoryMapperMock.getUserStoryById(userStory.getId())).thenReturn(null);
	    
	    // Act
	    try {
	        taskService.getUserStoryTasks(userStory.getProject().getId(), userStory.getId());
	    } catch (ResponseErrorException e) {
	        assertEquals(userStoryDoesNotExistErrorMessage, e.getMessage());
	    }
	}
	
	@Test
	public void testGetTaskReturnsATask() {
		// Arrange
		when(projectMapperMock.getProjectById(project.getId())).thenReturn(project);
	    when(userStoryMapperMock.getUserStoryById(userStory.getId())).thenReturn(userStory);
	    when(taskMapperMock.getTaskById(task.getId())).thenReturn(task);
	    
	    // Act
	    Task response = taskService.getTask(project.getId(), userStory.getId(), task.getId());
	    
	    // Assert
	    assertTrue(response.equals(task));
	}
	
	@Test
	public void testGetTaskThrowsAnExceptionWhenProjectIsNull() {
		// Arrange
		when(projectMapperMock.getProjectById(project.getId())).thenReturn(null);
	    
		// Act
		try {
		    taskService.getTask(project.getId(), userStory.getId(), task.getId());
		} catch (ResponseErrorException e) {
		    assertEquals(projectDoesNotExistErrorMessage, e.getMessage());
		}
	}
	
	@Test
	public void testGetTaskThrowsAnExceptionWhenUserStoryIsNull() {
		// Arrange
		when(projectMapperMock.getProjectById(project.getId())).thenReturn(project);
	    when(userStoryMapperMock.getUserStoryById(userStory.getId())).thenReturn(null);
    
	    // Act
	    try {
	        taskService.getTask(project.getId(), userStory.getId(), task.getId());
	    } catch (ResponseErrorException e) {
	        assertEquals(userStoryDoesNotExistErrorMessage, e.getMessage());
	    }
	}
	
	@Test
	public void testGetTaskThrowsAnExceptionWhenTaskDoesNotExist() {
		// Arrange
		when(projectMapperMock.getProjectById(project.getId())).thenReturn(project);
	    when(userStoryMapperMock.getUserStoryById(userStory.getId())).thenReturn(userStory);
	    when(taskMapperMock.getTaskById(task.getId())).thenReturn(null);

	    // Act
	    try {
	        taskService.getTask(project.getId(), userStory.getId(), task.getId());
	    } catch (ResponseErrorException e) {
	        assertEquals(taskDoesNotExistErrorMessage, e.getMessage());
	    }
	}
	
	@Test
	public void testUpdateTaskUpdatesATaskAndReturnsIt() {
		// Arrange
		when(taskMapperMock.getTaskById(task.getId())).thenReturn(task);
		
		// Act
		Task response = taskService.updateTask(task, task.getId());
		
		// Assert
		assertTrue(response.equals(task));
	}
	
	@Test
	public void testUpdateTaskThrowsExceptionWhenTaskDoesNotExist() {
		// Arrange
		when(taskMapperMock.getTaskById(task.getId())).thenReturn(null);

		// Act
		try {
		    taskService.updateTask(task, task.getId());
		} catch (ResponseErrorException e) {
		    assertEquals(taskDoesNotExistErrorMessage, e.getMessage());
		}
	}
}