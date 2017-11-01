package uk.ac.qub.csc3045.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.qub.csc3045.api.model.Project;
import uk.ac.qub.csc3045.api.service.ProjectService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/project")
public class ProjectController {
	
	private final ProjectService projectService;

    @Autowired
    public ProjectController( ProjectService projectService) {
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

}
