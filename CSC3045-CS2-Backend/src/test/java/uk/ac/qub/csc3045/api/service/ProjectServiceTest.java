package uk.ac.qub.csc3045.api.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;

import uk.ac.qub.csc3045.api.exception.ResponseErrorException;
import uk.ac.qub.csc3045.api.mapper.ProjectMapper;
import uk.ac.qub.csc3045.api.model.Project;
import uk.ac.qub.csc3045.api.model.User;

public class ProjectServiceTest {

	private ProjectService projectService;
	private ProjectMapper projectMapperMock;
	
	private User user;
	private Project project;
	
	@Before
    public void setUp() throws Exception {
        projectMapperMock = mock(ProjectMapper.class);
        
        projectService = new ProjectService(projectMapperMock);

        user = new User();
        user.setId(1L);
        user.setForename("Harry");
        user.setSurname("Potter");
        user.setEmail("harryp@gmail.com");
        
        project = new Project();
        project.setId(1L);
        project.setName("New Project");
        project.setDescription("Test Project");
        project.setManager(user); 
        
        List<User> users = new ArrayList<>();
        users.add(user);
        project.setUsers(users);
    }
	
	@Test
	public void handleAddToTeamRequestSuccessful() throws Exception{
		Project response = projectService.addToTeam(project);
		
		assertEquals(project.getUsers(), response.getUsers());
	}
	
	@Test(expected = ResponseErrorException.class)
	public void handleAddToTeamRequestFailure() throws Exception{
		doThrow(new DataIntegrityViolationException("")).when(projectMapperMock).addToProjectTeam(project.getId(), user.getId());	
		projectService.addToTeam(project);
	}
	
	@Test
	public void getTeamRequestSuccessful() throws Exception{
		when(projectMapperMock.getUsersOnProject(project.getId())).thenReturn(project.getUsers());
		when(projectMapperMock.findProjectByProjectId(project.getId())).thenReturn(project);
		
		List<User> response = projectService.getTeamMembers(project.getId());
		
		assertEquals(project.getUsers(), response);
	}
	
	@Test(expected = ResponseErrorException.class)
	public void getTeamRequestFailure() throws Exception{
		when(projectMapperMock.findProjectByProjectId(project.getId())).thenReturn(null);
		projectService.getTeamMembers(project.getId());	
	}
}
