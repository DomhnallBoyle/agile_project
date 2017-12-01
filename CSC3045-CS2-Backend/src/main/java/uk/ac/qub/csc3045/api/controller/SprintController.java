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
		
    /**
     * Create sprint controller
     * @param projectId id of the project
     * @param sprint object from the request body
     * @return sprint object from the database
     */
    @PostMapping()
    public ResponseEntity<Sprint> createSprint(@PathVariable("projectId") long projectId, @Valid @RequestBody Sprint sprint) {
        return new ResponseEntity<>(this.sprintService.createSprint(projectId, sprint), HttpStatus.CREATED);
    }

    /**
     * Controller for getting sprints for a particular project
     * @param projectId id of the project
     * @return List of sprint objects that are related to the project
     */
    @GetMapping()
    public ResponseEntity<List<Sprint>> getProjectSprints(@PathVariable("projectId") long projectId) {
        return new ResponseEntity<>(this.sprintService.getProjectSprints(projectId), HttpStatus.OK);
    }
    
    /**
     * Controller for getting a particular sprint
     * @param projectId id of the project the sprint belongs to
     * @param sprintId id of the sprint
     * @return sprint object from the database
     */
    @GetMapping(value = "/{sprintId}")
    public ResponseEntity<Sprint> getSprint(@PathVariable("projectId") long projectId, @PathVariable("sprintId") long sprintId) {
        return new ResponseEntity<>(sprintService.getSprint(projectId, sprintId), HttpStatus.OK);
    }

    /**
     * Controller for getting users for a particular team
     * @param projectId id of the project the sprint belongs to
     * @param sprintId id of the sprint for which the users belongs to
     * @return list of user objects that are members of that particular sprint
     */
    @GetMapping(value = "/{sprintId}/user")
    public ResponseEntity<List<User>> getSprintTeam(@PathVariable("projectId") long projectId, @PathVariable("sprintId") long sprintId) {
        return new ResponseEntity<>(this.sprintService.getSprintTeam(projectId, sprintId), HttpStatus.OK);
    }
	
    /**
     * Controller for updating a particular sprint team
     * i.e. adding/removing users from the sprint team
     * @param projectId id of the project the sprint belongs to
     * @param sprintId id of the sprint object
     * @param sprint object from the request body
     * @return list of updated sprint team members from database
     */
	@PutMapping(value = "/{sprintId}/user")
    public ResponseEntity<List<User>> updateSprintTeam(@PathVariable("projectId") long projectId,@PathVariable("sprintId") long sprintId, @Valid @RequestBody Sprint sprint) {
		if(sprint.getId() != null && (sprint.getId() != sprintId)) {
			throw new ResponseErrorException("Id in URL and body do not match", HttpStatus.BAD_REQUEST);
		}
		sprint.setId(sprintId);
        return new ResponseEntity<>(this.sprintService.updateSprintTeam(projectId, sprint), HttpStatus.OK);
    }

	/**
	 * Controller to get the available developers i.e. developers who have not been assigned to a sprint
	 * @param projectId id of the project
	 * @param sprintId id of the sprint
	 * @return list of available users
	 */
    @GetMapping(value = "{sprintId}/user/available")
    public ResponseEntity<List<User>> getAvailableDevelopers(@PathVariable("projectId") long projectId, @PathVariable("sprintId") long sprintId) {
        return new ResponseEntity<>(this.sprintService.getAvailableDevelopers(projectId, sprintId), HttpStatus.OK);
    }
    
    /**
     * Controller to update the sprint backlog i.e. add/remove tasks from the backlog
     * @param projectId - id of the project the sprint belongs to
     * @param sprint sprint object from the request body
     * @return list of updated user stories 
     */
    @PutMapping(value = "/{sprintId}/story")
    public ResponseEntity<List<UserStory>> updateSprintBacklog(@PathVariable("projectId") long projectId, @Valid @RequestBody Sprint sprint) {
        return new ResponseEntity<>(this.sprintService.updateSprintBacklog(projectId, sprint), HttpStatus.OK);
    }

    /**
     * Controller to get a particular sprint backlog
     * @param projectId id of the project the sprint belongs to
     * @param sprintId id of the sprint object
     * @return list of user stories associated with that sprint
     */
    @GetMapping(value = "/{sprintId}/story")
    public ResponseEntity<List<UserStory>> getSprintBacklog(@PathVariable("projectId") long projectId, @PathVariable("sprintId") long sprintId){
    	return new ResponseEntity<List<UserStory>>(this.sprintService.getSprintBacklog(projectId, sprintId), HttpStatus.OK);
    }
}
