package uk.ac.qub.csc3045.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import uk.ac.qub.csc3045.api.exception.ResponseErrorException;
import uk.ac.qub.csc3045.api.mapper.TaskMapper;
import uk.ac.qub.csc3045.api.mapper.UserStoryMapper;
import uk.ac.qub.csc3045.api.mapper.ProjectMapper;
import uk.ac.qub.csc3045.api.model.Task;
import uk.ac.qub.csc3045.api.utility.ValidationUtility;


@Service
public class TaskService {
	
	private TaskMapper taskMapper;
	private ProjectMapper projectMapper;
	private UserStoryMapper userStoryMapper;
	
	@Autowired
	public TaskService(TaskMapper taskMapper, ProjectMapper projectMapper, UserStoryMapper userStoryMapper) {
		this.taskMapper = taskMapper;
		this.projectMapper = projectMapper;
		this.userStoryMapper = userStoryMapper;
	}
	
	public Task create(long projectId, long userStoryId, Task task) {
		if (ValidationUtility.validateProjectExists(projectId, projectMapper)) {
			if (ValidationUtility.validateUserStoryExists(userStoryId, userStoryMapper)) {
				taskMapper.createTask(userStoryId, task);
				return taskMapper.getTaskById(task.getId());
			}
			else {
				throw new ResponseErrorException("User Story does not exist in the database", HttpStatus.NOT_FOUND);
			}
		}
		else {
			throw new ResponseErrorException("Project does not exist in the database", HttpStatus.NOT_FOUND);
		}
	}
	
	public List<Task> getUserStoryTasks(long projectId, long userStoryId){
		if (ValidationUtility.validateProjectExists(projectId, projectMapper)) {
			if (ValidationUtility.validateUserStoryExists(userStoryId, userStoryMapper)) {
				return taskMapper.getUserStoryTasks(userStoryId);
			}
			else {
				throw new ResponseErrorException("User Story does not exist in the database", HttpStatus.NOT_FOUND);
			}
		}
		else {
			throw new ResponseErrorException("Project does not exist in the database", HttpStatus.NOT_FOUND);
		}
	}
	
	public Task getTask(long projectId, long userStoryId, long taskId) {
		if (ValidationUtility.validateProjectExists(projectId, projectMapper)) {
			if (ValidationUtility.validateUserStoryExists(userStoryId, userStoryMapper)) {
				if (ValidationUtility.validateTaskExists(taskId, taskMapper)) {
					return taskMapper.getTaskById(taskId);
				}
				else {
					throw new ResponseErrorException("Task does not exist in the database", HttpStatus.NOT_FOUND);
				}
			}
			else {
				throw new ResponseErrorException("User Story does not exist in the database", HttpStatus.NOT_FOUND);
			}
		}
		else {
			throw new ResponseErrorException("Project does not exist in the database", HttpStatus.NOT_FOUND);
		}
	}
	
	public Task updateTask(Task task, long taskId) {
		if (ValidationUtility.validateTaskExists(taskId, taskMapper)) {
			taskMapper.updateTask(task);
			return taskMapper.getTaskById(task.getId());
		}
		else {
			throw new ResponseErrorException("Task does not exist in the database", HttpStatus.NOT_FOUND);
		}
	}
}
