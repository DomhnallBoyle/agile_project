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

    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping()
    public ResponseEntity<Task> create(@PathVariable("projectId") long projectId,
                                       @PathVariable("userStoryId") long userStoryId,
                                       @Valid @RequestBody Task task) {
        return new ResponseEntity<>(taskService.create(projectId, userStoryId, task), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<Task>> getTasks(@PathVariable("projectId") long projectId,
                                               @PathVariable("userStoryId") long userStoryId) {
        return new ResponseEntity<>(taskService.getUserStoryTasks(projectId, userStoryId), HttpStatus.OK);
    }

    @GetMapping(value = "/{taskId}")
    public ResponseEntity<Task> getTask(@PathVariable("projectId") long projectId,
                                        @PathVariable("userStoryId") long userStoryId,
                                        @PathVariable("taskId") long taskId) {
        return new ResponseEntity<>(taskService.getTask(projectId, userStoryId, taskId), HttpStatus.OK);
    }

    @PutMapping(value = "/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable("taskId") long taskId,
                                           @Valid @RequestBody Task task) {
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
