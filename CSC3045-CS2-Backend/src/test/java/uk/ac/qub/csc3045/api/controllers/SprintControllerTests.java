package uk.ac.qub.csc3045.api.controllers;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.ac.qub.csc3045.api.controller.SprintController;
import uk.ac.qub.csc3045.api.model.Sprint;
import uk.ac.qub.csc3045.api.service.SprintService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static uk.ac.qub.csc3045.api.setup.UnitTestObjectGenerator.*;

public class SprintControllerTests {

    private SprintController sprintController;
    private SprintService sprintService;

    private Sprint sprint;

    @Before
    public void setup() {
        sprintService = mock(SprintService.class);
        sprintController = new SprintController(sprintService);

        sprint = generateSprint();
        sprint.setProject(generateProject());
        sprint.setScrumMaster(generateUser());
        sprint.setUsers(generateUserList(5));
    }

    @Test
    public void getSprintShouldReturn200OnSuccess() {
        //Arrange
        when(sprintService.getSprint(sprint.getProject().getId(), sprint.getId())).thenReturn(sprint);
        
        //Act
        ResponseEntity response = sprintController.getSprint(sprint.getProject().getId(), sprint.getId());

        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sprint, response.getBody());
    }

    @Test
    public void createSprintShouldReturn201OnSuccess() {
        //Arrange
        when(sprintService.createSprint(sprint.getProject().getId(), sprint)).thenReturn(sprint);

        //Act
        ResponseEntity response = sprintController.createSprint(sprint.getProject().getId(), sprint);

        //Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(sprint, response.getBody());
    }

    @Test
    public void getProjectSprintsShouldReturn200OnSuccess() {
        //Arrange
        List<Sprint> sprints = new ArrayList<>();
        sprints.add(generateSprint());
        sprints.add(generateSprint());

        when(sprintService.getProjectSprints(99L)).thenReturn(sprints);

        //Act
        ResponseEntity response = sprintController.getProjectSprints(99L);

        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sprints, response.getBody());
    }

    @Test
    public void getSprintTeamShouldReturn200OnSuccess() {
        //Arrange
        when(sprintService.getSprintTeam(sprint.getProject().getId(), sprint.getId())).thenReturn(sprint.getUsers());

        //Act
        ResponseEntity response = sprintController.getSprintTeam(sprint.getProject().getId(), sprint.getId());

        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sprint.getUsers(), response.getBody());
    }

    @Test
    public void updateSprintTeamShouldReturn200OnSuccess() {
        //Arrange
        when(sprintService.updateSprintTeam(sprint.getProject().getId(), sprint)).thenReturn(sprint.getUsers());

        //Act
        ResponseEntity response = sprintController.updateSprintTeam(sprint.getProject().getId(), sprint.getId(), sprint);

        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sprint.getUsers(), response.getBody());
    }

    @Test
    public void getAvailableDevelopersShouldReturn200OnSuccess() {
        //Arrange
        when(sprintService.getAvailableDevelopers(sprint.getProject().getId(), sprint.getId())).thenReturn(sprint.getUsers());

        //Act
        ResponseEntity response = sprintController.getAvailableDevelopers(sprint.getProject().getId(), sprint.getId());

        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sprint.getUsers(), response.getBody());
    }
}