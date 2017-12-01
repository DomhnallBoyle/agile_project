package uk.ac.qub.csc3045.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uk.ac.qub.csc3045.api.model.Task;
import uk.ac.qub.csc3045.api.model.TaskEstimate;
import uk.ac.qub.csc3045.api.service.TaskService;

@RestController
@RequestMapping(value = "/project/{projectId}/story/{userStoryId}/task")
public class TaskController {
		
	/**
	 * Private variables
	 */
	private TaskService taskService;
	
	/**
	 * Constructor for the task controller
	 * @param taskService - service for the controller 
	 */
	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}

	/**
	 * Endpoint for creating a task
	 * @param projectId id of the project the story belongs to
	 * @param userStoryId id of the story the task belongs to
	 * @param task object from the request
	 * @return created task from the database
	 */
	@PostMapping()
	public ResponseEntity<Task> create(@Valid @PathVariable("projectId") long projectId,
			@Valid @PathVariable("userStoryId") long userStoryId,
			@Valid @RequestBody Task task) {
		return new ResponseEntity<>(taskService.create(projectId, userStoryId, task), HttpStatus.CREATED);
	}
	
	/**
	 * Gets the tasks for a particular user story
	 * @param projectId id of the project the user story belongs to
	 * @param userStoryId id of the user story the tasks belongs to
	 * @return list of tasks within that particular user story
	 */
	@GetMapping()
	public ResponseEntity<List<Task>> getTasks(@Valid @PathVariable("projectId") long projectId,
			@Valid @PathVariable("userStoryId") long userStoryId) {
		return new ResponseEntity<List<Task>>(taskService.getUserStoryTasks(projectId, userStoryId), HttpStatus.OK);
	}
	
	/**
	 * Endpoint to retrieve a particular task
	 * @param projectId id of the project the user story belongs to
	 * @param userStoryId id of the user story the task belongs to
	 * @param taskId id of the task
	 * @return task object from the database
	 */
	@GetMapping(value = "/{taskId}")
	public ResponseEntity<Task> getTask(@Valid @PathVariable("projectId") long projectId,
			@Valid @PathVariable("userStoryId") long userStoryId, @Valid @PathVariable("taskId") long taskId) {
		return new ResponseEntity<Task>(taskService.getTask(projectId, userStoryId, taskId), HttpStatus.OK);
	}
	
	/**
	 * Updates a particular task object
	 * @param task task task object from the request to be updated
	 * @param taskId id of the task object
	 * @return updated task from the database
	 */
	@PutMapping(value = "/{taskId}")
	public ResponseEntity<Task> updateTask(@Valid @RequestBody Task task, 
			@Valid @PathVariable("taskId") long taskId) {
		return new ResponseEntity<>(taskService.updateTask(task, taskId), HttpStatus.OK);
	}
	
    @GetMapping(value = "/{taskId}/estimates")
    public ResponseEntity<List<TaskEstimate>> getTaskEstimates(@PathVariable("taskId") long taskId) {
        return new ResponseEntity<>(taskService.getTaskEstimates(taskId), HttpStatus.OK);
    }

    @PutMapping(value = "/{taskId}/estimates")
    public ResponseEntity<List<TaskEstimate>> updateTaskEstimates(@PathVariable("taskId") long taskId,
                                                                  @Valid @RequestBody List<TaskEstimate> taskEstimates) {
        return new ResponseEntity<>(taskService.updateTaskEstimates(taskId, taskEstimates), HttpStatus.OK);
    }

}
