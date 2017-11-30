package uk.ac.qub.csc3045.api.service;



import static org.junit.Assert.assertTrue;
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
import uk.ac.qub.csc3045.api.mapper.TaskMapper;
import uk.ac.qub.csc3045.api.mapper.UserStoryMapper;
import uk.ac.qub.csc3045.api.model.AcceptanceTest;
import uk.ac.qub.csc3045.api.model.Project;
import uk.ac.qub.csc3045.api.model.Sprint;
import uk.ac.qub.csc3045.api.model.Task;
import uk.ac.qub.csc3045.api.model.UserStory;
import static uk.ac.qub.csc3045.api.setup.UnitTestObjectGenerator.*;

public class TaskServiceTest {
	private TaskMapper taskMapper;
	private TaskService taskService;
	private Task task;
	private UserStoryMapper userStoryMapper;
	private ProjectMapper projectMapper;
	private UserStory userStory;
	private Project project;
	private Sprint sprint;
	private AcceptanceTest acceptanceTest;
	private List<UserStory>userStories;
	private List<Task> tasks;

	
	
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
        task=generateTask();
        task.setUserStory(userStory);
		project.setUserStories(userStories);
		taskMapper = mock(TaskMapper.class);
    	projectMapper = mock(ProjectMapper.class);
    	userStoryMapper = mock(UserStoryMapper.class);
        tasks = new ArrayList<Task>();
    	tasks.add(task);
    	userStory.setTasks(tasks);
    	taskService = new TaskService(taskMapper,projectMapper,userStoryMapper);

	}
	@Test
    public void testCreateTaskReturnsCreatedTask() {
		when(projectMapper.getProjectById(project.getId())).thenReturn(project);
	    when(userStoryMapper.getUserStoryById(userStory.getId())).thenReturn(userStory);
	    when(taskMapper.getTaskById(task.getId())).thenReturn(task);
	    Task response = taskService.create(project.getId(),userStory.getId(),task);
	
	    assertTrue(response.equals(task));    
	
	}
	@Test(expected = ResponseErrorException.class)
	public void testCreateTaskThrowsExceptionWhenProjectIsNull() {
		when(projectMapper.getProjectById(project.getId())).thenReturn(null);
	    Task response = taskService.create(project.getId(),userStory.getId(),task);
	    assertTrue(response.equals(task));
	}
	@Test(expected = ResponseErrorException.class)
	public void testCreateTaskThrowsExceptionWhenUserStoryIsNull() {
		when(projectMapper.getProjectById(project.getId())).thenReturn(project);
	    when(userStoryMapper.getUserStoryById(userStory.getId())).thenReturn(null);
	    when(taskMapper.getTaskById(task.getId())).thenReturn(task);
	    Task response = taskService.create(project.getId(),userStory.getId(),task);
	
	    assertTrue(response.equals(task));
	}
	@Test
	public void testGetUserStoryTasksReturnsTasks() {
		when(projectMapper.getProjectById(project.getId())).thenReturn(project);
	    when(userStoryMapper.getUserStoryById(userStory.getId())).thenReturn(userStory);
	    when(taskMapper.getUserStoryTasks(userStory.getId())).thenReturn(tasks);
	    
	    List<Task> response = taskService.getUserStoryTasks(userStory.getProject().getId(), userStory.getId());
	    assertTrue(response.equals(tasks));
	    
	}
	@Test(expected = ResponseErrorException.class)
	public void testGetUserStoryTasksThrowsExceptionWhenProjectIsNull() {
		when(projectMapper.getProjectById(project.getId())).thenReturn(null);
	    when(userStoryMapper.getUserStoryById(userStory.getId())).thenReturn(userStory);
	    when(taskMapper.getUserStoryTasks(userStory.getId())).thenReturn(tasks);
	    
	    List<Task> response = taskService.getUserStoryTasks(userStory.getProject().getId(), userStory.getId());
	    assertTrue(response.equals(tasks));
	}
	@Test(expected = ResponseErrorException.class)
	public void testGetUserStoryTasksThrowsExceptionWhenUserStoryIsNull() {
		when(projectMapper.getProjectById(project.getId())).thenReturn(project);
	    when(userStoryMapper.getUserStoryById(userStory.getId())).thenReturn(null);
	    when(taskMapper.getUserStoryTasks(userStory.getId())).thenReturn(tasks);
	    
	    List<Task> response = taskService.getUserStoryTasks(userStory.getProject().getId(), userStory.getId());
	    assertTrue(response.equals(tasks));
	}
	@Test
	public void testGetTaskReturnsATask() {
		when(projectMapper.getProjectById(project.getId())).thenReturn(project);
	    when(userStoryMapper.getUserStoryById(userStory.getId())).thenReturn(userStory);
	    when(taskMapper.getTaskById(task.getId())).thenReturn(task);
	    
	    Task response = taskService.getTask(project.getId(),userStory.getId(), task.getId());
	    assertTrue(response.equals(task));
	}
	@Test(expected = ResponseErrorException.class)
	public void testGetTaskThrowsAnExceptionWhenProjectIsNull() {
		when(projectMapper.getProjectById(project.getId())).thenReturn(null);
	    when(userStoryMapper.getUserStoryById(userStory.getId())).thenReturn(userStory);
	    when(taskMapper.getTaskById(task.getId())).thenReturn(task);
	    
	    Task response = taskService.getTask(project.getId(),userStory.getId(), task.getId());
	    assertTrue(response.equals(task));
	}
	@Test(expected = ResponseErrorException.class)
	public void testGetTaskThrowsAnExceptionWhenUserStoryIsNull() {
		when(projectMapper.getProjectById(project.getId())).thenReturn(project);
	    when(userStoryMapper.getUserStoryById(userStory.getId())).thenReturn(null);
	    when(taskMapper.getTaskById(task.getId())).thenReturn(task);
	    
	    Task response = taskService.getTask(project.getId(),userStory.getId(), task.getId());
	    assertTrue(response.equals(task));
	}
	@Test(expected = ResponseErrorException.class)
	public void testGetTaskThrowsAnExceptionWhenTaskIsNull() {
		when(projectMapper.getProjectById(project.getId())).thenReturn(project);
	    when(userStoryMapper.getUserStoryById(userStory.getId())).thenReturn(userStory);
	    when(taskMapper.getTaskById(task.getId())).thenReturn(null);
	    
	    Task response = taskService.getTask(project.getId(),userStory.getId(), task.getId());
	    assertTrue(response.equals(task));
	}
	@Test
	public void testUpdateTaskUpdatesATaskAndReturnsIt() {
		when(taskMapper.getTaskById(task.getId())).thenReturn(task);
		Task response = taskService.updateTask(task, task.getId());
		assertTrue(response.equals(task));
	}
	@Test (expected = ResponseErrorException.class)
	public void testUpdateTaskThrowsExceptionWhenTaskIdIsNull() {
		when(taskMapper.getTaskById(task.getId())).thenReturn(null);
		Task response = taskService.updateTask(task, task.getId());
		assertTrue(response.equals(task));
	}
	

}
