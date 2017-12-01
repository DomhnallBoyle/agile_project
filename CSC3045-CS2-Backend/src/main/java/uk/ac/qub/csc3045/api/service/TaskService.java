package uk.ac.qub.csc3045.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uk.ac.qub.csc3045.api.exception.ResponseErrorException;
import uk.ac.qub.csc3045.api.mapper.ProjectMapper;
import uk.ac.qub.csc3045.api.mapper.SprintMapper;
import uk.ac.qub.csc3045.api.mapper.TaskMapper;
import uk.ac.qub.csc3045.api.mapper.UserStoryMapper;
import uk.ac.qub.csc3045.api.model.Sprint;
import uk.ac.qub.csc3045.api.model.Task;
import uk.ac.qub.csc3045.api.model.TaskEstimate;
import uk.ac.qub.csc3045.api.model.UserStory;
import uk.ac.qub.csc3045.api.utility.ValidationUtility;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class TaskService {

    private TaskMapper taskMapper;
    private ProjectMapper projectMapper;
    private UserStoryMapper userStoryMapper;
    private SprintMapper sprintMapper;

    @Autowired
    public TaskService(TaskMapper taskMapper, ProjectMapper projectMapper, UserStoryMapper userStoryMapper, SprintMapper sprintMapper) {
        this.taskMapper = taskMapper;
        this.projectMapper = projectMapper;
        this.userStoryMapper = userStoryMapper;
        this.sprintMapper = sprintMapper;
    }

    public Task create(long projectId, long userStoryId, Task task) {
        if (ValidationUtility.validateProjectExists(projectId, projectMapper)) {
            if (ValidationUtility.validateUserStoryExists(userStoryId, userStoryMapper)) {
                taskMapper.createTask(userStoryId, task);
                createTaskEstimates(task.getUserStory().getId(), task);
                return taskMapper.getTaskById(task.getId());
            } else {
                throw new ResponseErrorException("User Story does not exist in the database", HttpStatus.NOT_FOUND);
            }
        } else {
            throw new ResponseErrorException("Project does not exist in the database", HttpStatus.NOT_FOUND);
        }
    }

    public List<Task> getUserStoryTasks(long projectId, long userStoryId) {
        if (ValidationUtility.validateProjectExists(projectId, projectMapper)) {
            if (ValidationUtility.validateUserStoryExists(userStoryId, userStoryMapper)) {
                return taskMapper.getUserStoryTasks(userStoryId);
            } else {
                throw new ResponseErrorException("User Story does not exist in the database", HttpStatus.NOT_FOUND);
            }
        } else {
            throw new ResponseErrorException("Project does not exist in the database", HttpStatus.NOT_FOUND);
        }
    }

    public Task getTask(long projectId, long userStoryId, long taskId) {
        if (ValidationUtility.validateProjectExists(projectId, projectMapper)) {
            if (ValidationUtility.validateUserStoryExists(userStoryId, userStoryMapper)) {
                if (ValidationUtility.validateTaskExists(taskId, taskMapper)) {
                    return taskMapper.getTaskById(taskId);
                } else {
                    throw new ResponseErrorException("Task does not exist in the database", HttpStatus.NOT_FOUND);
                }
            } else {
                throw new ResponseErrorException("User Story does not exist in the database", HttpStatus.NOT_FOUND);
            }
        } else {
            throw new ResponseErrorException("Project does not exist in the database", HttpStatus.NOT_FOUND);
        }
    }

    public Task updateTask(Task task, long taskId) {
        if (ValidationUtility.validateTaskExists(taskId, taskMapper)) {
            taskMapper.updateTask(task);
            return taskMapper.getTaskById(task.getId());
        } else {
            throw new ResponseErrorException("Task does not exist in the database", HttpStatus.NOT_FOUND);
        }
    }

    public List<TaskEstimate> getTaskEstimates(long taskId) {
        if (ValidationUtility.validateTaskExists(taskId, taskMapper)) {
            return taskMapper.getTaskEstimates(taskId);
        } else {
            throw new ResponseErrorException("Task does not exist in the database", HttpStatus.NOT_FOUND);
        }
    }

    public List<TaskEstimate> updateTaskEstimates(long taskId, List<TaskEstimate> taskEstimates) {
        if (ValidationUtility.validateTaskExists(taskId, taskMapper)) {
            for (TaskEstimate taskEstimate : taskEstimates) {
                taskMapper.updateTaskEstimate(taskEstimate);
            }

            return taskMapper.getTaskEstimates(taskId);
        } else {
            throw new ResponseErrorException("Task does not exist in the database", HttpStatus.NOT_FOUND);
        }
    }

    private void createTaskEstimates(long userStoryId, Task task) {
        if (ValidationUtility.validateUserStoryExists(userStoryId, userStoryMapper)) {
            UserStory userStory = userStoryMapper.getUserStoryById(userStoryId);
            if (ValidationUtility.validateSprintExists(userStory.getSprint().getId(), sprintMapper)) {
                Sprint sprint = sprintMapper.getSprintById(userStory.getSprint().getId());
                generateTaskEstimatesInDatabase(sprint, task);
            } else {
                throw new ResponseErrorException("Sprint does not exist in the database", HttpStatus.NOT_FOUND);
            }
        } else {
            throw new ResponseErrorException("User Story does not exist in the database", HttpStatus.NOT_FOUND);
        }
    }

    private void generateTaskEstimatesInDatabase(Sprint sprint, Task task) {
        LocalDateTime dateIterator = sprint.getStartDate();

        while (dateIterator.isBefore(sprint.getEndDate()) || dateIterator.equals(sprint.getEndDate())) {
            TaskEstimate estimate = new TaskEstimate(task.getId(), dateIterator, task.getInitialEstimate());
            taskMapper.createTaskEstimate(estimate);

	        dateIterator = dateIterator.plusDays(1L);
        }
    }
}
