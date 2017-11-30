package uk.ac.qub.csc3045.api.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static uk.ac.qub.csc3045.api.setup.UnitTestObjectGenerator.*;
import static uk.ac.qub.csc3045.api.setup.UnitTestObjectGenerator.generateUserList;

import org.junit.Before;
import org.junit.Test;

import org.springframework.dao.DataIntegrityViolationException;
import uk.ac.qub.csc3045.api.exception.ResponseErrorException;
import uk.ac.qub.csc3045.api.mapper.ProjectMapper;
import uk.ac.qub.csc3045.api.mapper.SprintMapper;
import uk.ac.qub.csc3045.api.model.Project;
import uk.ac.qub.csc3045.api.model.Sprint;
import uk.ac.qub.csc3045.api.model.User;
import uk.ac.qub.csc3045.api.model.UserStory;
import uk.ac.qub.csc3045.api.utility.ValidationUtility;


import java.util.ArrayList;
import java.util.List;

public class SprintServiceTest {

    private SprintService sprintService;
    private SprintMapper sprintMapperMock;
    private ProjectMapper projectMapperMock;

    private Sprint sprint;
    private List<Sprint> sprints;

    @Before
    public void setup() {
        sprintMapperMock = mock(SprintMapper.class);
        projectMapperMock = mock(ProjectMapper.class);
        sprintService = new SprintService(sprintMapperMock, projectMapperMock);

        sprint = generateSprint();
        sprint.setProject(generateProject());
        sprint.setScrumMaster(generateUser());
        sprint.setUsers(generateUserList(5));
        sprints = generateSprintList(2);
    }

    @Test
    public void createValidSprintShouldReturnCreatedSprint() {
        //Arrange
        when(sprintMapperMock.getSprintById(sprint.getId())).thenReturn(sprint);
        when(projectMapperMock.getProjectById(sprint.getProject().getId())).thenReturn(sprint.getProject());

        //Act
        Sprint response = sprintService.createSprint(sprint.getProject().getId(), sprint);

        //Assert
        assertTrue(response.equals(sprint));
    }

    @Test()
    public void createInvalidSprintShouldThrowException() {
        //Arrange
        doThrow(new DataIntegrityViolationException("")).when(sprintMapperMock).createSprint(sprint);
        try {
        	 sprintService.createSprint(sprint.getProject().getId(), sprint);
             
        }catch(ResponseErrorException e) {
        	assertTrue(e.getMessage() == "Project Id does not exist in the database");
        }
    }
	
    @Test
    public void getExistingSprintShouldReturnSprint() {
        //Arrange
        when(sprintMapperMock.getSprintById(sprint.getId())).thenReturn(sprint);
        when(projectMapperMock.getProjectById(sprint.getProject().getId())).thenReturn(sprint.getProject());
        
        //Act
        Sprint response = sprintService.getSprint(sprint.getProject().getId(), sprint.getId());
        
        //Assert
        assertTrue(response.equals(sprint));
    }

    @Test()
    public void getNonExistingSprintShouldThrowException() {
    	//Arrange
       when(sprintMapperMock.getSprintById(sprint.getId())).thenReturn(null);
       try {
        	 sprintService.getSprint(sprint.getProject().getId(), sprint.getId());
            
       }catch(ResponseErrorException e) {
       	assertTrue(e.getMessage() == "Project Id does not exist in the database");
       }
    }

    @Test
    public void getProjectSprintsOnProjectWithSprintsShouldReturnSprints() {
        //Arrange
        when(sprintMapperMock.getProjectSprints(sprint.getProject().getId())).thenReturn(sprints);
        when(projectMapperMock.getProjectById(sprint.getProject().getId())).thenReturn(sprint.getProject());
        
        //Act
        List<Sprint> response = sprintService.getProjectSprints(sprint.getProject().getId());

        //Assert
        assertTrue(response.equals(sprints));
    }

    @Test()
    public void getProjectSprintsOnProjectWithNoSprintsShouldThrowException() {
        //Arrange
        when(sprintMapperMock.getProjectSprints(sprint.getProject().getId())).thenReturn(new ArrayList<>());
        try {
       	 sprintService.getProjectSprints(sprint.getProject().getId());
       }catch(ResponseErrorException e) {
       	assertTrue(e.getMessage() == "Project Id does not exists in the database");
       }
    }

    @Test
    public void getSprintTeamOnExistingSprintShouldReturnTeam() {
        //Arrange
        when(sprintMapperMock.getSprintById(sprint.getId())).thenReturn(sprint);
        when(sprintMapperMock.getSprintTeam(sprint.getId())).thenReturn(sprint.getUsers());
        when(projectMapperMock.getProjectById(sprint.getProject().getId())).thenReturn(sprint.getProject());
        
        //Act
        List<User> response = sprintService.getSprintTeam(sprint.getProject().getId(), sprint.getId());

        //Assert
        assertTrue(response.equals(sprint.getUsers()));
    }

    @Test()
    public void getSprintTeamOnNonExistingSprintShouldThrowException() {
        //Arrange
    	when(projectMapperMock.getProjectById(sprint.getProject().getId())).thenReturn(sprint.getProject());
        when(sprintMapperMock.getSprintById(sprint.getId())).thenReturn(null);

        try {
            sprintService.getSprintTeam(sprint.getProject().getId(), sprint.getId());
          }catch(ResponseErrorException e) {
          	assertTrue(e.getMessage() == "Sprint does not exist");
          }
       
    }

    @Test
    public void getAvailableDevelopersOnProjectWithAvailableDevelopersShouldReturnDevelopers() {
        //Arrange
        List<User> developers = generateUserList(2);

        when(sprintMapperMock.getSprintById(sprint.getId())).thenReturn(sprint);

        when(sprintMapperMock.getClashingSprintsForUser(developers.get(0).getId(), sprint.getStartDate(), sprint.getEndDate()))
                .thenReturn(new ArrayList<>());
        when(sprintMapperMock.getClashingSprintsForUser(developers.get(1).getId(), sprint.getStartDate(), sprint.getEndDate()))
                .thenReturn(new ArrayList<>());

        when(projectMapperMock.getProjectDevelopers(sprint.getProject().getId()))
                .thenReturn(developers);
        when(projectMapperMock.getProjectById(sprint.getProject().getId())).thenReturn(sprint.getProject());
        
        //Act
        List<User> response = sprintService.getAvailableDevelopers(sprint.getProject().getId(), sprint.getId());

        //Assert
        assertTrue(response.equals(developers));
    }

    @Test(expected = ResponseErrorException.class)
    public void getAvailableDevelopersOnProjectWithNoAvailableDevelopersShouldThrowException() {
        //Arrange
        List<User> developers = generateUserList(2);
        List<Sprint> clashingSprints = generateSprintList(2);

        when(sprintMapperMock.getSprintById(sprint.getId())).thenReturn(sprint);

        when(sprintMapperMock.getClashingSprintsForUser(developers.get(0).getId(), sprint.getStartDate(), sprint.getEndDate()))
                .thenReturn(clashingSprints);
        when(sprintMapperMock.getClashingSprintsForUser(developers.get(1).getId(), sprint.getStartDate(), sprint.getEndDate()))
                .thenReturn(clashingSprints);

        when(projectMapperMock.getProjectDevelopers(sprint.getProject().getId()))
                .thenReturn(developers);

        //Act
        sprintService.getAvailableDevelopers(sprint.getProject().getId(), sprint.getId());
    }

    @Test(expected = ResponseErrorException.class)
    public void getAvailableDevelopersOnNonExistingSprintShouldThrowException() {
        //Arrange
        when(sprintMapperMock.getSprintById(sprint.getId())).thenReturn(null);

        //Act
        sprintService.getAvailableDevelopers(sprint.getProject().getId(), sprint.getId());
    }

    @Test
    public void updateSprintTeamShouldReturnNewTeam() {
        //Arrange
        List<User> newTeam = new ArrayList<>(sprint.getUsers());
        newTeam.remove(0);

        when(sprintMapperMock.getSprintTeam(sprint.getId())).thenReturn(sprint.getUsers()).thenReturn(newTeam);
        when(projectMapperMock.getProjectById(sprint.getProject().getId())).thenReturn(sprint.getProject());
        doNothing().when(sprintMapperMock).resetSprintTeam(sprint.getId());
        doNothing().when(sprintMapperMock).resetSprintTeam(sprint.getId());

        //Act
        List<User> response = sprintService.updateSprintTeam(sprint.getProject().getId(), sprint);

        //Assert
        assertTrue(response.equals(newTeam));
    }
    
    @Test
    public void handleGetBacklogRequestSuccessful() {
    	when(projectMapperMock.getProjectById(sprint.getProject().getId())).thenReturn(sprint.getProject());
        when(sprintMapperMock.getSprintStories(sprint.getId())).thenReturn(sprint.getUserStories());
        when(sprintMapperMock.getSprintById(sprint.getId())).thenReturn(sprint);

        List<UserStory> response = sprintService.getSprintBacklog(sprint.getProject().getId(), sprint.getId());

        assertEquals(sprint.getUserStories(), response);
    }

    @Test()
    public void handleGetBacklogRequestFailure() {
    	when(projectMapperMock.getProjectById(sprint.getProject().getId())).thenReturn(sprint.getProject());
        when(sprintMapperMock.getSprintById(sprint.getId())).thenReturn(null);
        
        try {
        	 sprintService.getSprintBacklog(sprint.getProject().getId(), sprint.getId());
        }catch(ResponseErrorException e) {
        	assertTrue(e.getMessage() == "Sprint does not exist");
        }
    }

}