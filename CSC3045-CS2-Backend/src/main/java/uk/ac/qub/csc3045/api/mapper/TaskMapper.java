package uk.ac.qub.csc3045.api.mapper;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import uk.ac.qub.csc3045.api.model.Task;
import uk.ac.qub.csc3045.api.model.TaskEstimate;

@Mapper
@Repository
public interface TaskMapper {
	
    //insert
	void createTask(@Param("userStoryId") long userStoryId, @Param("task") Task task);
	void createTaskEstimate(@Param("taskEstimate") TaskEstimate taskEstimate);
	
	//select
	Task getTaskById(@Param("id") long id);
	List<Task> getUserStoryTasks(@Param("userStoryId") long userStoryId);
    List<TaskEstimate> getTaskEstimates(@Param("taskId") long taskId);
	
	//update
	void updateTask(@Param("task") Task task);
	void updateTaskEstimate(@Param("taskEstimate") TaskEstimate taskEstimate);
	    
}
