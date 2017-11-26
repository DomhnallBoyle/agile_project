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
import uk.ac.qub.csc3045.api.model.Sprint;
import uk.ac.qub.csc3045.api.model.User;
import uk.ac.qub.csc3045.api.model.UserStory;

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

        //Act
        Sprint response = sprintService.createSprint(sprint);

        //Assert
        assertTrue(response.equals(sprint));
    }

    @Test(expected = ResponseErrorException.class)
    public void createInvalidSprintShouldThrowException() {
        //Arrange
        doThrow(new DataIntegrityViolationException("")).when(sprintMapperMock).createSprint(sprint);

        //Act
        Sprint response = sprintService.createSprint(sprint);

        //Assert
        assertTrue(response.equals(sprint));
    }
	
    @Test
    public void getExistingSprintShouldReturnSprint() {
        //Arrange
        when(sprintMapperMock.getSprintById(sprint.getId())).thenReturn(sprint);
        
        //Act
        Sprint response = sprintService.getSprint(sprint.getId());
        
        //Assert
        assertTrue(response.equals(sprint));
    }

    @Test(expected = ResponseErrorException.class)
    public void getNonExistingSprintShouldThrowException() {
    	//Arrange
        when(sprintMapperMock.getSprintById(sprint.getId())).thenReturn(null);
        
        //Act
        sprintService.getSprint(sprint.getId());
    }

    @Test
    public void getProjectSprintsOnProjectWithSprintsShouldReturnSprints() {
        //Arrange
        when(sprintMapperMock.getProjectSprints(sprint.getProject().getId())).thenReturn(sprints);

        //Act
        List<Sprint> response = sprintService.getProjectSprints(sprint.getProject().getId());

        //Assert
        assertTrue(response.equals(sprints));
    }

    @Test(expected = ResponseErrorException.class)
    public void getProjectSprintsOnProjectWithNoSprintsShouldThrowException() {
        //Arrange
        when(sprintMapperMock.getProjectSprints(sprint.getProject().getId())).thenReturn(new ArrayList<>());

        //Act
        sprintService.getProjectSprints(sprint.getProject().getId());
    }

    @Test
    public void getSprintTeamOnExistingSprintShouldReturnTeam() {
        //Arrange
        when(sprintMapperMock.getSprintById(sprint.getId())).thenReturn(sprint);
        when(sprintMapperMock.getSprintTeam(sprint.getId())).thenReturn(sprint.getUsers());

        //Act
        List<User> response = sprintService.getSprintTeam(sprint.getId());

        //Assert
        assertTrue(response.equals(sprint.getUsers()));
    }

    @Test(expected = ResponseErrorException.class)
    public void getSprintTeamOnNonExistingSprintShouldThrowException() {
        //Arrange
        when(sprintMapperMock.getSprintById(sprint.getId())).thenReturn(null);

        //Act
        sprintService.getSprintTeam(sprint.getProject().getId());
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

        //Act
        List<User> response = sprintService.getAvailableDevelopers(sprint.getId());

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
        sprintService.getAvailableDevelopers(sprint.getId());
    }

    @Test(expected = ResponseErrorException.class)
    public void getAvailableDevelopersOnNonExistingSprintShouldThrowException() {
        //Arrange
        when(sprintMapperMock.getSprintById(sprint.getId())).thenReturn(null);

        //Act
        sprintService.getAvailableDevelopers(sprint.getId());
    }

    @Test
    public void updateSprintTeamShouldReturnNewTeam() {
        //Arrange
        List<User> newTeam = new ArrayList<>(sprint.getUsers());
        newTeam.remove(0);

        when(sprintMapperMock.getSprintTeam(sprint.getId())).thenReturn(sprint.getUsers()).thenReturn(newTeam);
        doNothing().when(sprintMapperMock).resetSprintTeam(sprint.getId());
        doNothing().when(sprintMapperMock).resetSprintTeam(sprint.getId());

        //Act
        List<User> response = sprintService.updateSprintTeam(sprint);

        //Assert
        assertTrue(response.equals(newTeam));
    }
    
    @Test
    public void handleGetBacklogRequestSuccessful() {
        when(sprintMapperMock.getUserStoriesInSprint(sprint.getId())).thenReturn(sprint.getUserStories());
        when(sprintMapperMock.getSprintById(sprint.getId())).thenReturn(sprint);

        List<UserStory> response = sprint.getUserStories();

        assertEquals(sprint.getUserStories(), response);
    }

    @Test(expected = ResponseErrorException.class)
    public void handleGetBacklogRequestFailure() {
        when(sprintMapperMock.getSprintById(sprint.getId())).thenReturn(null);
        sprint.getUserStories();
    }

}