package uk.ac.qub.csc3045.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uk.ac.qub.csc3045.api.model.Project;
import uk.ac.qub.csc3045.api.model.Sprint;
import uk.ac.qub.csc3045.api.model.User;
import uk.ac.qub.csc3045.api.service.ProjectService;
import uk.ac.qub.csc3045.api.service.SprintService;

@RestController
@RequestMapping(value = "/sprint")
public class SprintController {

	private final SprintService sprintService;

    @Autowired
    public SprintController(SprintService sprintService) {
        this.sprintService = sprintService;
    }
    
    @GetMapping(value = "/project/{projectId}")
    public ResponseEntity<List<Sprint>> get(@Valid @PathVariable("sprintId") long sprintId) {
	      return new ResponseEntity<>(this.sprintService.getSprintsInProject(sprintId), HttpStatus.OK);
    }
		
    @PostMapping()
    public ResponseEntity<Sprint> create(@Valid @RequestBody Sprint sprint) {
        return new ResponseEntity<>(this.sprintService.create(sprint), HttpStatus.CREATED);
    }
    
	
	@PostMapping(value = "/sprint")
    public ResponseEntity<Sprint> addToTeam(@Valid @RequestBody Sprint sprint) {
        return new ResponseEntity<>(this.sprintService.addToTeam(sprint), HttpStatus.OK);
    }
	
	@GetMapping(value = "/team/{sprintId}")
    public ResponseEntity<List<User>> getTeamMembers(@Valid @PathVariable("sprintd") long sprintId) {
        return new ResponseEntity<>(this.sprintService.getSprintTeamMembers(sprintId), HttpStatus.OK);
    }
}
