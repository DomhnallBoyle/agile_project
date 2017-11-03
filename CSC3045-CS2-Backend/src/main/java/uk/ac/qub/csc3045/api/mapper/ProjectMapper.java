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
	void addToProjectTeam(@Param("projectId") long projectId, @Param("userId") long userId);

	List<User> getUsersOnProject(@Param("projectId") long projectId);

	void createProject(Project project);
	void updateProject(Project project);
	Project getProjectById(@Param("id")long id);
	List<Project> getProjectsForUser(@Param("userId") long userId);
}