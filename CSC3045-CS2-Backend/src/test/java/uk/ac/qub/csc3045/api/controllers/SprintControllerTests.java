package uk.ac.qub.csc3045.api.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import uk.ac.qub.csc3045.api.controller.SprintController;
import uk.ac.qub.csc3045.api.model.Sprint;
import uk.ac.qub.csc3045.api.service.SprintService;

public class SprintControllerTests {
    @Test
    public void getSprintShouldReturn200OnSuccess() {
        //Arrange
        SprintService sprintService = mock(SprintService.class);
        Sprint sprint = new Sprint();
        sprint.setId(100l);
        SprintController sprintController = new SprintController(sprintService);
        when(sprintService.getSprint(sprint.getId())).thenReturn(sprint);
        
        //Act
        ResponseEntity response = sprintController.getSprint(sprint.getId());

        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sprint, response.getBody());
    }
}