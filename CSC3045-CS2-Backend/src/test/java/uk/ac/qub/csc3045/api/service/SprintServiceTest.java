package uk.ac.qub.csc3045.api.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import uk.ac.qub.csc3045.api.exception.ResponseErrorException;
import uk.ac.qub.csc3045.api.mapper.ProjectMapper;
import uk.ac.qub.csc3045.api.mapper.SprintMapper;
import uk.ac.qub.csc3045.api.model.Sprint;

public class SprintServiceTest {
	
    @Test
    public void handleGetSprintRequestSuccessful() {
        //Arrange
        SprintMapper sprintMapperMock = mock(SprintMapper.class);
        ProjectMapper projectMapperMock = mock(ProjectMapper.class);
        Sprint sprint = new Sprint();
        sprint.setId(100l);
        SprintService sprintService = new SprintService(sprintMapperMock, projectMapperMock);
        when(sprintMapperMock.getSprintById(sprint.getId())).thenReturn(sprint);
        
        //Act
        Sprint response = sprintService.getSprint(sprint.getId());
        
        //Assert
        assertTrue(response.equals(sprint));
    }

    @Test(expected = ResponseErrorException.class)
    public void handleGetSprintRequestFailure() {
    	//Arrange
    	SprintMapper sprintMapperMock = mock(SprintMapper.class);
    	ProjectMapper projectMapperMock = mock(ProjectMapper.class);
        Sprint sprint = new Sprint();
        sprint.setId(100l);
        SprintService sprintService = new SprintService(sprintMapperMock, projectMapperMock);
        when(sprintMapperMock.getSprintById(sprint.getId())).thenReturn(null);
        
        //Act
        sprintService.getSprint(sprint.getId());

    }
}