package uk.ac.qub.csc3045.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import uk.ac.qub.csc3045.api.model.Project;
import uk.ac.qub.csc3045.api.model.Sprint;
import uk.ac.qub.csc3045.api.model.User;

@Mapper
@Repository
public interface SprintMapper {

    // SELECT Queries
	
	Sprint getSprintById(@Param("id") long id);

    List<Sprint> getSprintsInProject(@Param("projectId") long projectId);
    
    List<User> getUsersOnSprint(@Param("sprintId") long sprintId);

    // INSERT Queries

    void createSprint(Sprint sprint);
    
    void addToSprintTeam(@Param("sprintId") long sprintId, @Param("userId") long userId);
    


  
}
