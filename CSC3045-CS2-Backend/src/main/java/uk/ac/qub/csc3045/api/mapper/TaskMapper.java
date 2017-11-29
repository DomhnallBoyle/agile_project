package uk.ac.qub.csc3045.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import uk.ac.qub.csc3045.api.model.Task;

@Mapper
@Repository
public interface TaskMapper {
	
    //insert
	void createTask(@Param("userStoryId") long userStoryId,@Param("task") Task task);
	
	//select
	Task getTaskById(@Param("id") long id);
	
	List<Task> getTasks(@Param("userStoryId") long userStoryId);
	
	//update
	void updateTask(@Param("task")Task task);
	    
}
