package uk.ac.qub.csc3045.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import uk.ac.qub.csc3045.api.exception.ResponseErrorException;
import uk.ac.qub.csc3045.api.mapper.TaskMapper;
import uk.ac.qub.csc3045.api.model.Task;


@Service
public class TaskService {
	
	private TaskMapper taskMapper;
	
	@Autowired
	public TaskService(TaskMapper taskMapper) {
		this.taskMapper = taskMapper;
	}
	
	public Task create(long userStoryId, Task task) {
		try {
			 taskMapper.createTask(userStoryId,task);
	         return taskMapper.getTaskById(task.getId());
		}catch (DataIntegrityViolationException e) {
            throw new ResponseErrorException("User Story does not exist in the database", HttpStatus.NOT_FOUND);
        }
	}
	
	public List<Task>getTasks(long userStoryId){
		try {
			return taskMapper.getTasks(userStoryId);
		}catch (DataIntegrityViolationException e) {
           throw new ResponseErrorException("User Story does not exist in the database", HttpStatus.NOT_FOUND);
       }
	}
	
	public Task getTask(long taskId) {
		try {
			return taskMapper.getTaskById(taskId);
		}catch (DataIntegrityViolationException e) {
           throw new ResponseErrorException("User Story does not exist in the database", HttpStatus.NOT_FOUND);
       }
	}
	
	public Task updateTask(Task task) {
		try {
			taskMapper.updateTask(task);
			return taskMapper.getTaskById(task.getId());
		}catch (DataIntegrityViolationException e) {
           throw new ResponseErrorException("User Story does not exist in the database", HttpStatus.NOT_FOUND);
       }
	}
}
