package uk.ac.qub.csc3045.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import uk.ac.qub.csc3045.api.model.Project;
import uk.ac.qub.csc3045.api.model.Skill;
import uk.ac.qub.csc3045.api.model.User;

@Mapper
@Repository
public interface UserMapper {

    // SELECT Queries

    User findUserByEmail(@Param("email") String email);
    
    List<Project> getProjectsForUser(@Param("userId") long userId);
    
    List<Skill> getUserSkills(@Param("userId") long userId);
    
    // UPDATE Queries
    
    void addUserSkill(@Param("userId") long userId, @Param("skill") Skill skill);
    
    // DELETE Queries
    
    void removeUserSkills(@Param("userId") long userId);
}
