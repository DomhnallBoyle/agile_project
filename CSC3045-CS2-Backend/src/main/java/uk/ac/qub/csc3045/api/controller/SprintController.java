package uk.ac.qub.csc3045.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uk.ac.qub.csc3045.api.exception.ResponseErrorException;
import uk.ac.qub.csc3045.api.model.Sprint;
import uk.ac.qub.csc3045.api.model.User;
import uk.ac.qub.csc3045.api.model.UserStory;
import uk.ac.qub.csc3045.api.service.SprintService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/project/{projectId}/sprint")
public class SprintController {

	private final SprintService sprintService;

    @Autowired
    public SprintController(SprintService sprintService) {
        this.sprintService = sprintService;
    }
		
    @PostMapping()
    public ResponseEntity<Sprint> createSprint(@PathVariable("projectId") long projectId, @Valid @RequestBody Sprint sprint) {
        return new ResponseEntity<>(this.sprintService.createSprint(projectId, sprint), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<Sprint>> getProjectSprints(@PathVariable("projectId") long projectId) {
        return new ResponseEntity<>(this.sprintService.getProjectSprints(projectId), HttpStatus.OK);
    }
    
    @GetMapping(value = "/{sprintId}")
    public ResponseEntity<Sprint> getSprint(@PathVariable("projectId") long projectId, @PathVariable("sprintId") long sprintId) {
        return new ResponseEntity<>(sprintService.getSprint(projectId, sprintId), HttpStatus.OK);
    }

    @GetMapping(value = "/{sprintId}/user")
    public ResponseEntity<List<User>> getSprintTeam(@PathVariable("projectId") long projectId, @PathVariable("sprintId") long sprintId) {
        return new ResponseEntity<>(this.sprintService.getSprintTeam(projectId, sprintId), HttpStatus.OK);
    }
	
	@PutMapping(value = "/{sprintId}/user")
    public ResponseEntity<List<User>> updateSprintTeam(@PathVariable("projectId") long projectId,@PathVariable("sprintId") long sprintId, @Valid @RequestBody Sprint sprint) {
		if(sprint.getId() != null && (sprint.getId() != sprintId)) {
			throw new ResponseErrorException("Id in URL and body do not match", HttpStatus.BAD_REQUEST);
		}
		sprint.setId(sprintId);
        return new ResponseEntity<>(this.sprintService.updateSprintTeam(projectId, sprint), HttpStatus.OK);
    }

    @GetMapping(value = "{sprintId}/user/available")
    public ResponseEntity<List<User>> getAvailableDevelopers(@PathVariable("projectId") long projectId, @PathVariable("sprintId") long sprintId) {
        return new ResponseEntity<>(this.sprintService.getAvailableDevelopers(projectId, sprintId), HttpStatus.OK);
    }
    
    @PutMapping(value = "/{sprintId}/story")
    public ResponseEntity<List<UserStory>> updateSprintBacklog(@PathVariable("projectId") long projectId, @Valid @RequestBody Sprint sprint) {
        return new ResponseEntity<>(this.sprintService.updateSprintBacklog(projectId, sprint), HttpStatus.OK);
    }
    
    @GetMapping(value = "/{sprintId}/story")
    public ResponseEntity<List<UserStory>> getSprintBacklog(@PathVariable("projectId") long projectId, @PathVariable("sprintId") long sprintId) {
        return new ResponseEntity<>(this.sprintService.getSprintBacklog(projectId, sprintId), HttpStatus.OK);
    }
}
