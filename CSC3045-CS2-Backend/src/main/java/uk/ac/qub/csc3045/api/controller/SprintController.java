package uk.ac.qub.csc3045.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.ac.qub.csc3045.api.model.Sprint;
import uk.ac.qub.csc3045.api.model.User;
import uk.ac.qub.csc3045.api.service.SprintService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/sprint")
public class SprintController {

	private final SprintService sprintService;

    @Autowired
    public SprintController(SprintService sprintService) {
        this.sprintService = sprintService;
    }
		
    @PostMapping()
    public ResponseEntity<Sprint> createSprint(@Valid @RequestBody Sprint sprint) {
        return new ResponseEntity<>(this.sprintService.createSprint(sprint), HttpStatus.CREATED);
    }

    @GetMapping(value = "/project/{projectId}")
    public ResponseEntity<List<Sprint>> getProjectSprints(@PathVariable("projectId") long projectId) {
        return new ResponseEntity<>(this.sprintService.getProjectSprints(projectId), HttpStatus.OK);
    }
    
    @GetMapping(value = "/{sprintId}")
    public ResponseEntity<Sprint> getSprint(@PathVariable("sprintId") long sprintId) {
        return new ResponseEntity<>(sprintService.getSprint(sprintId), HttpStatus.OK);
    }

    @GetMapping(value = "/team/{sprintId}")
    public ResponseEntity<List<User>> getSprintTeam(@PathVariable("sprintId") long sprintId) {
        return new ResponseEntity<>(this.sprintService.getSprintTeam(sprintId), HttpStatus.OK);
    }
	
	@PutMapping(value = "/team")
    public ResponseEntity<List<User>> updateSprintTeam(@Valid @RequestBody Sprint sprint) {
        return new ResponseEntity<>(this.sprintService.updateSprintTeam(sprint), HttpStatus.OK);
    }

    @GetMapping(value = "/available/team/{sprintId}")
    public ResponseEntity<List<User>> getAvailableDevelopers(@PathVariable("sprintId") long sprintId) {
        return new ResponseEntity<>(this.sprintService.getAvailableDevelopers(sprintId), HttpStatus.OK);
    }
}
