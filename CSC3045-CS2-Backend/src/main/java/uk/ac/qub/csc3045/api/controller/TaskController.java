package uk.ac.qub.csc3045.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uk.ac.qub.csc3045.api.model.Task;
import uk.ac.qub.csc3045.api.service.TaskService;

@RestController
@RequestMapping(value = "/project/{projectId}/story/{userStoryId}/task")
public class TaskController {
		
	private TaskService taskService;
	
	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}
	
	
	@PostMapping()
	public ResponseEntity<Task> create(@Valid @PathVariable("projectId") long projectId,
			@Valid @PathVariable("userStoryId") long userStoryId,
			@Valid @RequestBody Task task) {
		return new ResponseEntity<>(taskService.create(userStoryId,task), HttpStatus.CREATED);
	}
	@GetMapping()
	public ResponseEntity<List<Task>> getTasks(@Valid @PathVariable("projectId") long projectId,
			@Valid @PathVariable("userStoryId") long userStoryId) {
		return new ResponseEntity<List<Task>>(taskService.getTasks(userStoryId), HttpStatus.OK);
	}
	@GetMapping(value = "/{id}")
	public ResponseEntity<Task>getTask(@Valid @PathVariable("id") long taskId) {
		return new ResponseEntity<Task>(taskService.getTask(taskId), HttpStatus.OK);
	}
	
		
		
}
