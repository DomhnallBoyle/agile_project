package uk.ac.qub.csc3045.api.mapper;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import uk.ac.qub.csc3045.api.model.Project;
import uk.ac.qub.csc3045.api.model.Sprint;
import uk.ac.qub.csc3045.api.model.User;
import uk.ac.qub.csc3045.api.model.UserStory;

@Mapper
@Repository
public interface SprintMapper {

    // SELECT Queries
	
	Sprint getSprintById(@Param("id") long id);

    List<Sprint> getProjectSprints(@Param("projectId") long projectId);
    
    List<User> getSprintTeam(@Param("sprintId") long sprintId);
    
    List<UserStory> getSprintBacklog(@Param("sprintId") long sprintId);

    List<Sprint> getClashingSprintsForUser(@Param("userId") long userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // INSERT Queries

    void createSprint(Sprint sprint);
    
    void addToSprintTeam(@Param("sprintId") long sprintId, @Param("userId") long userId);

    void addToSprintBacklog(@Param("sprintId") long sprintId, @Param("userStoryId") long userStoryId);
    
    // DELETE Queries

    void resetSprintTeam(@Param("sprintId") long sprintId);
    
    void resetSprintBacklog(@Param("sprintId") long sprintId);
}

