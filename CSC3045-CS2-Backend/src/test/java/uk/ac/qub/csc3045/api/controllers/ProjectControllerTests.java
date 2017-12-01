package uk.ac.qub.csc3045.api.controllers;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import uk.ac.qub.csc3045.api.controller.ProjectController;
import uk.ac.qub.csc3045.api.controller.UserController;
import uk.ac.qub.csc3045.api.exception.ResponseErrorException;
import uk.ac.qub.csc3045.api.model.Project;
import uk.ac.qub.csc3045.api.model.User;
import uk.ac.qub.csc3045.api.service.ProjectService;
import uk.ac.qub.csc3045.api.service.UserService;
import static uk.ac.qub.csc3045.api.setup.UnitTestObjectGenerator.*;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProjectControllerTests {

    private ProjectController projectController;
    private ProjectService projectService;
    
    private Project project;

    @Before
    public void setUp() {
    	this.projectService = mock(ProjectService.class);
        this.projectController = new ProjectController(projectService);
        this.project = generateProject(); 
    }

    @Test
    public void createProjectShouldReturn201() {
        //Arrange
        when(projectService.create(project)).thenReturn(project);
        
        //Act
        ResponseEntity response = projectController.create(project);

        //Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(project, response.getBody());
    }
    
    @Test
    public void updateProjectShouldReturn200() {
    	//Arrange
        when(projectService.update(project)).thenReturn(project);
        
        //Act
        ResponseEntity response = projectController.update(project, project.getId());

        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(project, response.getBody());
    }
    
    @Test
    public void updateProjectIdsDifferentIdsShouldReturn400() {
    	try {
            projectController.update(project, -1);
    	}
    	catch(ResponseErrorException ex) {
    		assertEquals(ex.getMessage(), "Id in URL and body do not match");
    	}
    }
    
    @Test
    public void getProjectShouldReturn200() {
        //Arrange
        when(projectService.get(1)).thenReturn(project);
        
        //Act
        ResponseEntity response = projectController.get(1);

        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(project, response.getBody());
    }
    
    @Test
    public void addToTeamShouldReturn200() {
        //Arrange
        when(projectService.addToTeam(project)).thenReturn(project);
        
        //Act
        ResponseEntity response = projectController.addToTeam(project, project.getId());

        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(project, response.getBody());
    }
    
    @Test
    public void addToTeamProjectIdsDifferentShouldReturn400() {
    	try {
            projectController.addToTeam(project, -1);
    	}
    	catch(ResponseErrorException ex) {
    		assertEquals(ex.getMessage(), "Id in URL and body do not match");
    	}
    }
    
    @Test
    public void getTeamMembersShouldReturn200() {
    	List<User> userList = generateUserList(5);
    	
    	//Arrange
        when(projectService.getTeamMembers(project.getId())).thenReturn(userList);
        
        //Act
        ResponseEntity response = projectController.getTeamMembers(project.getId());

        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userList, response.getBody());
    }

}
