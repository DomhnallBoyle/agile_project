package uk.ac.qub.csc3045.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    
    @RequestMapping(value = "/team", method = POST)
    public ResponseEntity<Project> addToTeam(@Valid @RequestBody Project project) {
        return new ResponseEntity<>(this.projectService.addToTeam(project), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/teamGet", method = POST)
    public ResponseEntity<List<User>> getTeamMembers(@Valid @RequestBody Project project) {
        return new ResponseEntity<>(this.projectService.getTeamMembers(project), HttpStatus.OK);
    }
    
    @PostMapping()
    public ResponseEntity<Project> create(@Valid @RequestBody Project project) {
        return new ResponseEntity<>(this.projectService.create(project), HttpStatus.CREATED);
    }

}