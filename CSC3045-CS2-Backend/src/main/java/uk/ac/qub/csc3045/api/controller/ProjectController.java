package uk.ac.qub.csc3045.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping()
    public ResponseEntity<Project> create(@Valid @RequestBody Project project) {
        return new ResponseEntity<>(this.projectService.create(project), HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<Project> update(@Valid @RequestBody Project project) {
        return new ResponseEntity<>(this.projectService.update(project), HttpStatus.OK);
    }

    @GetMapping(value = "/{projectId}")
    public ResponseEntity<Project> get(@Valid @PathVariable("projectId") long projectId) {
        return new ResponseEntity<>(this.projectService.get(projectId), HttpStatus.OK);
    }

    @GetMapping(value = "/user/{userId}")
    public ResponseEntity<List<Project>> getProjectsForUser(@Valid @PathVariable("userId") long userId) {
        return new ResponseEntity<>(this.projectService.getProjectsForUser(userId), HttpStatus.OK);
    }

    @PostMapping(value = "/team")
    public ResponseEntity<Project> addToTeam(@Valid @RequestBody Project project) {
        return new ResponseEntity<>(this.projectService.addToTeam(project), HttpStatus.OK);
    }

    @GetMapping(value = "/team/{projectId}")
    public ResponseEntity<List<User>> getTeamMembers(@Valid @PathVariable("projectId") long projectId) {
        return new ResponseEntity<>(this.projectService.getTeamMembers(projectId), HttpStatus.OK);
    }
}