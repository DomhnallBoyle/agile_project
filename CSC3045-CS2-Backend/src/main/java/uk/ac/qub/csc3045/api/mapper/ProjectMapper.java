package uk.ac.qub.csc3045.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import uk.ac.qub.csc3045.api.model.Project;
import uk.ac.qub.csc3045.api.model.User;

@Mapper
@Repository
public interface ProjectMapper {
    Project findProjectByProjectName(@Param("projectName") String projectName); 
    User findUserByEmail(@Param("email") String email);
    
    void addToProjectTeam(@Param("user") User user);
    List<User> getProjectTeam(@Param("project") Project project);
    
	void createProject(Project project);
	Project getProjectById(@Param("id")long id);
	
}