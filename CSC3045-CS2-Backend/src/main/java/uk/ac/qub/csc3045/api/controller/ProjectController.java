package uk.ac.qub.csc3045.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uk.ac.qub.csc3045.api.exception.ResponseErrorException;
import uk.ac.qub.csc3045.api.model.Project;
import uk.ac.qub.csc3045.api.model.User;
import uk.ac.qub.csc3045.api.service.ProjectService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/project")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * Project create controller
     * @param project from the request body
     * @return Project object from the database
     */
    @PostMapping()
    public ResponseEntity<Project> create(@Valid @RequestBody Project project) {
        return new ResponseEntity<>(this.projectService.create(project), HttpStatus.CREATED);
    }

    /**
     * Update a particular project
     * @param project from the request body
     * @param projectId id of the project
     * @return Project from the database
     */
    @PutMapping(value = "/{projectId}")
    public ResponseEntity<Project> update(@Valid @RequestBody Project project,  @PathVariable("projectId") long projectId) {
    	//checks id in body and request are equal if both are set
    	if(project.getId() != null && (projectId != project.getId())) {
    		throw new ResponseErrorException("Id in URL and body do not match", HttpStatus.BAD_REQUEST);
    	}
    	project.setId(projectId);
        return new ResponseEntity<>(this.projectService.update(project), HttpStatus.OK);
    }

    /**
     * 
     * @param projectId
     * @return
     */
    @GetMapping(value = "/{projectId}")
    public ResponseEntity<Project> get(@Valid @PathVariable("projectId") long projectId) {
        return new ResponseEntity<>(this.projectService.get(projectId), HttpStatus.OK);
    }

    /**
     * 
     * @param project
     * @param projectId
     * @return
     */
    @PostMapping(value = "/{projectId}/user")
    public ResponseEntity<Project> addToTeam(@Valid @RequestBody Project project,  @PathVariable("projectId") long projectId) {
    	//checks id in body and request are equal if both are set
    	if(project.getId() != null && (projectId != project.getId())) {
    		throw new ResponseErrorException("Id in URL and body do not match", HttpStatus.BAD_REQUEST);
    	}
    	project.setId(projectId);
        return new ResponseEntity<>(this.projectService.addToTeam(project), HttpStatus.OK);
    }

    /**
     * 
     * @param projectId
     * @return
     */
    @GetMapping(value = "/{projectId}/user")
    public ResponseEntity<List<User>> getTeamMembers(@Valid @PathVariable("projectId") long projectId) {
        return new ResponseEntity<>(this.projectService.getTeamMembers(projectId), HttpStatus.OK);
    }
}