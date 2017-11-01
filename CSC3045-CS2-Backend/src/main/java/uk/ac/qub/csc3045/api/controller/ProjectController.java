package uk.ac.qub.csc3045.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import uk.ac.qub.csc3045.api.model.Project;
import uk.ac.qub.csc3045.api.model.User;
import uk.ac.qub.csc3045.api.service.ProjectService;

@RestController
@RequestMapping(value = "/project")
public class ProjectController {

    private final ProjectService projectService;
    
    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }
    
    @PostMapping()
    public ResponseEntity<Project> create(@Valid @RequestBody Project project) {
        return new ResponseEntity<>(this.projectService.create(project), HttpStatus.CREATED);
    }
    
    @PutMapping()
    public ResponseEntity<Project> update(@Valid @RequestBody Project project){
    	return new ResponseEntity<>(this.projectService.update(project), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/team", method = POST)
    public ResponseEntity<Project> addToTeam(@Valid @RequestBody Project project) {
        return new ResponseEntity<>(this.projectService.addToTeam(project), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/team/{projectId}", method = GET)
    public ResponseEntity<List<User>> getTeamMembers(@Valid @PathVariable("projectId") long projectId) {
        return new ResponseEntity<>(this.projectService.getTeamMembers(projectId), HttpStatus.OK);
    }
}